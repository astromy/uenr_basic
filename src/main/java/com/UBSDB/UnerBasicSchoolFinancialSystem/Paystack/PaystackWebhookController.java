package com.UBSDB.UnerBasicSchoolFinancialSystem.Paystack;

import com.UBSDB.UnerBasicSchoolFinancialSystem.FeePayment.PaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Server-to-server webhook that Paystack calls directly when a transaction event
 * happens. This is the real source of truth for payments — it does not depend on
 * the parent's browser successfully returning to /Paystack/callback, so it still
 * credits the payment if their connection drops right after paying.
 *
 * Configure this URL in the Paystack dashboard under Settings -> API Keys & Webhooks:
 *   https://<your-domain>/Paystack/webhook
 *
 * Must remain publicly reachable and CSRF-exempt (Spring Security's CSRF filter is
 * already disabled globally in SecurityConfig, and this path is in the permitAll
 * list — see SecurityConfig.java).
 *
 * @author UBS
 */
@RestController
public class PaystackWebhookController {

    private static final Logger LOG = Logger.getLogger(PaystackWebhookController.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired private PaystackService paystackService;
    @Autowired private PaymentService paymentService;

    @PostMapping("/Paystack/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request,
                                                @RequestHeader(value = "x-paystack-signature", required = false) String signature) {
        String rawBody;
        try {
            rawBody = readRawBody(request);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Could not read Paystack webhook body", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unreadable body");
        }

        // Signature check first, before any parsing — an unsigned or badly-signed
        // request is rejected outright so it can never trigger a fee credit.
        if (!paystackService.isValidWebhookSignature(rawBody, signature)) {
            LOG.warning("Rejected Paystack webhook call with invalid signature");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid signature");
        }

        try {
            JsonNode root = mapper.readTree(rawBody);
            String event = root.path("event").asText("");

            // Paystack sends other events too (transfer.success, subscription.*, etc.)
            // — only charge.success represents money that has actually landed.
            if (!"charge.success".equals(event)) {
                return ResponseEntity.ok("ignored: " + event);
            }

            JsonNode data = root.path("data");
            String reference = data.path("reference").asText(null);
            long amountKobo = data.path("amount").asLong(0);
            String payerEmail = data.path("customer").path("email").asText(null);

            JsonNode metadata = data.path("metadata");
            // Credit the intended fee amount, not the total that included Paystack's charge.
            double amount = paystackService.resolveCreditAmount(
                    metadata.hasNonNull("netAmountPesewas") ? metadata.path("netAmountPesewas").asText() : null,
                    amountKobo);
            String studentId = metadata.path("studentId").asText(null);
            String paymentType = metadata.has("paymentType") ? metadata.path("paymentType").asText("SchoolFees") : "SchoolFees";

            if (reference == null || reference.isBlank()) {
                LOG.severe("Paystack webhook charge.success with no reference");
                // Acknowledge with 200 anyway — this payload will never become valid on retry,
                // and returning an error just makes Paystack retry it forever.
                return ResponseEntity.ok("missing reference");
            }
            if (studentId == null || studentId.isBlank()) {
                LOG.severe("Paystack webhook charge.success for reference " + reference + " has no studentId in metadata");
                return ResponseEntity.ok("missing studentId metadata");
            }

            var receipt = paymentService.SavePaystackPayment(studentId, amount, reference, paymentType, payerEmail);
            if (receipt == null) {
                LOG.severe("Failed to save payment for Paystack reference " + reference);
                // Still 200: an internal failure here should be fixed and reconciled manually
                // (the reference is idempotent so re-processing is safe once fixed), rather
                // than have Paystack hammer this endpoint with retries for hours.
                return ResponseEntity.ok("processing error, needs manual reconciliation: " + reference);
            }

            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Unexpected error processing Paystack webhook", ex);
            return ResponseEntity.ok("error logged");
        }
    }

    private String readRawBody(HttpServletRequest request) throws IOException {
        try (var reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[4096];
            int read;
            while ((read = reader.read(buf)) != -1) {
                sb.append(buf, 0, read);
            }
            return sb.toString();
        }
    }
}