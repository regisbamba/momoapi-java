package ci.bamba.regis;

import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.RequestToPay;
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
        return super.requestToPay(TYPE, token, amount, currency, externalId, payerPartyId, payerMessage, payeeNote);
    }

    public Observable<RequestToPay> getRequestToPay(String referenceId) {
        return createToken().flatMap(token -> getRequestToPay(token.getAccessToken(), referenceId));
    }

    public Observable<RequestToPay> getRequestToPay(String token, String referenceId) {
        return super.getRequestToPay(TYPE, token, referenceId);
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
