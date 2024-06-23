package uz.ichange.billing.billing.exception;

public class ToTimeException extends ApiException {

    protected static final String name = "TO_TIME_";

    public ToTimeException(ExceptionType type) {
        super(name + type.toString());
    }
}
