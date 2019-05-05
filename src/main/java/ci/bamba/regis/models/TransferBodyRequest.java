package ci.bamba.regis.models;

public class TransferBodyRequest {

    private String amount;
    private String currency;
    private String externalId;
    private Payee payee;
    private String payerMessage;
    private String payeeNote;

    public TransferBodyRequest() {

    }

    public TransferBodyRequest(String amount, String currency, String externalId, String payerPartyId, String payerMessage, String payeeNote) {
        this.amount = amount;
        this.currency = currency;
        this.externalId = externalId;
        this.payee = new Payee(payerPartyId);
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

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
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

    public static class Payee {
        private String partyIdType;
        private String partyId;

        public Payee() {

        }

        Payee(String partyId) {
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
