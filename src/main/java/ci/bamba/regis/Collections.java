package ci.bamba.regis;

import ci.bamba.regis.exceptions.RequestException;
import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.CollectionsRequestToPay;
import ci.bamba.regis.models.CollectionsRequestToPayBodyRequest;
import ci.bamba.regis.models.Token;
import io.reactivex.Observable;

public class Collections extends Product {

    private static final String TYPE = "collection";

    Collections(String baseUrl, Environment environment, String subscriptionKey, String apiUser, String apiKey) {
        super(baseUrl, environment, subscriptionKey, apiUser, apiKey);
    }

    public Observable<Token> createToken() {
        return super.createToken(TYPE);
    }

    public Observable<String> requestToPay(float amount, String currency, String externalId, String payerPartyId,
            String payerMessage, String payeeNote) {
        return createToken().flatMap(token -> requestToPay(token.getAccessToken(), amount, currency, externalId,
                payerPartyId, payerMessage, payeeNote));
    }

    public Observable<String> requestToPay(String token, float amount, String currency, String externalId,
            String payerPartyId, String payerMessage, String payeeNote) {
        CollectionsRequestToPayBodyRequest body = new CollectionsRequestToPayBodyRequest(String.format("%s", amount),
                currency, externalId, payerPartyId, payerMessage, payeeNote);
        String referenceId = getUUID();
        return RestClient.getService(getBaseUrl()).collectionsCreateRequestToPay(getAuthHeader(token),
                getSubscriptionKey(), referenceId, getEnvironment().getEnv(), body).map(response -> {
                    if (response.code() == 202) {
                        return referenceId;
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<CollectionsRequestToPay> getRequestToPay(String referenceId) {
        return createToken().flatMap(token -> getRequestToPay(token.getAccessToken(), referenceId));
    }

    public Observable<CollectionsRequestToPay> getRequestToPay(String token, String referenceId) {
        return RestClient.getService(getBaseUrl()).collectionsGetRequestToPay(getAuthHeader(token),
                getSubscriptionKey(), getEnvironment().getEnv(), referenceId).map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<AccountBalance> getAccountBalance() {
        return createToken().flatMap(token -> getAccountBalance(token.getAccessToken()));
    }

    public Observable<AccountBalance> getAccountBalance(String token) {
        return super.getAccountBalance(TYPE, token);
    }

    public Observable<AccountStatus> getAccountStatus(String msisdn) {
        return createToken().flatMap(token -> getAccountStatus(token.getAccessToken(), msisdn));
    }

    public Observable<AccountStatus> getAccountStatus(String token, String msisdn) {
        return super.getAccountStatus(TYPE, token, msisdn);
    }
}
