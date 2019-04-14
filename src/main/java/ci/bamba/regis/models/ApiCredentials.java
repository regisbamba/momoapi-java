package ci.bamba.regis.models;

public class ApiCredentials {

    private String user;
    private String key;

    public ApiCredentials(){

    }

    public ApiCredentials(String user, String key) {
        this.user = user;
        this.key = key;
    }

    public String getUser() {
        return user;
    }
    public String getKey() {
        return key;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
