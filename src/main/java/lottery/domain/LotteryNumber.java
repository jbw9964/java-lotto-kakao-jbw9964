package lottery.domain;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import lottery.domain.LotteryExpression.NumberExpression;

public class LotteryNumber {

    private static final int
            LOTTERY_MIN_NUMBER = 1,
            LOTTERY_MAX_NUMBER = 45;

    private final int number;

    public LotteryNumber(int number) {
        validateNumber(number);
        this.number = number;
    }

    private static void validateNumber(int number) {
        if (number < LOTTERY_MIN_NUMBER || number > LOTTERY_MAX_NUMBER) {
            throw new IllegalArgumentException(String.format(
                    "로또 번호는 [%d - %d] 범위의 숫자만 가능합니다.",
                    LOTTERY_MIN_NUMBER, LOTTERY_MAX_NUMBER
            ));
        }
    }

    public boolean numberEquals(int number) {
        return this.number == number;
    }

    public String representWith(NumberExpression numberExpression) {

        if (numberExpression == null) {
            throw new IllegalArgumentException("로또 번호 표현식은 Null 일수 없습니다.");
        }

        Function<Integer, String> numberToStringConverter = numberExpression.numberToStringConverter();

        return numberToStringConverter.apply(this.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LotteryNumber that)) {
            return false;
        }
        return number == that.number;
    }

    public static LotteryNumber createRandomLotteryNumber(Random random) {
        int number = random.nextInt(
                LOTTERY_MIN_NUMBER,
                LOTTERY_MAX_NUMBER + 1
        );

        return new LotteryNumber(number);
    }
}
