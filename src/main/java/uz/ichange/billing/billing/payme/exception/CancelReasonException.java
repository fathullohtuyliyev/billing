package uz.ichange.billing.billing.payme.exception;


public class CancelReasonException extends ApiException {

    protected static final String name = "CANCEL_REASON_";

    public CancelReasonException(ExceptionType type) {
        super(name + type.toString());
    }
}
