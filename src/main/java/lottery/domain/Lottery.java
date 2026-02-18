package lottery.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lottery.domain.LotteryExpression.NumberExpression;

public class Lottery {

    private static final int LOTTERY_NUMBER_LENGTH = 6;

    private final Set<LotteryNumber> lotteryNumbers;

    public Lottery(List<LotteryNumber> lotteryNumbers) {
        Set<LotteryNumber> validLotteryNumbers = validateLengthsAndDuplicates(lotteryNumbers);

        this.lotteryNumbers = Collections.unmodifiableSet(validLotteryNumbers);
    }

    private static Set<LotteryNumber> validateLengthsAndDuplicates(
            List<LotteryNumber> lotteryNumbers) {

        int requiredLength = LOTTERY_NUMBER_LENGTH;

        if (lotteryNumbers.size() != requiredLength) {
            throw new IllegalArgumentException(String.format(
                    "로또 번호 길이는 %d 자리여야 합니다.",
                    requiredLength
            ));
        }

        Set<LotteryNumber> refinedNumbers = new HashSet<>(lotteryNumbers);

        if (refinedNumbers.size() != requiredLength) {
            throw new IllegalArgumentException("중복된 로또 번호가 제공되었습니다.");
        }

        return refinedNumbers;
    }

    public long countMatchingLotteryNumbers(Lottery givenLottery) {
        return this.lotteryNumbers.stream()
                .filter(givenLottery.lotteryNumbers::contains)
                .count();
    }

    public boolean contains(LotteryNumber given) {
        return this.lotteryNumbers.contains(given);
    }

    public int size() {
        return this.lotteryNumbers.size();
    }

    public String representWith(LotteryExpression expression) {

        if (expression == null) {
            throw new IllegalArgumentException("로또 표현식은 Null 일수 없습니다.");
        }

        String leftBracket = expression.leftBracket();
        String rightBracket = expression.rightBracket();
        String numberDeliminator = expression.numberDeliminator();
        NumberExpression numberExpression = expression.numberExpression();

        return this.lotteryNumbers.stream()
                .sorted()
                .map(lotteryNumber -> lotteryNumber.representWith(numberExpression))
                .collect(Collectors.joining(
                        numberDeliminator, leftBracket, rightBracket
                ));
    }

    public static Lottery createRandomLottery() {
        List<LotteryNumber> randomLotteryNumbers = LotteryNumber.getRandomLotteryNumbers(
                LOTTERY_NUMBER_LENGTH
        );

        return new Lottery(randomLotteryNumbers);
    }
}
