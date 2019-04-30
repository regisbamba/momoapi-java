package ci.bamba.regis;

import java.util.HashMap;
import java.util.UUID;

import ci.bamba.regis.exceptions.RequestException;
import ci.bamba.regis.models.ApiCredentials;
import ci.bamba.regis.models.ApiUser;
import io.reactivex.Observable;

public class SandboxProvisioning {

    private String subscriptionKey;
    private String baseUrl;
    private Environment environment;

    SandboxProvisioning(String subscriptionKey, String baseUrl) {
        this.subscriptionKey = subscriptionKey;
        this.baseUrl = baseUrl;
        this.environment = Environment.SANDBOX;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Observable<String> createApiUser() {
        return createApiUser("string");
    }

    public Observable<String> createApiUser(String providerCallbackHost) {
        String referenceId = UUID.randomUUID().toString();
        HashMap<String, String> body = new HashMap<>();
        body.put("providerCallbackHost", providerCallbackHost);
        return RestClient
                .getService(getBaseUrl())
                .createApiUser(getSubscriptionKey(), referenceId, body)
                .map(response -> {
                    if (response.code() == 201) {
                        return referenceId;
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<ApiUser> getApiUser(String referenceId) {
        return RestClient.getService(getBaseUrl())
                .getApiUser(getSubscriptionKey(), referenceId)
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<ApiCredentials> createApiKey(String referenceId) {
        return RestClient
                .getService(getBaseUrl())
                .createApiKey(getSubscriptionKey(), referenceId)
                .map(response -> {
                    if (response.code() == 201) {
                        return new ApiCredentials(referenceId, response.body().getApiKey());
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

}
