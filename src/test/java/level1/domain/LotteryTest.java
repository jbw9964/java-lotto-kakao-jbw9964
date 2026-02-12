package level1.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.IntStream;
import level1.exception.DuplicateLotteryNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryTest {

    private static List<Integer> createNumbers(int startInclusive, int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive)
                .boxed()
                .toList();
    }

    @Test
    @DisplayName("중복된 번호는 허용되지 않는다.")
    void testDuplicateLottery() {
        int duplicateNumber = 1;

        List<Integer> duplicateNumbers = List.of(duplicateNumber, duplicateNumber);

        assertThatThrownBy(() -> new Lottery(duplicateNumbers))
                .isInstanceOf(DuplicateLotteryNumberException.class);
    }

    @Test
    @DisplayName("어느 수가 로또에 속하는지 알 수 있다.")
    void testContains() {
        int start = 1, end = 10;
        int another = 100;

        List<Integer> numbers = createNumbers(start, end);

        assert !numbers.contains(another);

        Lottery lottery = new Lottery(numbers);

        assertThat(lottery.contains(another)).isFalse();

        for (Integer number : numbers) {
            assertThat(lottery.contains(number)).isTrue();
        }
    }

    @Test
    @DisplayName("다른 로또와 겹치는 번호 수를 알 수 있다.")
    void testCountMatchingLotteryNumbers() {
        int start = 1;
        int end1 = 5, end2 = 10;

        Lottery lottery1 = new Lottery(createNumbers(start, end1));
        Lottery lottery2 = new Lottery(createNumbers(start, end2));

        assertThat(lottery1.countMatchingLotteryNumbers(lottery2)).isEqualTo(end1);
        assertThat(lottery2.countMatchingLotteryNumbers(lottery1)).isEqualTo(end1);

        assertThat(lottery1.countMatchingLotteryNumbers(lottery1)).isEqualTo(end1);
        assertThat(lottery2.countMatchingLotteryNumbers(lottery2)).isEqualTo(end2);
    }
}
