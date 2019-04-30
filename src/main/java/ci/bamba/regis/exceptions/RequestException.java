package ci.bamba.regis.exceptions;

public class RequestException extends RuntimeException {

    private static final long serialVersionUID = -4501718003875804300L;
    private int code;

    public RequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
