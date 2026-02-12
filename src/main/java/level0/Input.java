package level0;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {

    public static final List<String> DEFAULT_DELIMINATORS = List.of(":", ",");

    private final String inputString;

    private final Set<String> deliminators;

    private final Numbers numbers;

    public Input(String inputString) {
        this.inputString = inputString;
        this.deliminators = parseDeliminators(inputString);
        this.numbers = parseNumbers(inputString);
    }

    private Set<String> parseDeliminators(String inputString) {
        Set<String> deliminators = new HashSet<>(
                DEFAULT_DELIMINATORS
        );

        Matcher m = Pattern.compile("//(.)\\\\n(.*)").matcher(inputString);
        if (m.find()) {
            String customDelimiter = m.group(1);
            deliminators.add(customDelimiter);
        }
        return deliminators;
    }

    private Numbers parseNumbers(String inputString) {
        Matcher m = Pattern.compile("//(.)\\\\n(.*)").matcher(inputString);
        if (m.find()) {
            inputString = m.group(2);
        }

        String[] inputs = inputString.split(buildSplitter());

        /*
        변수명에 자료구조 타입(List)을 포함하고 있습니다.
            - 타입 정보는 이미 선언부 List에 명시되어 있어 중복
            - 나중에 자료구조가 변경되면 변수명도 함께 수정해야 함
            - 변수명은 "어떤 타입인가"가 아니라 "무엇을 담고 있는가"를 표현

        --> List<Number> 변수명 numberList 에서 numbers 로 변경
        --> Stream api 사용
         */
        List<Number> numbers = Arrays.stream(inputs)
                .map(Number::new)
                .toList();

        return new Numbers(numbers);
    }

    private String buildSplitter() {
        StringBuilder sb = new StringBuilder();
        for (String deliminator : deliminators) {
            sb.append(deliminator).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Set<String> getDeliminators() {
        return deliminators;
    }

    public Numbers getNumbers() {
        return numbers;
    }
}
