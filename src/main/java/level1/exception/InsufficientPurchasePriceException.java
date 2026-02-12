package level1.exception;

public class InsufficientPurchasePriceException extends RuntimeException {

    public InsufficientPurchasePriceException(String message) {
        super(message);
    }
}
