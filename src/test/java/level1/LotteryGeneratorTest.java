package level1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import level1.domain.AnswerLottery;
import level1.domain.Lottery;
import level1.exception.InvalidLotteryNumberLengthException;
import level1.exception.InvalidLotteryNumberRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryGeneratorTest {

    private static final int
            MIN_NUMBER = 1,
            MAX_NUMBER = 10,
            NUMBER_LENGTH = 5;

    private static final LotteryGenerator generator = new LotteryGenerator(
            MIN_NUMBER, MAX_NUMBER, NUMBER_LENGTH
    );

    @Test
    @DisplayName("정답 로또를 올바르게 생성할 수 있다.")
    void testGenerateAnswerLottery() {

        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int bonusNumber = 6;

        //noinspection ConstantValue
        assert numbers.size() == NUMBER_LENGTH;
        assert !numbers.contains(bonusNumber);

        AnswerLottery answerLottery = generator.generateAnswerLottery(numbers, bonusNumber);

        assertThat(answerLottery.getLotteryNumberLength())
                .isEqualTo(NUMBER_LENGTH);

        Lottery lottery1 = new Lottery(numbers);
        Lottery lottery2 = new Lottery(Collections.emptyList());
        Lottery lottery3 = new Lottery(List.of(bonusNumber));

        assert lottery1.getLotteryNumberLength() == NUMBER_LENGTH;

        assertThat(answerLottery.countMatchingLotteryNumbers(lottery1)).isEqualTo(NUMBER_LENGTH);
        assertThat(answerLottery.containsBonusNumber(lottery1)).isFalse();

        assertThat(answerLottery.countMatchingLotteryNumbers(lottery2)).isZero();
        assertThat(answerLottery.containsBonusNumber(lottery2)).isFalse();

        assertThat(answerLottery.countMatchingLotteryNumbers(lottery3)).isZero();
        assertThat(answerLottery.containsBonusNumber(lottery3)).isTrue();
    }

    @Test
    @DisplayName("허용 범위를 벗어난 숫자로 정답 로또 생성은 불가능하다.")
    void testInvalidLotteryNumberRangeException() {
        int validNumber = 5;
        int invalidNumber = 5000;

        assertThatThrownBy(() -> generator.generateAnswerLottery(
                List.of(1, 2, 3, 4, invalidNumber),
                validNumber
        ))
                .isInstanceOf(InvalidLotteryNumberRangeException.class);
        assertThatThrownBy(() -> generator.generateAnswerLottery(
                List.of(1, 2, 3, 4, 5),
                invalidNumber
        ))
                .isInstanceOf(InvalidLotteryNumberRangeException.class);
    }

    @Test
    @DisplayName("제공한 로또 번호 숫자가 충분치 않으면 정답 로또 생성은 불가능하다.")
    void testInvalidLotteryNumberLengthException() {
        int bonusNumber = 10;
        List<Integer> numbers1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> numbers2 = List.of(1, 2, 3, 4);

        assertThatThrownBy(() -> generator.generateAnswerLottery(numbers1, bonusNumber))
                .isInstanceOf(InvalidLotteryNumberLengthException.class);
        assertThatThrownBy(() -> generator.generateAnswerLottery(numbers2, bonusNumber))
                .isInstanceOf(InvalidLotteryNumberLengthException.class);
    }

    @Test
    @DisplayName("생성된 랜덤 로또 번호는 허용범위를 만족한다.")
    void testGenerateRandomLotteries() {
        int minNumber = 1, maxNumber = 5;
        int numberLength = 5;

        LotteryGenerator generator = new LotteryGenerator(minNumber, maxNumber, numberLength);

        List<Lottery> randomLotteries = generator.generateRandomLotteries(1);

        assertThat(randomLotteries).hasSize(1);

        Lottery randomLottery = randomLotteries.getFirst();
        assertThat(randomLottery.getLotteryNumberLength()).isEqualTo(numberLength);

        for (int number = minNumber; number <= maxNumber; number++) {
            assertThat(randomLottery.contains(number)).isTrue();
        }
    }
}