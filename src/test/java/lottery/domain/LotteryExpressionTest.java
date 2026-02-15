package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.IntStream;
import lottery.domain.LotteryExpression.NumberExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryExpressionTest {

    private static final List<LotteryNumber> oneToSix = IntStream.rangeClosed(1, 6)
            .mapToObj(LotteryNumber::new)
            .toList();

    @Test
    @DisplayName("주어진 로또 표현식으로 로또를 표현할 수 있다.")
    void testLotteryRepresentation() {
        String leftBracket = "[{ |";
        String rightBracket = "| }]";
        String deliminator = ":";

        LotteryExpression representation = new LotteryExpression(
                leftBracket, rightBracket, deliminator
        );

        String expectedRepresentation = "[{ |1:2:3:4:5:6| }]";

        Lottery lottery = new Lottery(oneToSix);
        assertThat(lottery.representWith(representation)).isEqualTo(expectedRepresentation);
    }

    @Test
    @DisplayName("Null 인 로또 표현식으로 로또를 표현할 수 없다.")
    void testNullLotteryExpression() {
        Lottery lottery = new Lottery(oneToSix);

        assertThatThrownBy(() -> lottery.representWith(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주어진 숫자 표현식으로 로또 번호를 표현할 수 있다.")
    void testNumberRepresentation() {
        NumberExpression representation = new NumberExpression(
                i -> String.format("[---%3d---]", i)
        );

        int number = 10;
        String expectedRepresentation = "[--- 10---]";

        LotteryNumber lotteryNumber = new LotteryNumber(number);
        assertThat(lotteryNumber.representWith(representation)).isEqualTo(expectedRepresentation);
    }

    @Test
    @DisplayName("Null 인 숫자 표현식으로 로또 번호를 표현할 수 없다.")
    void testNullNumberExpression() {
        LotteryNumber lotteryNumber = new LotteryNumber(10);

        assertThatThrownBy(() -> lotteryNumber.representWith(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}