package ci.bamba.regis.models;

public class ApiCredentials {

    private String apiUser;
    private String apiKey;

    public ApiCredentials(String apiUser, String apiKey) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
    }

    public String getApiUser() {
        return apiUser;
    }
    public String getApiKey() {
        return apiKey;
    }

}
