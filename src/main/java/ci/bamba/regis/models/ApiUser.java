package ci.bamba.regis.models;

public class ApiUser {

    private String providerCallbackHost;
    private String targetEnvironment;

    public ApiUser(){

    }

    public ApiUser(String providerCallbackHost, String targetEnvironment) {
        this.providerCallbackHost = providerCallbackHost;
        this.targetEnvironment = targetEnvironment;
    }

    public String getProviderCallbackHost() {
        return providerCallbackHost;
    }

    public void setProviderCallbackHost(String providerCallbackHost) {
        this.providerCallbackHost = providerCallbackHost;
    }

    public String getTargetEnvironment() {
        return targetEnvironment;
    }

    public void setTargetEnvironment(String targetEnvironment) {
        this.targetEnvironment = targetEnvironment;
    }


}
