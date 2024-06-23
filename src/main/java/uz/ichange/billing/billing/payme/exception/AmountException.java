package uz.ichange.billing.billing.payme.exception;

public class AmountException extends ApiException {
    protected static final String name = "AMOUNT_";

    public AmountException(ExceptionType type) {
        super(name + type.toString());
    }
}
