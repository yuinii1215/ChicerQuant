package AnyQuantProject.util.exception;

/**
 * Created by G on 16/4/15.
 */
public class NetFailedException extends Exception {
    String code;

    public NetFailedException(String code) {
        super();
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
