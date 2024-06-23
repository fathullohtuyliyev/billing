package uz.ichange.billing.billing.payme.exception;

public class AccountNotFoundException extends ApiException {
    protected static final String name = "ACCOUNT_";

    public AccountNotFoundException(ExceptionType type) {
        super(name + type.toString());
    }
}
