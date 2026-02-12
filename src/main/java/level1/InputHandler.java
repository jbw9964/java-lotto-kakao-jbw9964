package level1;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int inputPrice() {
        return this.parseInt(scanner.nextLine());
    }

    public List<Integer> inputLastAnswerLottery() {
        return Arrays.stream(scanner.nextLine().split(",\\s*"))
                .map(this::parseInt)
                .toList();
    }

    public int inputBonusLotteryNumber() {
        return this.parseInt(scanner.nextLine());
    }

    private int parseInt(String given) {
        try {
            return Integer.parseInt(given);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("주어진 문자열이 숫자가 아닙니다.");
        }
    }
}
