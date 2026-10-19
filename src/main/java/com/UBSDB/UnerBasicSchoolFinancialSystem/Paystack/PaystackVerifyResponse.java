package com.UBSDB.UnerBasicSchoolFinancialSystem.Paystack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 * Maps the JSON response from Paystack's GET /transaction/verify/{reference}.
 * Only the fields this project actually needs are declared; everything else is
 * ignored rather than failing deserialization if Paystack adds new fields.
 *
 * @author UBS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackVerifyResponse {

    private boolean status;
    private String message;
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    /** True only when Paystack both accepted the API call AND the charge succeeded. */
    public boolean isSuccessfulCharge() {
        return status && data != null && "success".equalsIgnoreCase(data.getStatus());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private String reference;
        /** "success", "failed", "abandoned", etc. */
        private String status;
        /** amount in the smallest currency unit (pesewas for GHS) */
        private long amount;
        private String currency;
        private String gateway_response;
        private String paid_at;
        private Customer customer;
        private Map<String, Object> metadata;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getGateway_response() {
            return gateway_response;
        }

        public void setGateway_response(String gateway_response) {
            this.gateway_response = gateway_response;
        }

        public String getPaid_at() {
            return paid_at;
        }

        public void setPaid_at(String paid_at) {
            this.paid_at = paid_at;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Map<String, Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        /** Converts the pesewas/kobo amount Paystack reports back to GHS. */
        public double getAmountInBaseCurrency() {
            return amount / 100.0;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Customer {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
