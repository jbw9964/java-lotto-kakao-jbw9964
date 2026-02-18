package lottery.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lottery.domain.AnswerLottery;
import lottery.domain.Lottery;
import lottery.domain.LotteryNumber;
import lottery.domain.MatchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MatchTypeResolverTest {

    private static final List<LotteryNumber> answerLotteryNumbers = IntStream.rangeClosed(1, 6)
            .mapToObj(LotteryNumber::new)
            .toList();
    private static final List<LotteryNumber> tenToTwenty = IntStream.rangeClosed(10, 20)
            .mapToObj(LotteryNumber::new)
            .toList();
    private static final LotteryNumber bonusLotteryNumber = new LotteryNumber(7);

    private static final AnswerLottery answerLottery = new AnswerLottery(
            answerLotteryNumbers, bonusLotteryNumber
    );

    private static final MatchTypeResolver matchTypeResolver = MatchTypeResolver.getInstance();

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("정답 로또와 주어진 로또를 비교해 일치 타입을 식별할 수 있다.")
    void testResolveMatchTypeWith(MatchType expectedMatchType) {

        Lottery lottery = createLotteryWith(expectedMatchType);

        assertThat(matchTypeResolver.resolveMatchTypeWith(answerLottery, lottery))
                .isEqualTo(expectedMatchType);
    }

    private static Lottery createLotteryWith(MatchType matchType) {
        if (matchType.equals(MatchType.FIVE_WITH_BONUS)) {

            List<LotteryNumber> lotteryNumbers = Stream.concat(
                    answerLotteryNumbers.subList(0, 5).stream(),
                    Stream.of(bonusLotteryNumber)
            ).toList();

            return new Lottery(lotteryNumbers);
        }

        int length = switch (matchType) {
            case NONE -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            default -> throw new AssertionError();
        };
        int remains = answerLotteryNumbers.size() - length;

        List<LotteryNumber> lotteryNumbers = Stream.concat(
                answerLotteryNumbers.subList(0, length).stream(),
                tenToTwenty.subList(0, remains).stream()
        ).toList();

        return new Lottery(lotteryNumbers);
    }

    private static Stream<Arguments> allMatches() {
        return Arrays.stream(MatchType.values())
                .map(Arguments::of);
    }
}