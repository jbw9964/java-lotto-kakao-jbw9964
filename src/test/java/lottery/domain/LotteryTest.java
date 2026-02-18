package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
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
        List<LotteryNumber> fiveNumbers = oneToTwenty.subList(0, 4);
        List<LotteryNumber> sevenNumbers = oneToTwenty.subList(0, 7);
        List<LotteryNumber> sixNumbers = oneToTwenty.subList(0, 6);

        assertThatThrownBy(() -> new Lottery(fiveNumbers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Lottery(sevenNumbers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Lottery(sixNumbers))
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
        Lottery randomLottery = Lottery.createRandomLottery();

        assertThat(randomLottery).isNotNull();
        assertThat(randomLottery.size()).isEqualTo(6);
    }
}
