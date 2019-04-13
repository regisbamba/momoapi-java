package ci.bamba.regis.exceptions;

public class RequestException extends RuntimeException {

    private int code;
    private String message;

    public RequestException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
