package ci.bamba.regis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import ci.bamba.regis.models.AccountBalance;
import ci.bamba.regis.models.AccountStatus;
import ci.bamba.regis.models.ApiKey;
import ci.bamba.regis.models.ApiUser;
import ci.bamba.regis.models.CollectionsRequestToPay;
import ci.bamba.regis.models.DisbursementsTransfer;
import ci.bamba.regis.models.DisbursementsTransferBodyRequest;
import ci.bamba.regis.models.RemittancesTransfer;
import ci.bamba.regis.models.RemittancesTransferBodyRequest;
import ci.bamba.regis.models.Token;
import io.reactivex.Observable;
import ci.bamba.regis.models.CollectionsRequestToPayBodyRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RestClient {

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    return chain.proceed(request
                            .newBuilder()
                            .header("Content-Type", "application/json")
                            .method(request.method(), request.body()).build());
                });
        return builder.build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .create();
    }

    private static Retrofit getRestAdapter(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getClient())
                .build();
    }

    static Service getService(String baseUrl) {
        return getRestAdapter(baseUrl).create(Service.class);
    }

    public interface Service {

        @POST("v1_0/apiuser")
        Observable<Response<Void>> createApiUser(
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Reference-Id") String xReferenceId,
                @Body HashMap<String, String> body
        );

        @GET("v1_0/apiuser/{X-Reference-Id}")
        Observable<Response<ApiUser>> getApiUser(
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Path("X-Reference-Id") String xReferenceId
        );

        @POST("v1_0/apiuser/{X-Reference-Id}/apikey")
        Observable<Response<ApiKey>> createApiKey(
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Path("X-Reference-Id") String xReferenceId
        );

        @POST("{type}/token/")
        Observable<Response<Token>> createToken(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Path("type") String type
        );

        @POST("collection/v1_0/requesttopay")
        Observable<Response<Void>> collectionsCreateRequestToPay(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Reference-Id") String referenceId,
                @Header("X-Target-Environment") String targetEnvironment,
                @Body CollectionsRequestToPayBodyRequest body
        );

        @POST("disbursement/v1_0/transfer")
        Observable<Response<Void>> disbursementsCreateTransfer(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Reference-Id") String referenceId,
                @Header("X-Target-Environment") String targetEnvironment,
                @Body DisbursementsTransferBodyRequest body
        );

        @POST("remittance/v1_0/transfer")
        Observable<Response<Void>> remittancesCreateTransfer(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Reference-Id") String referenceId,
                @Header("X-Target-Environment") String targetEnvironment,
                @Body RemittancesTransferBodyRequest body
        );

        @GET("collection/v1_0/requesttopay/{referenceId}")
        Observable<Response<CollectionsRequestToPay>> collectionsGetRequestToPay(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Target-Environment") String targetEnvironment,
                @Path("referenceId") String referenceId
        );

        @GET("disbursement/v1_0/transfer/{referenceId}")
        Observable<Response<DisbursementsTransfer>> disbursementsGetTransfer(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Target-Environment") String targetEnvironment,
                @Path("referenceId") String referenceId
        );

        @GET("remittance/v1_0/transfer/{referenceId}")
        Observable<Response<RemittancesTransfer>> remittancesGetTransfer(
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Target-Environment") String targetEnvironment,
                @Path("referenceId") String referenceId
        );

        @GET("{type}/v1_0/account/balance")
        Observable<Response<AccountBalance>> getAccountBalance(
                @Path("type") String type,
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Target-Environment") String targetEnvironment
        );

        @GET("{type}/v1_0/accountholder/{accountHolderIdType}/{accountHolderId}/active ")
        Observable<Response<AccountStatus>> getAccountStatus(
                @Path("type") String type,
                @Header("Authorization") String authorization,
                @Header("Ocp-Apim-Subscription-Key") String subscriptionKey,
                @Header("X-Target-Environment") String targetEnvironment,
                @Path("accountHolderIdType") String accountHolderIdType,
                @Path("accountHolderId") String accountHolderId
        );


    }

}
