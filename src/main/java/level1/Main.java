package level1;

import java.util.List;
import level1.domain.AnswerLottery;
import level1.domain.Lottery;
import level1.domain.Match;
import level1.exception.InsufficientPurchasePriceException;

public class Main {

    private static final int LOTTERY_PRICE = 1_000;

    private static final InputHandler inputHandler;
    private static final LotteryGenerator lotteryGenerator;

    static {
        inputHandler = new InputHandler();
        lotteryGenerator = new LotteryGenerator();
    }

    public static void main(String[] args) {
        int purchasePrice = receivePurchasePrice();

        List<Lottery> givenLotteries = purchaseLotteries(purchasePrice);

        AnswerLottery answerLottery = receiveAnswerLottery();

        Report report = new Report(answerLottery, givenLotteries);

        printLotteryReport(purchasePrice, report);
    }

    private static int receivePurchasePrice() {
        System.out.println("\n구입금액을 입력해 주세요.");

        int purchasePrice = inputHandler.inputPrice();

        if (purchasePrice < LOTTERY_PRICE) {
            throw new InsufficientPurchasePriceException(String.format(
                    "구입 금액은 %d 보다 크거나 같아야 합니다.",
                    LOTTERY_PRICE
            ));
        }

        return purchasePrice;
    }

    private static List<Lottery> purchaseLotteries(int purchasePrice) {
        int numberOfLotteries = purchasePrice / LOTTERY_PRICE;

        System.out.printf("\n%d 개를 구매했습니다.\n", numberOfLotteries);

        List<Lottery> randomLotteries = lotteryGenerator.generateRandomLotteries(numberOfLotteries);

        randomLotteries.stream()
                .map(Lottery::represent)
                .forEach(System.out::println);

        return randomLotteries;
    }

    private static AnswerLottery receiveAnswerLottery() {
        System.out.println("\n지난 주 당첨 번호를 입력해 주세요.");

        List<Integer> lastAnswerLotteryNumbers = inputHandler.inputLastAnswerLottery();

        System.out.println("보너스 볼을 입력해 주세요.");

        int bonusLotteryNumber = inputHandler.inputBonusLotteryNumber();

        return lotteryGenerator.generateAnswerLottery(lastAnswerLotteryNumbers, bonusLotteryNumber);
    }

    private static void printLotteryReport(int purchasePrice, Report report) {
        Match[] matchesInConcern = Match.valuesExcept(Match.NONE);

        System.out.println("\n당첨 통계\n---------");

        for (Match match : matchesInConcern) {
            long matchCount = report.getMatchCount(match);
            System.out.printf(
                    "%s - %d 개\n",
                    match.getDescription(), matchCount
            );
        }

        long totalPrize = report.getTotalPrize();
        double profitRate = (double) totalPrize / purchasePrice;

        System.out.printf(
                "총 수익률은 %.2f 입니다.\n",
                profitRate
        );
    }
}