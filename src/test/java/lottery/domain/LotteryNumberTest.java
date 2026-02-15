package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lottery.domain.LotteryExpression.NumberExpression;
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
    @DisplayName("최대 최소 규칙을 만족하는 랜덤 로또 숫자를 생성할 수 있다.")
    void testCreateRandomLotteryNumber() {
        // 확률적으로 4_050 번 시도했을 때 95% 확률로 모든 숫자가 적어도 한번씩은 뽑힘
        int testSize = 10_000;

        Random random = new Random();
        Set<LotteryNumber> everyLotteryNumbers = IntStream.rangeClosed(1, 45)
                .mapToObj(LotteryNumber::new)
                .collect(Collectors.toSet());

        for (int test = 0; test < testSize; test++) {
            LotteryNumber randomLotteryNumber = LotteryNumber.createRandomLotteryNumber(random);

            assertThat(everyLotteryNumbers).contains(randomLotteryNumber);
        }
    }

    @Test
    @DisplayName("주어진 표현식으로 로또 번호를 표현할 수 있다.")
    void testRepresentWith() {
        NumberExpression representation = new NumberExpression(
                i -> String.format("[---%2d---]", i)
        );

        int number = 10;
        String expectedRepresentation = "[---10---]";

        LotteryNumber lotteryNumber = new LotteryNumber(number);
        assertThat(lotteryNumber.representWith(representation)).isEqualTo(expectedRepresentation);
    }

    @Test
    @DisplayName("Null 인 표현식으로 로또 번호를 표현할 수 없다.")
    void testNullExpression() {
        LotteryNumber lotteryNumber = new LotteryNumber(10);

        assertThatThrownBy(() -> lotteryNumber.representWith(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}