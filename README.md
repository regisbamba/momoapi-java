# MoMo API Java Client

This is a Java client for the new MTN Mobile Money API ([MoMo API](https://momodeveloper.mtn.com)).

## Getting Started

### Gradle
First, add the JitPack repository to your root build.gradle file at the end of repositories section.
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
First, make sure, you have opened an account on the  ([MoMo API](https://momodeveloper.mtn.com)) portal. You will need to subscribe to products on the portal before you can use them through the API.

### Getting a new client
Once that's all done, you can go ahead and get a new instance of a MoMo API client.

```
MoMo momo = new MoMo(Environment.SANDBOX);
```
This sets up your client in the sandbox environment.

### Working with products
As mentionned in order to start working with products on the API, you first need to subscribe to a product on the portal and get the subscription key - primary or second.

In your code, you can then do:
```
String subscriptionKey = "XXXX";
MoMo momo = new MoMo(Environment.SANDBOX);
Collections collections = momo.subscribeToCollections(subscriptionKey);
```

The **collections** instance above wiill allow you to do all operations available through the Collections product of the MoMo API.

Example : Create an API user for collections.
```
...
collections.createApiUser("www.mycallback.com");

```
This MoMo API client library uses Reactive Programming via [RxJava](https://github.com/ReactiveX/RxJava). All API ressources are provided via **Observable** streams.

```
collections.createApiUser("www.mycallback.com").subscribe(referenceId -> {
	// This section executes in case of success.
	// You just created a user, so do what you want with the reference ID.
}, throwable -> {
	// This section executes in case of errors.
	// if you cast the exeception to RequestException, you can get the HTTP code and message returned by the MoMo API.
	RequestException e = (RequestException) throwable;
	System.out.println(String.format("\nHTTP Code: %s\nMessage: %s", e.getCode(), e.getMessage())));
});
```

TODO : Add more example to README.