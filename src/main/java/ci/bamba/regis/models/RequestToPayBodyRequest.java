package ci.bamba.regis.models;

public class RequestToPayBodyRequest {

    private String amount;
    private String currency;
    private String externalId;
    private Payer payer;
    private String payerMessage;
    private String payeeNote;

    public RequestToPayBodyRequest() {

    }

    public RequestToPayBodyRequest(String amount, String currency, String externalId, String payerPartyId, String payerMessage, String payeeNote) {
        this.amount = amount;
        this.currency = currency;
        this.externalId = externalId;
        this.payer = new Payer(payerPartyId);
        this.payerMessage = payerMessage;
        this.payeeNote = payeeNote;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public static class Payer {
        private String partyIdType;
        private String partyId;

        public Payer() {

        }

        Payer(String partyId) {
            this.partyId = partyId;
            this.partyIdType = "MSISDN";
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
