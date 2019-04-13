package ci.bamba.regis;

public class MoMo {

    private static final String BASE_URL_SANDBOX = "https://ericssonbasicapi2.azure-api.net/";
    private static final String BASE_URL_PRODUCTION = ""; // TODO : updates url for production

    private Environment environment;

    public MoMo(Environment environment) {
        this.environment = environment;
    }

    public Collections subscribeToCollections(String subscriptionKey) {
        return new Collections(getBaseUrl(), getEnvironment(), subscriptionKey);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    private String getBaseUrl() {
        return this.environment == Environment.SANDBOX ? BASE_URL_SANDBOX : BASE_URL_PRODUCTION;
    }

}
