package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryNumberTest {

    @Test
    @DisplayName("로또 번호는 [1 - 45] 범위의 숫자만 가능하다.")
    void testInvalidNumber() {
        List<Integer> invalidNumbers = List.of(
                -1, 0, 46, 100
        );

        for (int invalidNumber : invalidNumbers) {
            assertThatThrownBy(() -> new LotteryNumber(invalidNumber))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("로또 숫자가 일치하는지 확인할 수 있다.")
    void numberEquals() {
        int number1 = 1, number2 = 2;
        LotteryNumber lotteryNumber1 = new LotteryNumber(number1);
        LotteryNumber lotteryNumber2 = new LotteryNumber(number2);

        //noinspection ConstantValue
        assert number1 != number2;

        assertThat(lotteryNumber1.numberEquals(number1)).isTrue();
        assertThat(lotteryNumber2.numberEquals(number1)).isFalse();

        assertThat(lotteryNumber2.numberEquals(number1)).isFalse();
        assertThat(lotteryNumber2.numberEquals(number2)).isTrue();
    }

    @Test
    @DisplayName("동일한 숫자를 가진 LotteryNumber 는 동등하다.")
    void testEquals() {
        int number = 10;
        LotteryNumber lotteryNumber1 = new LotteryNumber(number);
        LotteryNumber lotteryNumber2 = new LotteryNumber(number);

        assertThat(lotteryNumber1.equals(lotteryNumber2)).isTrue();
        assertThat(lotteryNumber2.equals(lotteryNumber1)).isTrue();
    }

    @Test
    @DisplayName("주어진 길이만큼 랜덤 로또 번호 목록을 제공할 수 있다.")
    void testRandomLotteryNumbersLength() {
        int minLength = 0, maxLength = 45;

        for (int length = minLength; length <= maxLength; length++) {
            List<LotteryNumber> randomLotteryNumbers = LotteryNumber.getRandomLotteryNumbers(
                    length
            );

            assertThat(randomLotteryNumbers).isNotNull().hasSize(length);
        }
    }

    @Test
    @DisplayName("최대 최소 규칙을 만족하는 랜덤 로또 숫자를 생성할 수 있다.")
    void testRandomLotteryNumbersRange() {
        // 확률적으로 4_050 번 시도했을 때 95% 확률로 모든 숫자가 적어도 한번씩은 뽑힘
        int testSize = 10_000;

        Set<LotteryNumber> everyLotteryNumbers = IntStream.rangeClosed(1, 45)
                .mapToObj(LotteryNumber::new)
                .collect(Collectors.toSet());

        for (int test = 0; test < testSize; test++) {
            LotteryNumber randomLotteryNumber = LotteryNumber.getRandomLotteryNumbers(1)
                    .getFirst();

            assertThat(everyLotteryNumbers).contains(randomLotteryNumber);
        }
    }

    @Test
    @DisplayName("45 보다 큰 길이의 랜덤 로또 숫자 목록은 제공할 수 없다.")
    void testTooLargeRandomLotteryNumbersLength() {
        int maxima = 45;
        int tooLargeLength = maxima + 1;

        assertThatThrownBy(() -> LotteryNumber.getRandomLotteryNumbers(tooLargeLength))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("0 보다 적은 길이의 랜덤 로또 숫자 목록은 제공할 수 없다.")
    void testNegativeRandomLotteryNumbersLength() {
        int zeroLength = 0;
        int negativeLength = zeroLength - 1;

        assertThatThrownBy(() -> LotteryNumber.getRandomLotteryNumbers(negativeLength))
                .isInstanceOf(IllegalArgumentException.class);
    }
}