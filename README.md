# MoMo API Java Client

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/regisbamba/momoapi-java/blob/master/License.md) [![Release](https://jitpack.io/v/regisbamba/momoapi-java.svg)](https://jitpack.io/#regisbamba/momoapi-java) [![Build Status](https://travis-ci.org/regisbamba/momoapi-java.svg?branch=master)](https://travis-ci.org/regisbamba/momoapi-java) [![codecov](https://codecov.io/gh/regisbamba/momoapi-java/branch/master/graph/badge.svg)](https://codecov.io/gh/regisbamba/momoapi-java)

This library helps you consume the new MTN Mobile Money API ([MoMo API](https://momodeveloper.mtn.com)).

Features :

- Support for Collections, Disbursements, Remittances
- Support for Sandbox User provisioning
- Automatically generate tokens for your requests, eg: collections.getAccountBalance() // no need to get token

## 1. Installation

To get started, you first have to add the JitPack repository to your root build.gradle file at the end of repositories section.

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the dependency to your dependencies section

```
dependencies {
    implementation 'com.github.regisbamba:momoapi-java:{latest-version}'
}
```

For other build systems, please see instructions on [Jitpack's website](https://jitpack.io/#regisbamba/momoapi-java).

## 2. Prerequesite

Before everything else, make sure you open an account on the ([MoMo API](https://momodeveloper.mtn.com)) portal.
You will need to subscribe to products on the portal before you can use them through the API and this client.

## 3. Consuming resources from the API

This library uses Reactive Programming via [RxJava](https://github.com/ReactiveX/RxJava). All API resources are provided via **Observable** streams.

When you make a request, you can get the results by subscribing to the Observable and check whether the request was successful or not.

Eg: Getting the balance for your account.

```java
collections.getAccountBalance().subscribe(
    new Consumer<AccountBalance>() {
        @Override
        public void accept(AccountBalance accountBalance) {                 // This function executes in case of success.
            System.out.println(accountBalance.getAvailableBalance());       // 900
        }
    }
);
```

You can also consume error events in case the API request failed.

```java
collections.getAccountBalance().subscribe(
    new Consumer<AccountBalance>() {
        @Override
        public void accept(AccountBalance accountBalance) {                 // This function executes in case of success.
            System.out.println(accountBalance.getAvailableBalance());       // 900
        }
    },
    new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception {          // This function executes in case of errors.
            RequestException e = (RequestException) throwable;              // Cast the throwable to RequestException to get the HTTP code and message returned by the MoMo API.
            System.out.println(e.getCode());                                // 401
            System.out.println(e.getMessage());                             // Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.
        }
    }
);
```

If you use Java 8, you can use lambda functions for more clarity.

```java
collections.getAccountBalance().subscribe(accountBalance -> {     // This function executes in case of success.
    System.out.println(accountBalance.getAvailableBalance());     // 900
});
```

```java
collections.getAccountBalance().subscribe(
    accountBalance -> {                                                 // This function executes in case of success.
        System.out.println(accountBalance.getAvailableBalance());       // 900
    },
    throwable -> {                                                      // This function executes in case of error.
        RequestException e = (RequestException) throwable;              // Cast the throwable to RequestException to get the HTTP code and message returned by the MoMo API.
        System.out.println(e.getCode());                                // 401
        System.out.println(e.getMessage());                             // Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.
    }
);
```

## 4. Reference

### 4.1 Create a MoMo client

Create a new MoMo client by specifying the environment (either SANDBOX OR PRODUCTION).

```java
MoMo momo = new MoMo(Environment.SANDBOX);
```

### 4.2 Authenticate your requests

According to [documentation](https://momodeveloper.mtn.com/api-documentation/api-description/), the credentials to be used are :

- Subscription Key
- API User and API Key to generate a Bearer Token for Oauth 2.0

The Subscription Key is available when you subscribe to a product via the portal.

The API User and API Key are used to grant access to the wallet system in a specific country. Please note that :

- In **production environment**, API User and API Key are wholly managed by you through Partner Portal.
- In **sandbox environment**, a provisioning API is exposed to enable developers generate their own API User and API Key for **testing purposes only**.

In simple terms, if you are in production you should copy and paste API User and API Key from the portal and store them as variables in your code.

If you are in sandbox, use the SandboxProvisioning class to generate API User and API Key as explained below.

First get a provisioning instance :

```java
SandboxProvisioning sandboxProvisioning = momo.createSandboxProvisioning(subscriptionKey);
```

#### 4.2.1 Create an API User (Sandbox Only)

Create an API User and get the referenceId back.

Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/post-v1_0-apiuser

```java
sandboxProvisioning.createApiUser().subscribe(referenceId -> {
    System.out.println(referenceId); // db0fc432-c940-4116-bbd1-887ab663e2a3
});
```

You can also specify a providerCallbackHost parameter.

```java
sandboxProvisioning.createApiUser("www.myapp.com").subscribe(referenceId -> {
    System.out.println(referenceId); // 0812e642-5692-463b-8dce-370af19802c8
});
```

#### 4.2.2 Create an API Key (Sandbox Only)

Create an API Key using the referenceId from API User.

Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/post-v1_0-apiuser-apikey?

```java
sandboxProvisioning.createApiKey(referenceId).subscribe(apiCredentials -> {
    System.out.print(apiCredentials.getUser()); // 822b8ea9-cc34-47b8-adcc-23a9a468b0df
    System.out.print(apiCredentials.getKey());  // 06796ba6ab714c4990b068dcfac66d88
});
```

#### 4.2.3 Get an API User (Sandbox Only)

Get an API User record.

Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/get-v1_0-apiuser?

```java
sandboxProvisioning.getApiUser(referenceId).subscribe(apiUser -> {
    System.out.println(apiUser.getProviderCallbackHost()); // www.myapp.com
    System.out.println(apiUser.getTargetEnvironment());    // sandbox
});
```

### 4.3 Get a product instance

To make a request for a particular product, you need to create an instance of that product.

```java
Collections collections = momo.createCollections(subscriptionKey, apiUser, apiKey);
```

You can also do so for Disbursements and Remittances.

### 4.4 Collections

The Collections product enable remote collection of bills, fees or taxes.

#### 4.4.1 Create a Token

Create a Bearer Token to authenticate your requests.

Documentation: https://momodeveloper.mtn.com/docs/services/collection/operations/token-POST?

```java
collections.createToken().subscribe(
    token -> {
        System.out.println(token.getAccessToken()); // eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0....
        System.out.println(token.getExpiresIn());   // 3600
        System.out.println(token.getTokenType());   // acess_token
        System.out.println(token.getApiUser());     // 74aebb95-5c4b-465a-83e6-a4cfa83ad2ff
        System.out.println(token.getApiKey());      // f9f5c67a0b4944f895dfedc719a2753e
    }
);
```

**Please note**: For all requests, you can either create your own token as explained above and pass it to your requests or you can let the client automatically generate it for you.

#### 4.4.2 Request to pay

Request a payment from a consumer (Payer).

Documentation:
https://momodeveloper.mtn.com/docs/services/collection/operations/requesttopay-POST?

```java
float amount = 900;
String currency = "EUR";                         // In Sandbox, this should be EUR.
String externalId = "201904141150";
String payerPartyId = "0022505777777";
String payerMessage = "This is your order 1234"; // Avoid special characters as it causes Error 500 from the API.
String payeeNote = "Order 1234";                 // Avoid special characters as it causes Error 500 from the API.

collections.requestToPay(amount, currency, externalId, payerPartyId, payerMessage, payeeNote)
    .subscribe(referenceId -> {
        System.out.println(referenceId); // e0c04c5b-e591-46fa-b3f9-92276fdfda4d
    }
);
```

#### 4.4.3 Get a status of a request to pay

Get the status of a request to pay.

Documentation: https://momodeveloper.mtn.com/docs/services/collection/operations/requesttopay-referenceId-GET?

```java
collections.getRequestToPay(referenceId).subscribe(
    requestToPay -> {
        System.out.println(requestToPay.getFinancialTransactionId());    // 521734614
        System.out.println(requestToPay.getStatus());                    // SUCCESSFUL
    }
);
```

#### 4.4.4 Get the balance of the account.

Get the balance of the account.

Documentation: https://momodeveloper.mtn.com/docs/services/collection/operations/get-v1_0-account-balance?

```java
collections.getAccountBalance().subscribe(accountBalance -> {
    System.out.println(accountBalance.getAvailableBalance());   // 900
    System.out.println(accountBalance.getCurrency());           // EUR
});
```

#### 4.4.5 Get the status of an account holder

Check if an account holder is registered and active in the system.

Documentation: https://momodeveloper.mtn.com/docs/services/collection/operations/get-v1_0-accountholder-accountholderidtype-accountholderid-active?

```java
collections.getAccountStatus("46733123453").subscribe(accountStatus -> {
    System.out.println(accountStatus.getResult()); // true
});
```

### 4.5 Disbursements

The Disbursement product lets you automatically deposit funds to multiple users in one transaction.

#### 4.5.1 Create a Token

Create a Bearer Token to authenticate your requests.

Documentation: https://momodeveloper.mtn.com/docs/services/disbursement/operations/token-POST?

```java
disbursements.createToken().subscribe(
    token -> {
        System.out.println(token.getAccessToken()); // eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0....
        System.out.println(token.getExpiresIn());   // 3600
        System.out.println(token.getTokenType());   // acess_token
        System.out.println(token.getApiUser());     // 74aebb95-5c4b-465a-83e6-a4cfa83ad2ff
        System.out.println(token.getApiKey());      // f9f5c67a0b4944f895dfedc719a2753e
    }
);
```

**Please note**: For all requests, you can either create your own token as explained above and pass it to your requests or you can let the client automatically generate it for you.

#### 4.5.2 Transfer

Transfer an amount to a payee account.

Documentation:
https://momodeveloper.mtn.com/docs/services/disbursement/operations/transfer-POST?

```java
float amount = 900;
String currency = "EUR";                         // In Sandbox, this should be EUR.
String externalId = "201904141150";
String payeePartyId = "0022505777777";
String payerMessage = "This is your order 1234"; // Avoid special characters as it causes Error 500 from the API.
String payeeNote = "Order 1234";                 // Avoid special characters as it causes Error 500 from the API.

disbursements.transfer(amount, currency, externalId, payeePartyId, payerMessage, payeeNote)
    .subscribe(referenceId -> {
        System.out.println(referenceId); // e0c04c5b-e591-46fa-b3f9-92276fdfda4d
    }
);
```

#### 4.5.3 Get a status of a transfer

Get the status of a transfer.

Documentation: https://momodeveloper.mtn.com/docs/services/disbursement/operations/transfer-referenceId-GET?

```java
disbursements.getTransfer(referenceId).subscribe(
    transfer -> {
        System.out.println(transfer.getFinancialTransactionId());    // 521734614
        System.out.println(transfer.getStatus());                    // SUCCESSFUL
    }
);
```

#### 4.5.4 Get the balance of the account.

Get the balance of the account.

Documentation: https://momodeveloper.mtn.com/docs/services/disbursement/operations/get-v1_0-account-balance?

```java
disbursements.getAccountBalance().subscribe(accountBalance -> {
    System.out.println(accountBalance.getAvailableBalance());   // 900
    System.out.println(accountBalance.getCurrency());           // EUR
});
```

#### 4.5.5 Get the status of an account holder

Check if an account holder is registered and active in the system.

Documentation: https://momodeveloper.mtn.com/docs/services/disbursement/operations/get-v1_0-accountholder-accountholderidtype-accountholderid-active?

```java
disbursements.getAccountStatus("46733123453").subscribe(accountStatus -> {
    System.out.println(accountStatus.getResult()); // true
});
```

### 4.6 Remittances

The Remittance product lets you automatically deposit funds to multiple users in one transaction.

#### 4.6.1 Create a Token

Create a Bearer Token to authenticate your requests.

Documentation: https://momodeveloper.mtn.com/docs/services/remittance/operations/token-POST?

```java
remittances.createToken().subscribe(
    token -> {
        System.out.println(token.getAccessToken()); // eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0....
        System.out.println(token.getExpiresIn());   // 3600
        System.out.println(token.getTokenType());   // acess_token
        System.out.println(token.getApiUser());     // 74aebb95-5c4b-465a-83e6-a4cfa83ad2ff
        System.out.println(token.getApiKey());      // f9f5c67a0b4944f895dfedc719a2753e
    }
);
```

**Please note**: For all requests, you can either create your own token as explained above and pass it to your requests or you can let the client automatically generate it for you.

#### 4.6.2 Transfer

Transfer an amount to a payee account.

Documentation:
https://momodeveloper.mtn.com/docs/services/remittance/operations/transfer-POST?

```java
float amount = 900;
String currency = "EUR";                         // In Sandbox, this should be EUR.
String externalId = "201904141150";
String payeePartyId = "0022505777777";
String payerMessage = "This is your order 1234"; // Avoid special characters as it causes Error 500 from the API.
String payeeNote = "Order 1234";                 // Avoid special characters as it causes Error 500 from the API.

remittances.transfer(amount, currency, externalId, payeePartyId, payerMessage, payeeNote)
    .subscribe(referenceId -> {
        System.out.println(referenceId); // e0c04c5b-e591-46fa-b3f9-92276fdfda4d
    }
);
```

#### 4.6.3 Get a status of a transfer

Get the status of a transfer.

Documentation: https://momodeveloper.mtn.com/docs/services/remittance/operations/transfer-referenceId-GET?

```java
remittances.getTransfer(referenceId).subscribe(
    transfer -> {
        System.out.println(transfer.getFinancialTransactionId());    // 521734614
        System.out.println(transfer.getStatus());                    // SUCCESSFUL
    }
);
```

#### 4.6.4 Get the balance of the account.

Get the balance of the account.

Documentation: https://momodeveloper.mtn.com/docs/services/remittance/operations/get-v1_0-account-balance?

```java
remittances.getAccountBalance().subscribe(accountBalance -> {
    System.out.println(accountBalance.getAvailableBalance());   // 900
    System.out.println(accountBalance.getCurrency());           // EUR
});
```

#### 4.6.5 Get the status of an account holder

Check if an account holder is registered and active in the system.

Documentation: https://momodeveloper.mtn.com/docs/services/remittance/operations/get-v1_0-accountholder-accountholderidtype-accountholderid-active?

```java
remittances.getAccountStatus("46733123453").subscribe(accountStatus -> {
    System.out.println(accountStatus.getResult()); // true
});
```
