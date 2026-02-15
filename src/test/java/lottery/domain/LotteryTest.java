package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryTest {

    private static final List<LotteryNumber> oneToTwenty = IntStream.rangeClosed(1, 20)
            .mapToObj(LotteryNumber::new)
            .toList();

    @Test
    @DisplayName("로또 번호는 오직 6 자리만 허용한다.")
    void testInvalidNumberSize() {
        List<LotteryNumber> insufficientNumbers = oneToTwenty.subList(0, 4);
        List<LotteryNumber> tooManyNumbers = oneToTwenty.subList(0, 7);
        List<LotteryNumber> validNumbers = oneToTwenty.subList(0, 6);

        assertThatThrownBy(() -> new Lottery(insufficientNumbers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Lottery(tooManyNumbers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Lottery(validNumbers))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("중복된 로또 번호는 허용되지 않는다.")
    void testDuplicateNumber() {
        List<LotteryNumber> duplicateNumbers = Stream.of(1, 2, 3, 4, 5, 5)
                .map(LotteryNumber::new)
                .toList();

        assertThatThrownBy(() -> new Lottery(duplicateNumbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("어느 수가 로또에 속하는지 알수 있다.")
    void testContains() {
        List<LotteryNumber> oneToSix = oneToTwenty.subList(0, 6);
        List<LotteryNumber> sixToTwelve = oneToTwenty.subList(6, 12);

        Lottery lottery1 = new Lottery(oneToSix);
        Lottery lottery2 = new Lottery(sixToTwelve);

        for (LotteryNumber lotteryNumber : oneToSix) {
            assertThat(lottery1.contains(lotteryNumber)).isTrue();
            assertThat(lottery2.contains(lotteryNumber)).isFalse();
        }

        for (LotteryNumber lotteryNumber : sixToTwelve) {
            assertThat(lottery1.contains(lotteryNumber)).isFalse();
            assertThat(lottery2.contains(lotteryNumber)).isTrue();
        }
    }

    @Test
    @DisplayName("다른 로또와 겹치는 번호 수를 알수 있다.")
    void testCountMatchingLotteryNumbers() {

        List<LotteryNumber> baseNumbers = oneToTwenty.subList(0, 6);
        Lottery baseLottery = new Lottery(baseNumbers);

        for (int numOfMatches = 0; numOfMatches <= 6; numOfMatches++) {

            int fromIndex = 6 - numOfMatches;
            int toIndex = fromIndex + 6;

            //noinspection ConstantValue
            assert 0 <= fromIndex;
            assert toIndex <= oneToTwenty.size();

            List<LotteryNumber> numbers = oneToTwenty.subList(fromIndex, toIndex);
            Lottery lottery = new Lottery(numbers);

            assertThat(baseLottery.countMatchingLotteryNumbers(lottery)).isEqualTo(numOfMatches);
            assertThat(lottery.countMatchingLotteryNumbers(baseLottery)).isEqualTo(numOfMatches);
        }
    }

    @Test
    @DisplayName("길이 규칙을 만족하는 랜덤 로또를 생성할 수 있다.")
    void testCreateRandomLottery() {
        Random random = new Random();

        Lottery randomLottery = Lottery.createRandomLottery(random);

        assertThat(randomLottery).isNotNull();
        assertThat(randomLottery.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("주어진 표현식으로 로또를 표현할 수 있다.")
    void testRepresentWith() {
        String leftBracket = "[{ ";
        String rightBracket = " }]";
        String deliminator = ":";

        LotteryExpression representation = new LotteryExpression(
                leftBracket, rightBracket, deliminator
        );

        List<LotteryNumber> numbers = oneToTwenty.subList(0, 6);
        String expectedRepresentation = "[{ 1:2:3:4:5:6 }]";

        Lottery lottery = new Lottery(numbers);
        assertThat(lottery.representWith(representation)).isEqualTo(expectedRepresentation);
    }

    @Test
    @DisplayName("Null 인 표현식으로 로또를 표현할 수 없다.")
    void testNullExpression() {
        List<LotteryNumber> numbers = oneToTwenty.subList(0, 6);
        Lottery lottery = new Lottery(numbers);

        assertThatThrownBy(() -> lottery.representWith(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
