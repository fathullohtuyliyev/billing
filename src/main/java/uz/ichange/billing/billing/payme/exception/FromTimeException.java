package uz.ichange.billing.billing.payme.exception;


public class FromTimeException extends ApiException {

    protected static final String name = "FROM_TIME_";

    public FromTimeException(ExceptionType type) {
        super(name + type.toString());
    }
}
