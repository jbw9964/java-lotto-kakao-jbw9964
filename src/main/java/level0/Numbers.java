package level0;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Numbers {

    private final List<level0.Number> numbers;

    public Numbers(level0.Number... numbers) {
        this.numbers = Arrays.asList(numbers);
    }

    public Numbers(List<Number> numbers) {
        this.numbers = numbers;
    }

    public level0.Number getSum() {
        int result = 0;
        for (level0.Number number : numbers) {
            result += number.getValue();
        }
        return new level0.Number(result);
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
