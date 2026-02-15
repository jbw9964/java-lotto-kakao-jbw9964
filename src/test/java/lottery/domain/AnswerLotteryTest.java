package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnswerLotteryTest {

    private static final List<LotteryNumber> answerLotteryNumbers = IntStream.rangeClosed(1, 6)
            .mapToObj(LotteryNumber::new)
            .toList();

    @Test
    @DisplayName("보너스 볼은 기존 로또 번호와 중복되지 않는다.")
    void testDuplicateBonusLottery() {
        for (LotteryNumber bonusLotteryNumber : answerLotteryNumbers) {
            assertThatThrownBy(() -> new AnswerLottery(answerLotteryNumbers, bonusLotteryNumber))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    @DisplayName("보너스 번호가 포함되는지 확인할 수 있다.")
    void testContainsBonusNumber() {
        int bonusNumber = 7;
        LotteryNumber bonusLotteryNumber = new LotteryNumber(bonusNumber);

        AnswerLottery answerLottery = new AnswerLottery(answerLotteryNumbers, bonusLotteryNumber);

        List<LotteryNumber> bonusNumberExcluded = answerLotteryNumbers;
        List<LotteryNumber> bonusNumberIncluded = Stream.of(1, 2, 3, 4, 5, bonusNumber)
                .map(LotteryNumber::new)
                .toList();

        assertThat(bonusNumberExcluded).doesNotContain(bonusLotteryNumber);

        Lottery bonusIncludedLottery = new Lottery(bonusNumberIncluded);
        Lottery bonusExcludedLottery = new Lottery(bonusNumberExcluded);

        assertThat(answerLottery.containsBonusNumber(bonusIncludedLottery)).isTrue();
        assertThat(answerLottery.containsBonusNumber(bonusExcludedLottery)).isFalse();
    }
}
