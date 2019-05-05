package ci.bamba.regis;

import java.util.Base64;
import java.util.UUID;

import ci.bamba.regis.exceptions.RequestException;
import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.ApiCredentials;
import ci.bamba.regis.models.RequestToPay;
import ci.bamba.regis.models.RequestToPayBodyRequest;
import ci.bamba.regis.models.Token;
import ci.bamba.regis.models.Transfer;
import ci.bamba.regis.models.TransferBodyRequest;
import io.reactivex.Observable;

public class Product {

    private ApiCredentials apiCredentials;
    private String baseUrl;
    private String subscriptionKey;
    private Environment environment;

    Product(String baseUrl, Environment environment, String subscriptionKey, String apiUser, String apiKey) {
        this.baseUrl = baseUrl;
        this.environment = environment;
        this.subscriptionKey = subscriptionKey;
        this.apiCredentials = new ApiCredentials(apiUser, apiKey);
    }

    public ApiCredentials getApiCredentials() {
        return apiCredentials;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    Observable<Token> createToken(String type) {
        return createToken(type, this.getApiCredentials().getUser(), this.getApiCredentials().getKey());
    }

    private Observable<Token> createToken(String type, String apiUser, String apiKey) {
        byte[] encodedBytes = Base64.getEncoder().encode((apiUser + ":" + apiKey).getBytes());
        String authorization = "Basic " + new String(encodedBytes);
        return RestClient.getService(getBaseUrl()).createToken(authorization, getSubscriptionKey(), type)
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    protected String getUUID() {
        return UUID.randomUUID().toString();
    }

    protected String getAuthHeader(String token) {
        return String.format("Bearer %s", token);
    }

    public Observable<String> transfer(String type, String token, float amount, String currency,
            String externalId, String payeePartyId, String payerMessage, String payeeNote) {
        TransferBodyRequest body = new TransferBodyRequest(String.format("%s", amount), currency, externalId,
                payeePartyId, payerMessage, payeeNote);
        String referenceId = getUUID();
        return RestClient.getService(getBaseUrl()).createTransfer(type, getAuthHeader(token), getSubscriptionKey(),
                referenceId, getEnvironment().getEnv(), body).map(response -> {
                    if (response.code() == 202) {
                        return referenceId;
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<Transfer> getTransfer(String type, String token, String referenceId) {
        return RestClient.getService(getBaseUrl())
                .getTransfer(type, getAuthHeader(token), getSubscriptionKey(), getEnvironment().getEnv(), referenceId)
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<String> requestToPay(String type, String token, float amount, String currency, String externalId,
            String payerPartyId, String payerMessage, String payeeNote) {
        RequestToPayBodyRequest body = new RequestToPayBodyRequest(String.format("%s", amount), currency, externalId,
                payerPartyId, payerMessage, payeeNote);
        String referenceId = getUUID();
        return RestClient.getService(getBaseUrl()).createRequestToPay(type, getAuthHeader(token), getSubscriptionKey(),
                referenceId, getEnvironment().getEnv(), body).map(response -> {
                    if (response.code() == 202) {
                        return referenceId;
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<RequestToPay> getRequestToPay(String type, String token, String referenceId) {
        return RestClient.getService(getBaseUrl()).getRequestToPay(type, getAuthHeader(token), getSubscriptionKey(),
                getEnvironment().getEnv(), referenceId).map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    protected Observable<AccountBalance> getAccountBalance(String type, String token) {
        return RestClient.getService(getBaseUrl())
                .getAccountBalance(type, getAuthHeader(token), getSubscriptionKey(), getEnvironment().getEnv())
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<AccountStatus> getAccountStatus(String type, String token, String msisdn) {
        return RestClient.getService(getBaseUrl()).getAccountStatus(type, getAuthHeader(token), getSubscriptionKey(),
                getEnvironment().getEnv(), "msisdn", msisdn).map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

}