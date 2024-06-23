package uz.ichange.billing.billing.payme.exception;


public class TimeException extends ApiException {

    protected static final String name = "TIME_";

    public TimeException(ExceptionType type) {
        super(name + type.toString());
    }
}
