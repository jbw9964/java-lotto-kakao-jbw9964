package lottery.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lottery.domain.LotteryExpression.NumberExpression;

public class LotteryNumber implements Comparable<LotteryNumber> {

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
    public int compareTo(LotteryNumber o) {
        return Integer.compare(this.number, o.number);
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

    public static List<LotteryNumber> getRandomLotteryNumbers(int length) {
        return EveryPossibleLotteryNumbers.shuffleThenGetNumberList(length);
    }

    private static class EveryPossibleLotteryNumbers {

        private static final List<LotteryNumber> lotteryNumbers
                = IntStream.rangeClosed(LOTTERY_MIN_NUMBER, LOTTERY_MAX_NUMBER)
                .mapToObj(LotteryNumber::new)
                .collect(Collectors.toList());

        private static List<LotteryNumber> shuffleThenGetNumberList(int length) {
            validate(length);

            shuffleNumbers();

            return Collections.unmodifiableList(
                    lotteryNumbers.subList(0, length)
            );
        }

        private static void validate(int length) {
            if (length < 0) {
                throw new IllegalArgumentException(
                        "0 보다 적은 길이의 랜덤 로또 숫자 목록은 제공할 수 없습니다."
                );
            }

            int availableRandomLotteryNumbersLength = lotteryNumbers.size();

            if (length > availableRandomLotteryNumbersLength) {
                throw new IllegalStateException(String.format(
                        "[%d - %d] 범위의 로또 숫자로부터 %d 개의 숫자를 제공할 수 없습니다.",
                        LOTTERY_MIN_NUMBER, LOTTERY_MAX_NUMBER,
                        length
                ));
            }
        }

        private static void shuffleNumbers() {
            Collections.shuffle(lotteryNumbers);
        }
    }
}
