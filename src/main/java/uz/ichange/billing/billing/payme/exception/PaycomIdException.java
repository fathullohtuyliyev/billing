package uz.ichange.billing.billing.payme.exception;

public class PaycomIdException extends ApiException {

    protected static final String name = "PAYCOM_ID_";

    public PaycomIdException(ExceptionType type) {
        super(name + type.toString());
    }
}
