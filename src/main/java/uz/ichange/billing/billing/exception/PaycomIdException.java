package uz.ichange.billing.billing.exception;

public class PaycomIdException extends ApiException {

    protected static final String name = "PAYCOM_ID_";

    public PaycomIdException(ExceptionType type) {
        super(name + type.toString());
    }
}
