package level0;

import java.util.Objects;

public class Number {

    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public Number(String value) {
        this.value = parseValue(value);
    }

    private int parseValue(String value) {
        int val;

        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("숫자가 아닌 입력입니다.");
        }

        if (val < 0) {
            throw new RuntimeException("음수는 허용되지 않습니다.");
        }

        return val;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Number number)) {
            return false;
        }
        return value == number.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "level0.Number{" +
               "value=" + value +
               '}';
    }
}
