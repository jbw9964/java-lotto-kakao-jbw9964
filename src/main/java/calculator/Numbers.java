package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Numbers {

    private final List<calculator.Number> numbers;

    public Numbers(calculator.Number... numbers) {
        this.numbers = Arrays.asList(numbers);
    }

    public Numbers(List<Number> numbers) {
        this.numbers = numbers;
    }

    public calculator.Number getSum() {
        int result = 0;
        for (calculator.Number number : numbers) {
            result += number.getValue();
        }
        return new calculator.Number(result);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Numbers numbers1)) {
            return false;
        }
        return Objects.equals(numbers, numbers1.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numbers);
    }
}
