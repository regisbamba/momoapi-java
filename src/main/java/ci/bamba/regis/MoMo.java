package ci.bamba.regis;

public class MoMo {

    private static final String BASE_URL_SANDBOX = "https://ericssonbasicapi2.azure-api.net/";
    private static final String BASE_URL_PRODUCTION = "https://ericssonbasicapi1.azure-api.net/";

    private Environment environment;

    public MoMo(Environment environment) {
        this.environment = environment;
    }

    public SandboxProvisioning createSandboxProvisioning(String subscriptionKey) {
        return new SandboxProvisioning(subscriptionKey, getBaseUrl());
    }

    public Collections createCollections(String subscriptionKey, String apiUser, String apiKey) {
        return new Collections(getBaseUrl(), getEnvironment(), subscriptionKey, apiUser, apiKey);
    }

    public Disbursements createDisbursements(String subscriptionKey, String apiUser, String apiKey) {
        return new Disbursements(getBaseUrl(), getEnvironment(), subscriptionKey, apiUser, apiKey);
    }

    public Remittances createRemittances(String subscriptionKey, String apiUser, String apiKey) {
        return new Remittances(getBaseUrl(), getEnvironment(), subscriptionKey, apiUser, apiKey);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public String getBaseUrl() {
        return this.environment == Environment.SANDBOX ? BASE_URL_SANDBOX : BASE_URL_PRODUCTION;
    }


}
