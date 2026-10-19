package com.UBSDB.UnerBasicSchoolFinancialSystem.Paystack;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Thin wrapper around Paystack's REST API using the okhttp3 client that is already
 * a project dependency, so no new libraries are required.
 *
 * Docs: https://paystack.com/docs/api/transaction/
 *
 * @author UBS
 */
@Service
public class PaystackService {

    private static final Logger LOG = Logger.getLogger(PaystackService.class.getName());
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private PaystackConfig config;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .build();

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Generates a reference that is unique per attempt and traceable back to the
     * student it belongs to, in case you ever need to eyeball transactions on the
     * Paystack dashboard.
     */
    public String generateReference(String studentId) {
        return "UBS-" + studentId + "-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Returns the total to charge (in pesewas): the amount entered plus the fee
     * percentage of that amount, rounded to the nearest pesewa.
     *   e.g. 2% on GHS 1.00  -> fee 0.02,  total 1.02
     *        2% on GHS 500.00 -> fee 10.00, total 510.00
     * If passing fees is turned off, the payer is charged exactly the amount entered.
     */
    public long calculateChargePesewas(long netPesewas) {
        if (!config.isPassFeesToCustomer() || config.getFeePercent() <= 0) {
            return netPesewas;
        }
        long feePesewas = Math.round(netPesewas * config.getFeePercent() / 100.0);
        return netPesewas + feePesewas;
    }

    /**
     * The amount to credit the student with. When fees are passed on, the payer was
     * charged more than the fee amount they entered; that intended amount is stored in
     * metadata.netAmountPesewas at initialization and is what we credit. Falls back to
     * the charged amount for older transactions without that metadata.
     */
    public double resolveCreditAmount(Object netAmountPesewasMeta, long chargedPesewas) {
        if (netAmountPesewasMeta != null) {
            try {
                long net = Long.parseLong(String.valueOf(netAmountPesewasMeta).trim());
                // Never credit more than was actually paid.
                if (net > 0 && net <= chargedPesewas) {
                    return net / 100.0;
                }
            } catch (NumberFormatException ignored) {
                // fall through
            }
        }
        return chargedPesewas / 100.0;
    }

    /**
     * Calls POST /transaction/initialize and returns the parsed response, whose
     * data.authorization_url is where the payer's browser should be redirected to
     * complete payment on Paystack's hosted checkout page.
     *
     * @param email       payer's email (Paystack requires this even for card/mobile money)
     * @param amountPesewas the total to charge, in pesewas (already including any fee passed to the payer)
     * @param reference   a unique reference for this attempt, from generateReference()
     * @param metadata    arbitrary data echoed back on verify/webhook (studentId, paymentType, etc.)
     */
    public PaystackInitResponse initializeTransaction(String email, long amountPesewas, String reference, Map<String, Object> metadata) throws IOException {
        if (!config.isConfigured()) {
            throw new IllegalStateException("Paystack is not configured: set PAYSTACK_SECRET_KEY and PAYSTACK_PUBLIC_KEY");
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("email", email);
        // Paystack expects the smallest currency unit (pesewas for GHS, kobo for NGN).
        body.put("amount", amountPesewas);
        body.put("currency", config.getCurrency());
        body.put("reference", reference);
        if (config.getCallbackUrl() != null && !config.getCallbackUrl().isBlank()) {
            body.put("callback_url", config.getCallbackUrl());
        }
        if (metadata != null && !metadata.isEmpty()) {
            body.put("metadata", metadata);
        }

        String json = mapper.writeValueAsString(body);
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(config.getBaseUrl() + "/transaction/initialize")
                .addHeader("Authorization", "Bearer " + config.getSecretKey())
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                LOG.severe("Paystack initialize failed [" + response.code() + "]: " + responseBody);
                throw new IOException("Paystack initialize failed with status " + response.code());
            }
            return mapper.readValue(responseBody, PaystackInitResponse.class);
        }
    }

    /**
     * Calls GET /transaction/verify/{reference}. This is the source of truth for
     * whether money actually changed hands — always verify server-side before
     * crediting an account, never trust the redirect query params alone.
     */
    public PaystackVerifyResponse verifyTransaction(String reference) throws IOException {
        if (!config.isConfigured()) {
            throw new IllegalStateException("Paystack is not configured: set PAYSTACK_SECRET_KEY and PAYSTACK_PUBLIC_KEY");
        }

        Request request = new Request.Builder()
                .url(config.getBaseUrl() + "/transaction/verify/" + reference)
                .addHeader("Authorization", "Bearer " + config.getSecretKey())
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                LOG.severe("Paystack verify failed [" + response.code() + "]: " + responseBody);
                throw new IOException("Paystack verify failed with status " + response.code());
            }
            return mapper.readValue(responseBody, PaystackVerifyResponse.class);
        }
    }

    /**
     * Validates the X-Paystack-Signature header on an incoming webhook request.
     * The signature is HMAC-SHA512 of the *raw, exact* request body, keyed with the
     * secret key. Reject the webhook if this doesn't match — otherwise anyone who
     * finds the webhook URL could POST fake "charge.success" events and get free
     * fee credit. Comparison is constant-time to avoid timing attacks.
     *
     * @param rawRequestBody the exact bytes of the request body, unparsed
     * @param signatureHeader the value of the X-Paystack-Signature header
     */
    public boolean isValidWebhookSignature(String rawRequestBody, String signatureHeader) {
        if (signatureHeader == null || signatureHeader.isBlank() || rawRequestBody == null) {
            return false;
        }
        if (!config.isConfigured()) {
            return false;
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(config.getSecretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] hash = mac.doFinal(rawRequestBody.getBytes(StandardCharsets.UTF_8));
            String computedHex = toHex(hash);
            return MessageDigest.isEqual(
                    computedHex.getBytes(StandardCharsets.UTF_8),
                    signatureHeader.trim().getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Failed to validate Paystack webhook signature", ex);
            return false;
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}