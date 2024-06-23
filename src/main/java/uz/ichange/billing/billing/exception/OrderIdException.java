package uz.ichange.billing.billing.exception;

public class OrderIdException extends ApiException {

    protected static final String name = "ORDER_ID_";

    public OrderIdException(ExceptionType type) {
        super(name + type.toString());
    }
}
