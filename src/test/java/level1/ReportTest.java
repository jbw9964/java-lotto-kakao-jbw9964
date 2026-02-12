package level1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import level1.domain.AnswerLottery;
import level1.domain.Lottery;
import level1.domain.Match;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReportTest {

    private static final int bonusNumber = 7;
    private static final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

    private static final AnswerLottery answerLottery = new AnswerLottery(numbers, bonusNumber);

    private static Stream<Arguments> testGetMatchCountArgs() {
        return Arrays.stream(Match.values())
                .map(Arguments::of);
    }

    private static Lottery createLottery(Match match) {

        if (match.equals(Match.FIVE_WITH_BONUS)) {
            List<Integer> givenNumbers = new ArrayList<>(numbers.subList(0, 5));
            givenNumbers.add(bonusNumber);
            return new Lottery(givenNumbers);
        }

        List<Integer> givenNumbers = new ArrayList<>(numbers);

        int length = switch (match) {
            case NONE -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            default -> throw new AssertionError();
        };

        return new Lottery(givenNumbers.subList(0, length));
    }

    @Test
    @DisplayName("로또 리포트로부터 당첨 합을 구할 수 있다.")
    void testGetTotalPrize() {

        List<Lottery> lotteries = Arrays.stream(Match.values())
                .map(ReportTest::createLottery)
                .toList();

        Report report = new Report(answerLottery, lotteries);

        long expectedTotalPrize = Arrays.stream(Match.values())
                .mapToLong(Match::getPrize)
                .sum();

        assertThat(report.getTotalPrize()).isEqualTo(expectedTotalPrize);
    }

    @ParameterizedTest
    @MethodSource("testGetMatchCountArgs")
    @DisplayName("로또 리포트로부터 맞은 개수를 확인할 수 있다.")
    void testGetMatchCount(Match match) {

        List<Lottery> lotteries = List.of(createLottery(match));
        Report report = new Report(answerLottery, lotteries);

        assertThat(report.getMatchCount(match)).isOne();

        for (Match other : Match.valuesExcept(match)) {
            assertThat(report.getMatchCount(other)).isZero();
        }
    }
}