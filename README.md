# MoMo API Java Client

This is a Java client for the new MTN Mobile Money API ([MoMo API](https://momodeveloper.mtn.com)).

## Getting Started

### Gradle
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
    implementation 'com.github.regisbamba:momoapi-java:0.0.1'
}
```

### Maven
First, add the JitPack repository
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then, add the dependency 
```
<dependency>
    <groupId>com.github.regisbamba</groupId>
    <artifactId>momoapi-java</artifactId>
    <version>0.0.1</version>
</dependency>
```

For other build systems, please see instructions on [Jitpack's website](https://jitpack.io/#regisbamba/momoapi-java/0.0.1).

## Usage
To start using the API, make sure you first open an account on the  ([MoMo API](https://momodeveloper.mtn.com)) portal. You will need to subscribe to products on the portal before you can use them through the API.

### Getting a new client
Once that's done, you can go ahead and get a new instance of a MoMo API client.

```java
MoMo momo = new MoMo(Environment.SANDBOX);
```
This sets up your client in the sandbox environment.

### Working with products
As mentioned in order to start working with products on the API, you first need to subscribe to a product on the portal and get the subscription key - primary or second.

In your code, you can then do:
```java
String subscriptionKey = "XXXX";
MoMo momo = new MoMo(Environment.SANDBOX);
Collections collections = momo.subscribeToCollections(subscriptionKey);
```

The **collections** instance above will allow you to do all operations available through the Collections product of the MoMo API.

Example : Create an API user for collections.
```java
collections.createApiUser();
```
This MoMo API client library uses Reactive Programming via [RxJava](https://github.com/ReactiveX/RxJava). All API ressources are provided via **Observable** streams.

When you make a request with MoMo client, you can get the results by subscribing to the Observable.

Eg: Subscribe to get the referenceId when you create a new api user.
```java
collections.createApiUser().subscribe(
    new Consumer<String>() {
        @Override
        public void accept(String referenceId) { // This function executes in case of success.
             System.out.println(referenceId);
            // eg: ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
        }
    }
);
```

You can also consume error events in case of the API request failed.
```java
collections.createApiUser().subscribe(
    new Consumer<String>() {
        @Override
        public void accept(String referenceId) {  // This function executes in case of success.         
            // The referenceId for the user you just created.
            // eg: ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
        }
    },
    new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) throws Exception { // This function executes in case of errors.
            // if you cast the exception to RequestException, you can get the HTTP code and message returned by the MoMo API.
            RequestException e = (RequestException) throwable;
            System.out.println(String.format("\nHTTP Code: %s\nMessage: %s", e.getCode(), e.getMessage()));
            // HTTP Code: 401
            // Message : Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.
        }
    }
);
```

If you use Java 8, you can use lambda functions for more clarity.
```java
collections.createApiUser().subscribe(
    referenceId -> {
        System.out.println(referenceId);
        // ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
    }
);
```

```java
collections.createApiUser().subscribe(
    referenceId -> { // This function executes in case of success.
        System.out.println(referenceId);
        // ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
    },
    throwable -> { // This function executes in case of error.
        RequestException e = (RequestException) throwable;
        System.out.println(String.format("\nHTTP Code: %s\nMessage: %s", e.getCode(), e.getMessage())));
        // Message : Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.
    }
);
```

## Reference

### Create a MoMo client
You create a new MoMo client by specifying the environment (either SANDBOX OR PRODUCTION).
```java
MoMo momo = new MoMo(Environment.SANDBOX);
```

### Subscribe to a product
You subscribe to a product using the client you created.
```java
MoMo momo = new MoMo(Environment.SANDBOX);
Collections collections = momo.subscribeToCollections(subscriptionKey);
```  

### Authenticate your requests
According to (documentation)[https://momodeveloper.mtn.com/api-documentation/api-description/] :
There are two credentials used :
  - Subscription Key
  - API User and API Key to generate a Bearer Token for Oauth 2.0  

The Subscription Key is available when you subscribe to a product in the portal.
The API User and API Key are used to grant access to the wallet system in a specific country.

In production environment, API User and Api Key are wholly managed by the merchant through Partner Portal.
In sandbox environment, a provisioning API is exposed to enable developers generate own API User and API Key for testing purposes only.

This java client exposes methods to create Api user and Key in Sandbox, but if you are in production, you should create your token straight using Api User and Api Key from the portal.
 
### Sandbox User provisioning
Documentation : (https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api)[https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api]

First subscribe to a product :
```java
MoMo momo = new MoMo(Environment.SANDBOX);
Collections collections = momo.subscribeToCollections(subscriptionKey);
```  

#### Create an Api User (Sandbox Only)
Create an Api User and get the referenceId back.
Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/post-v1_0-apiuser

```java
collections.createApiUser().subscribe(referenceId -> {
    System.out.println(referenceId);
    // db0fc432-c940-4116-bbd1-887ab663e2a3
});
```

You can also specify a providerCallbackHost parameter.
```java
collections.createApiUser("www.myapp.com").subscribe(referenceId -> {
    System.out.println(referenceId);
    // 0812e642-5692-463b-8dce-370af19802c8
});
```

#### Create an Api Key (Sandbox Only)
Create an Api Key using the referenceId from Api User.
Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/post-v1_0-apiuser-apikey?

```java
collections.createApiKey(referenceId).subscribe(apiCredentials -> {
    System.out.print(apiCredentials.getUser()); // 822b8ea9-cc34-47b8-adcc-23a9a468b0df
    System.out.print(apiCredentials.getKey());  // 06796ba6ab714c4990b068dcfac66d88
});
```


#### Get an Api User (Sandbox Only)
Get an Api User record.
Documentation: https://momodeveloper.mtn.com/docs/services/sandbox-provisioning-api/operations/get-v1_0-apiuser?
```java
collections.getApiUser(referenceId).subscribe(apiUser -> {
    System.out.println(apiUser.getProviderCallbackHost()); // www.myapp.com
    System.out.println(apiUser.getTargetEnvironment()); // sandbox
});
```


### Collections
The Collections product enable remote collection of bills, fees or taxes.
Documentation: (https://momodeveloper.mtn.com/docs/services/collection)[https://momodeveloper.mtn.com/docs/services/collection]


