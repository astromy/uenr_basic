package com.UBSDB.UnerBasicSchoolFinancialSystem.Paystack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Central place for every Paystack setting. Nothing here is hard-coded — all values
 * are read from application.properties, which in turn is expected to read them from
 * environment variables in every real deployment (see application.properties for the
 * exact keys). The secret key must NEVER be committed to source control or exposed
 * to the browser; only publicKey is safe to hand to client-side code.
 *
 * @author UBS
 */
@Configuration
public class PaystackConfig {

    @Value("${paystack.secret-key:}")
    private String secretKey;

    @Value("${paystack.public-key:}")
    private String publicKey;

    @Value("${paystack.base-url:https://api.paystack.co}")
    private String baseUrl;

    @Value("${paystack.callback-url:}")
    private String callbackUrl;

    @Value("${paystack.currency:GHS}")
    private String currency;

    /** When true, Paystack's fee is added on top so the payer bears it and the school receives the full amount. */
    @Value("${paystack.pass-fees-to-customer:true}")
    private boolean passFeesToCustomer;

    /** Paystack's percentage fee (Ghana local cards / MoMo / bank transfer = 1.95%). */
    @Value("${paystack.fee-percent:1.95}")
    private double feePercent;

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isPassFeesToCustomer() {
        return passFeesToCustomer;
    }

    public double getFeePercent() {
        return feePercent;
    }

    public boolean isConfigured() {
        return secretKey != null && !secretKey.isBlank()
                && publicKey != null && !publicKey.isBlank();
    }
    @jakarta.annotation.PostConstruct
    void logStatus() {
        java.io.File env = new java.io.File(".env");
        System.out.println("Working dir: " + System.getProperty("user.dir"));
        System.out.println(".env found: " + env.exists() + " -> " + env.getAbsolutePath());
        System.out.println("Paystack configured: " + isConfigured());
    }
}