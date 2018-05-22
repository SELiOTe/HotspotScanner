package club.seliote.hotspotscanner.exception;

public class GetApplicationContextException extends Exception {

    public GetApplicationContextException() {
        super();
    }

    public GetApplicationContextException(String message) {
        super(message);
    }

    public GetApplicationContextException(Throwable cause) {
        super(cause);
    }

    public GetApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
