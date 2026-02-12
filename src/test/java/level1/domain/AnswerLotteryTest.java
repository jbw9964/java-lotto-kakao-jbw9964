package level1.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import level1.exception.DuplicateLotteryNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnswerLotteryTest {

    private static final List<Integer> lotteryNumbers = List.of(1, 2, 3);

    @Test
    @DisplayName("보너스 볼은 기존 로또 번호와 중복되지 않는다.")
    void testDuplicateBonusLottery() {
        for (Integer bonusLotteryNumber : lotteryNumbers) {
            assertThatThrownBy(() -> new AnswerLottery(lotteryNumbers, bonusLotteryNumber))
                    .isInstanceOf(DuplicateLotteryNumberException.class);
        }
    }

    @Test
    @DisplayName("보너스 번호가 포함되는지 확인할 수 있다.")
    void testContainsBonusNumber() {
        int bonusNumber1 = 10, bonusNumber2 = 20;

        //noinspection ConstantValue
        assert bonusNumber1 != bonusNumber2;
        assert !lotteryNumbers.contains(bonusNumber1);
        assert !lotteryNumbers.contains(bonusNumber2);

        AnswerLottery answerLottery1 = new AnswerLottery(lotteryNumbers, bonusNumber1);
        AnswerLottery answerLottery2 = new AnswerLottery(lotteryNumbers, bonusNumber2);

        Lottery lottery1 = new Lottery(List.of(bonusNumber1));
        Lottery lottery2 = new Lottery(List.of(bonusNumber2));

        assertThat(answerLottery1.containsBonusNumber(lottery1)).isTrue();
        assertThat(answerLottery1.containsBonusNumber(lottery2)).isFalse();

        assertThat(answerLottery2.containsBonusNumber(lottery1)).isFalse();
        assertThat(answerLottery2.containsBonusNumber(lottery2)).isTrue();

    }
}
