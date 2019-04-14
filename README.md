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

```
MoMo momo = new MoMo(Environment.SANDBOX);
```
This sets up your client in the sandbox environment.

### Working with products
As mentioned in order to start working with products on the API, you first need to subscribe to a product on the portal and get the subscription key - primary or second.

In your code, you can then do:
```
String subscriptionKey = "XXXX";
MoMo momo = new MoMo(Environment.SANDBOX);
Collections collections = momo.subscribeToCollections(subscriptionKey);
```

The **collections** instance above will allow you to do all operations available through the Collections product of the MoMo API.

Example : Create an API user for collections.
```
collections.createApiUser();
```
This MoMo API client library uses Reactive Programming via [RxJava](https://github.com/ReactiveX/RxJava). All API ressources are provided via **Observable** streams.

When you make a request with MoMo client, you can get the results by subscribing to the Observable.
Eg: Subscribe to get the referenceId when you create a new api user.
```
collections.createApiUser().subscribe(
    new Consumer<String>() {
        @Override
        public void accept(String referenceId) { // This section executes in case of success.
             System.out.println(referenceId);
            // eg: ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
        }
    }
);
```

You can also consume error events in case of the API request failed.
```
collections.createApiUser().subscribe(
    new Consumer<String>() {
        @Override
        public void accept(String referenceId) {  // This function executes in case of success.         
            // The referenceId for the apiUser you just created.
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
```
collections.createApiUser().subscribe(
    referenceId -> {
        System.out.println(referenceId);
        // ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
    }
);
```

```
collections.createApiUser().subscribe(
    referenceId -> { // This section only executes in case of success.
        System.out.println(referenceId);
        // ea6d052a-8bf4-451e-8fc1-40e7cac6aacc
    },
    throwable -> { // This section only executes in case of error.
        RequestException e = (RequestException) throwable;
        System.out.println(String.format("\nHTTP Code: %s\nMessage: %s", e.getCode(), e.getMessage())));
        // Message : Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.
    }
);
```

TODO : Add more example to README.
