package uz.ichange.billing.billing.exception;

public class ParamsException extends ApiException {

    protected static final String name = "PARAMS_";

    public ParamsException(ExceptionType type) {
        super(name + type.toString());
    }
}
