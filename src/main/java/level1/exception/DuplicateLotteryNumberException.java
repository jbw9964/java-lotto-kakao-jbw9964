package level1.exception;

public class DuplicateLotteryNumberException extends RuntimeException {

    public DuplicateLotteryNumberException(String message) {
        super(message);
    }
}
