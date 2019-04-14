package ci.bamba.regis;

import java.util.UUID;

import ci.bamba.regis.exceptions.RequestException;
import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.DisbursementsTransfer;
import ci.bamba.regis.models.DisbursementsTransferBodyRequest;
import ci.bamba.regis.models.Token;
import io.reactivex.Observable;

public class Disbursements extends Product {

    Disbursements(String baseUrl, Environment environment, String subscriptionKey, String apiUser, String apiKey) {
        super(baseUrl, environment, subscriptionKey, apiUser, apiKey);
    }

    public Observable<Token> createToken() {
        return super.createToken("disbursement");
    }

    public Observable<String> transfer(float amount, String currency, String externalId, String payeePartyId, String payerMessage, String payeeNote) {
        return createToken().flatMap(token -> transfer(token.getAccessToken(), amount, currency, externalId, payeePartyId, payerMessage, payeeNote));
    }

    public Observable<String> transfer(String token, float amount, String currency, String externalId, String payeePartyId, String payerMessage, String payeeNote) {
        DisbursementsTransferBodyRequest body = new DisbursementsTransferBodyRequest(String.format("%s", amount), currency, externalId, payeePartyId, payerMessage, payeeNote);
        String authorization = String.format("Bearer %s", token);
        String referenceId = UUID.randomUUID().toString();
        return RestClient
                .getService(getBaseUrl())
                .disbursementsCreateTransfer(authorization, getSubscriptionKey(), referenceId, getEnvironment().getEnv(), body)
                .map(response -> {
                    if (response.code() == 202) {
                        return referenceId;
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<DisbursementsTransfer> getTransfer(String referenceId) {
        return createToken().flatMap(token -> getTransfer(token.getAccessToken(), referenceId));
    }

    public Observable<DisbursementsTransfer> getTransfer(String token, String referenceId) {
        String authorization = String.format("Bearer %s", token);
        return RestClient
                .getService(getBaseUrl())
                .disbursementsGetTransfer(authorization, getSubscriptionKey(), getEnvironment().getEnv(), referenceId)
                .map(response -> {
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
        String authorization = String.format("Bearer %s", token);
        return RestClient.getService(getBaseUrl())
                .disbursementsGetAccountBalance(authorization, getSubscriptionKey(), getEnvironment().getEnv())
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }

    public Observable<AccountStatus> getAccountStatus(String msisdn) {
        return createToken().flatMap(token -> getAccountStatus(token.getAccessToken(), msisdn));
    }

    public Observable<AccountStatus> getAccountStatus(String token, String msisdn) {
        String authorization = String.format("Bearer %s", token);
        return RestClient.getService(getBaseUrl())
                .disbursementsGetAccountStatus(authorization, getSubscriptionKey(), getEnvironment().getEnv(), "msisdn", msisdn)
                .map(response -> {
                    if (response.code() == 200) {
                        return response.body();
                    } else {
                        throw new RequestException(response.code(), response.message());
                    }
                });
    }
}
