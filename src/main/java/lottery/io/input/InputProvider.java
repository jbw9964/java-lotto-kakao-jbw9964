package lottery.io.input;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputProvider {

    private static final String LIST_DELIMINATOR = ",\\s*";

    private final Scanner scanner;

    public InputProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    public int provideLineAsSingleInt() {
        String line = scanner.nextLine();
        return parseInt(line);
    }

    private static int parseInt(String given) {
        try {
            return Integer.parseInt(given);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("주어진 문자를 숫자로 바꾸는데 실패했습니다.");
        }
    }

    public List<Integer> provideLineAsIntList() {
        String input = scanner.nextLine();
        return Arrays.stream(input.split(LIST_DELIMINATOR))
                .map(InputProvider::parseInt)
                .toList();
    }
}
