package level1.exception;

public class InvalidLotteryNumberLengthException extends RuntimeException {

    public InvalidLotteryNumberLengthException(String message) {
        super(message);
    }
}
