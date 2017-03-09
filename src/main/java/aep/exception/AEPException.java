package aep.exception;

/**
 * Created by yooyoung-mo on 2017. 3. 9..
 */
public class AEPException extends RuntimeException {
    public AEPException() {
    }

    public AEPException(String message) {
        super(message);
    }

    public AEPException(String message, Throwable cause) {
        super(message, cause);
    }

    public AEPException(Throwable cause) {
        super(cause);
    }
}
