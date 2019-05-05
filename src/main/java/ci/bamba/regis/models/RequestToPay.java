package ci.bamba.regis.models;

public class RequestToPay {

    private String reason;
    private float amount;
    private String status;
    private String financialTransactionId;
    private String currency;
    private String externalId;
    private Payer payer;
    private String payerMessage;
    private String payeeNote;


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the financialTransactionId
     */
    public String getFinancialTransactionId() {
        return financialTransactionId;
    }

    /**
     * @param financialTransactionId the financialTransactionId to set
     */
    public void setFinancialTransactionId(String financialTransactionId) {
        this.financialTransactionId = financialTransactionId;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class Payer {
        private String partyIdType;
        private String partyId;

        public Payer() {
        }

        public String getPartyIdType() {
            return partyIdType;
        }

        public void setPartyIdType(String partyIdType) {
            this.partyIdType = partyIdType;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }
    }
}
