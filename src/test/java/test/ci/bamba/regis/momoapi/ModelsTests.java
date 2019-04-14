package test.ci.bamba.regis.momoapi;

import org.junit.Test;

import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.ApiCredentials;
import ci.bamba.regis.models.ApiKey;
import ci.bamba.regis.models.ApiUser;
import ci.bamba.regis.models.CollectionsRequestToPay;
import ci.bamba.regis.models.CollectionsRequestToPayBodyRequest;
import ci.bamba.regis.models.Token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ModelsTests {

    @Test
    public void testAccountBalance() {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAvailableBalance(5);
        accountBalance.setCurrency("USD");
        assertEquals(5, accountBalance.getAvailableBalance(), 0);
        assertEquals("USD", accountBalance.getCurrency());
    }

    @Test
    public void testAccountStatus() {
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setResult(true);
        assertTrue(accountStatus.getResult());
    }

    @Test
    public void testApiCredentials() {
        ApiCredentials apiCredentials1 = new ApiCredentials();
        apiCredentials1.setUser("user1");
        apiCredentials1.setKey("key1");

        assertEquals("user1", apiCredentials1.getUser());
        assertEquals("key1", apiCredentials1.getKey());

        ApiCredentials apiCredentials2 = new ApiCredentials("user2", "key2");

        assertEquals("user2", apiCredentials2.getUser());
        assertEquals("key2", apiCredentials2.getKey());
    }

    @Test
    public void testApiKey() {
        ApiKey apiKey1 = new ApiKey();
        apiKey1.setApiKey("api1");
        assertEquals("api1", apiKey1.getApiKey());

        ApiKey apiKey2 = new ApiKey("api2");
        assertEquals("api2", apiKey2.getApiKey());
    }

    @Test
    public void testApiUser() {
        ApiUser apiUser1 = new ApiUser();
        apiUser1.setProviderCallbackHost("host1");
        apiUser1.setTargetEnvironment("env1");
        assertEquals("host1", apiUser1.getProviderCallbackHost());
        assertEquals("env1", apiUser1.getTargetEnvironment());

        ApiUser apiUser2 = new ApiUser("host2", "env2");
        assertEquals("host2", apiUser2.getProviderCallbackHost());
        assertEquals("env2", apiUser2.getTargetEnvironment());
    }

    @Test
    public void testCollectionsRequestToPay() {
        float amount = 9;
        String currency = "USD";
        String externalId = "ext";
        String financialTransactionId = "fin";
        String payeeNote = "payee";
        String payerMessage = "mes";
        String reason = "reason";
        String status = "status";

        CollectionsRequestToPay.Payer payer = new CollectionsRequestToPay.Payer();
        payer.setPartyId("id");
        payer.setPartyIdType("type");

        CollectionsRequestToPay collectionsRequestToPay = new CollectionsRequestToPay();
        collectionsRequestToPay.setAmount(amount);
        collectionsRequestToPay.setCurrency(currency);
        collectionsRequestToPay.setExternalId(externalId);
        collectionsRequestToPay.setFinancialTransactionId(financialTransactionId);
        collectionsRequestToPay.setPayeeNote(payeeNote);
        collectionsRequestToPay.setPayerMessage(payerMessage);
        collectionsRequestToPay.setPayer(payer);
        collectionsRequestToPay.setReason(reason);
        collectionsRequestToPay.setStatus(status);

        assertEquals(amount, collectionsRequestToPay.getAmount(), 0);
        assertEquals(currency, collectionsRequestToPay.getCurrency());
        assertEquals(externalId, collectionsRequestToPay.getExternalId());
        assertEquals(financialTransactionId, collectionsRequestToPay.getFinancialTransactionId());
        assertEquals(payeeNote, collectionsRequestToPay.getPayeeNote());
        assertEquals(payerMessage, collectionsRequestToPay.getPayerMessage());
        assertEquals(reason, collectionsRequestToPay.getReason());
        assertEquals(status, collectionsRequestToPay.getStatus());
        assertNotNull(payer);
        assertEquals("id", collectionsRequestToPay.getPayer().getPartyId());
        assertEquals("type", collectionsRequestToPay.getPayer().getPartyIdType());
    }

    @Test
    public void testCollectionsRequestToPayBodyRequest() {
        String amount = "9";
        String currency = "USD";
        String externalId = "ext";
        String payeeNote = "payee";
        String payerMessage = "mes";

        CollectionsRequestToPayBodyRequest.Payer payer = new CollectionsRequestToPayBodyRequest.Payer();
        payer.setPartyId("id");
        payer.setPartyIdType("type");

        CollectionsRequestToPayBodyRequest collectionsRequestToPay = new CollectionsRequestToPayBodyRequest();
        collectionsRequestToPay.setAmount(amount);
        collectionsRequestToPay.setCurrency(currency);
        collectionsRequestToPay.setExternalId(externalId);
        collectionsRequestToPay.setPayeeNote(payeeNote);
        collectionsRequestToPay.setPayerMessage(payerMessage);
        collectionsRequestToPay.setPayer(payer);

        assertEquals(amount, collectionsRequestToPay.getAmount());
        assertEquals(currency, collectionsRequestToPay.getCurrency());
        assertEquals(externalId, collectionsRequestToPay.getExternalId());
        assertEquals(payeeNote, collectionsRequestToPay.getPayeeNote());
        assertEquals(payerMessage, collectionsRequestToPay.getPayerMessage());
        assertNotNull(payer);
        assertEquals("id", collectionsRequestToPay.getPayer().getPartyId());
        assertEquals("type", collectionsRequestToPay.getPayer().getPartyIdType());
    }

    @Test
    public void testToken() {
        Token token = new Token();
        token.setAccessToken("access");
        token.setExpiresIn(1);
        token.setTokenType("type");

        assertEquals(1, token.getExpiresIn());
        assertEquals("access", token.getAccessToken());
        assertEquals("type", token.getTokenType());
    }
}
