package test.ci.bamba.regis.momoapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ci.bamba.regis.RestClient;
import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.ApiCredentials;
import ci.bamba.regis.models.ApiKey;
import ci.bamba.regis.models.ApiUser;
import ci.bamba.regis.models.RequestToPay;
import ci.bamba.regis.models.RequestToPayBodyRequest;
import ci.bamba.regis.models.Token;
import ci.bamba.regis.models.Transfer;
import ci.bamba.regis.models.TransferBodyRequest;

public class UnitTests {

    @Test
    public void testRestClient() {
        RestClient client = new RestClient();
        assertNotNull(client);
    }

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

        RequestToPay.Payer payer = new RequestToPay.Payer();
        payer.setPartyId("id");
        payer.setPartyIdType("type");

        RequestToPay collectionsRequestToPay = new RequestToPay();
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

        RequestToPayBodyRequest.Payer payer = new RequestToPayBodyRequest.Payer();
        payer.setPartyId("id");
        payer.setPartyIdType("type");

        RequestToPayBodyRequest collectionsRequestToPay = new RequestToPayBodyRequest();
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

    @Test
    public void testDisbursementsTransferBodyRequest() {
        String amount = "9";
        String currency = "USD";
        String externalId = "ext";
        String payeeNote = "payee";
        String payerMessage = "mes";

        TransferBodyRequest.Payee payee = new TransferBodyRequest.Payee();
        payee.setPartyId("id");
        payee.setPartyIdType("type");

        TransferBodyRequest disbursementsTransfer = new TransferBodyRequest();
        disbursementsTransfer.setAmount(amount);
        disbursementsTransfer.setCurrency(currency);
        disbursementsTransfer.setExternalId(externalId);
        disbursementsTransfer.setPayeeNote(payeeNote);
        disbursementsTransfer.setPayerMessage(payerMessage);
        disbursementsTransfer.setPayee(payee);

        assertEquals(amount, disbursementsTransfer.getAmount());
        assertEquals(currency, disbursementsTransfer.getCurrency());
        assertEquals(externalId, disbursementsTransfer.getExternalId());
        assertEquals(payeeNote, disbursementsTransfer.getPayeeNote());
        assertEquals(payerMessage, disbursementsTransfer.getPayerMessage());
        assertNotNull(payee);
        assertEquals("id", disbursementsTransfer.getPayee().getPartyId());
        assertEquals("type", disbursementsTransfer.getPayee().getPartyIdType());
    }

    @Test
    public void testTransfer() {
        float amount = 9;
        String currency = "USD";
        String externalId = "ext";
        String financialTransactionId = "fin";
        String payeeNote = "payee";
        String payerMessage = "mes";
        String reason = "reason";
        String status = "status";

        Transfer.Payee payee = new Transfer.Payee();
        payee.setPartyId("id");
        payee.setPartyIdType("type");

        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setCurrency(currency);
        transfer.setExternalId(externalId);
        transfer.setFinancialTransactionId(financialTransactionId);
        transfer.setPayeeNote(payeeNote);
        transfer.setPayerMessage(payerMessage);
        transfer.setPayee(payee);
        transfer.setReason(reason);
        transfer.setStatus(status);

        assertEquals(amount, transfer.getAmount(), 0);
        assertEquals(currency, transfer.getCurrency());
        assertEquals(externalId, transfer.getExternalId());
        assertEquals(financialTransactionId, transfer.getFinancialTransactionId());
        assertEquals(payeeNote, transfer.getPayeeNote());
        assertEquals(payerMessage, transfer.getPayerMessage());
        assertEquals(reason, transfer.getReason());
        assertEquals(status, transfer.getStatus());
        assertNotNull(payee);
        assertEquals("id", transfer.getPayee().getPartyId());
        assertEquals("type", transfer.getPayee().getPartyIdType());
    }

    @Test
    public void testTransferBodyRequest() {
        String amount = "9";
        String currency = "USD";
        String externalId = "ext";
        String payeeNote = "payee";
        String payerMessage = "mes";

        TransferBodyRequest.Payee payee = new TransferBodyRequest.Payee();
        payee.setPartyId("id");
        payee.setPartyIdType("type");

        TransferBodyRequest remittancesTransfer = new TransferBodyRequest();
        remittancesTransfer.setAmount(amount);
        remittancesTransfer.setCurrency(currency);
        remittancesTransfer.setExternalId(externalId);
        remittancesTransfer.setPayeeNote(payeeNote);
        remittancesTransfer.setPayerMessage(payerMessage);
        remittancesTransfer.setPayee(payee);

        assertEquals(amount, remittancesTransfer.getAmount());
        assertEquals(currency, remittancesTransfer.getCurrency());
        assertEquals(externalId, remittancesTransfer.getExternalId());
        assertEquals(payeeNote, remittancesTransfer.getPayeeNote());
        assertEquals(payerMessage, remittancesTransfer.getPayerMessage());
        assertNotNull(payee);
        assertEquals("id", remittancesTransfer.getPayee().getPartyId());
        assertEquals("type", remittancesTransfer.getPayee().getPartyIdType());
    }
}
