package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import level1.domain.AnswerLottery;
import level1.domain.Lottery;
import level1.exception.InvalidLotteryNumberLengthException;
import level1.exception.InvalidLotteryNumberRangeException;

public class LotteryGenerator {

    /*
    객체지향적 관점에서, 상수들이 사용되는 도메인 객체 내부로 이동시키는 것은 어떨까요?
    예를 들어 Lottery 클래스가 자신의 크기와 범위를 알고 있는 것이 더 응집도가 높습니다.

    --> 관련 상수 `LotteryGenerator` 로 이동
     */
    private static final int
            DEFAULT_LOTTERY_MIN_NUMBER = 1,
            DEFAULT_LOTTERY_MAX_NUMBER = 45,
            DEFAULT_LOTTERY_NUMBER_LENGTH = 6;

    private final int minimumLotteryNumber;
    private final int maximumLotteryNumber;
    private final int lotteryNumberLength;

    private final RandomNumberViewSupplier randomNumberViewSupplier;

    /*
    로또를 생성을 담당하는 class 입니다.

    기존 로또 관련 상수 `(LOTTERY_MIN_VALUE 등)` 를 누가 가지고 있어야 책임이 명확해질까 고민하던 중,
    로또 생성을 담당한 객체가 갖고있어야 되겠다 생각했습니다.

    그래서 기존 `RandomLotteryGenerator` 의 기능 (랜덤 로또 생성) 을 포함한 해당 클래스를 구성하였습니다.
     */
    public LotteryGenerator() {
        this(
                DEFAULT_LOTTERY_MIN_NUMBER, DEFAULT_LOTTERY_MAX_NUMBER,
                DEFAULT_LOTTERY_NUMBER_LENGTH
        );
    }

    public LotteryGenerator(
            int minimumLotteryNumber, int maximumLotteryNumber, int lotteryNumberLength
    ) {
        this.minimumLotteryNumber = minimumLotteryNumber;
        this.maximumLotteryNumber = maximumLotteryNumber;
        this.lotteryNumberLength = lotteryNumberLength;

        if (maximumLotteryNumber < minimumLotteryNumber) {
            throw new IllegalStateException("잘못된 로또 번호 범위입니다.");
        }

        this.randomNumberViewSupplier = new RandomNumberViewSupplier(
                minimumLotteryNumber, maximumLotteryNumber
        );
    }

    public AnswerLottery generateAnswerLottery(
            List<Integer> lotteryNumbers, int bonusLotteryNumber
    ) {
        if (lotteryNumbers.size() != this.lotteryNumberLength) {
            throw new InvalidLotteryNumberLengthException(String.format(
                    "로또 번호 길이는 %d 여야 합니다.",
                    this.lotteryNumberLength
            ));
        }

        int minNumber = this.minimumLotteryNumber;
        int maxNumber = this.maximumLotteryNumber;

        boolean doesAnyNumberOutOfRange = lotteryNumbers.stream()
                .anyMatch(n -> n < minNumber || n > maxNumber);

        doesAnyNumberOutOfRange |= bonusLotteryNumber < minNumber || bonusLotteryNumber > maxNumber;

        if (doesAnyNumberOutOfRange) {
            throw new InvalidLotteryNumberRangeException(String.format(
                    "로또 번호는 [%d - %d] 범위의 숫자만 가능합니다.",
                    minNumber, maxNumber
            ));
        }

        return new AnswerLottery(lotteryNumbers, bonusLotteryNumber);
    }

    public List<Lottery> generateRandomLotteries(int numberOfLotteries) {
        List<Lottery> randomLotteries = new ArrayList<>(numberOfLotteries);

        for (int i = 0; i < numberOfLotteries; i++) {
            List<Integer> randomNumbers = randomNumberViewSupplier.supplyRandomView(
                    lotteryNumberLength
            );
            randomLotteries.add(new Lottery(randomNumbers));
        }

        return randomLotteries;
    }

    private static class RandomNumberViewSupplier {

        private final List<Integer> numbers;

        /*
        "랜덤한 n 개의 숫자를 제공" 을 담당하는 class 입니다.

        List#subList 메서드를 이용해 shuffle 된 list 의 view 를 제공합니다.
         */
        private RandomNumberViewSupplier(
                int lotteryMinNumber, int lotteryMaxNumber
        ) {
            this.numbers = IntStream.rangeClosed(lotteryMinNumber, lotteryMaxNumber)
                    .boxed()
                    .collect(Collectors.toList());
        }

        private List<Integer> supplyRandomView(int viewLength) {

            if (this.numbers.size() < viewLength) {
                throw new IllegalArgumentException(String.format(
                        "숫자 범위가 충분하지 않아 %d 개의 랜덤 숫자를 제공할 수 없습니다.",
                        viewLength
                ));
            }

            Collections.shuffle(this.numbers);

            return this.numbers.subList(0, viewLength);
        }
    }
}
