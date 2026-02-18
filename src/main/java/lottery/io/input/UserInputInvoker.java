package lottery.io.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import lottery.domain.AnswerLottery;
import lottery.domain.Lottery;
import lottery.domain.LotteryNumber;

public class UserInputInvoker {

    private final UserInputQuery userInputQuery;
    private final InputProvider inputProvider;

    public UserInputInvoker(UserInputQuery userInputQuery, InputProvider inputProvider) {
        this.userInputQuery = userInputQuery;
        this.inputProvider = inputProvider;
    }

    public int getPurchasePriceInput() {
        printQuery(userInputQuery::purchasePriceInputQuery);

        return inputProvider.provideLineAsSingleInt();
    }

    private static void printQuery(Supplier<String> querySupplier) {
        String query = querySupplier.get();
        System.out.println(query);
    }

    public int getNumberOfManualLotteriesInput() {
        printQuery(userInputQuery::numberOfManualLotteryInputQuery);

        return inputProvider.provideLineAsSingleInt();
    }

    public List<Lottery> getManualLotteriesInput(int numberOfLotteries) {

        if (numberOfLotteries <= 0) {
            return Collections.emptyList();
        }

        printQuery(userInputQuery::manualLotteryInputQuery);

        List<Lottery> lotteries = new ArrayList<>(numberOfLotteries);

        for (int i = 0; i < numberOfLotteries; i++) {
            List<LotteryNumber> lotteryNumbers = inputProvider.provideLineAsIntList()
                    .stream()
                    .map(LotteryNumber::new)
                    .toList();

            Lottery lottery = new Lottery(lotteryNumbers);
            lotteries.add(lottery);
        }

        return lotteries;
    }

    public AnswerLottery getAnswerLotteryInput() {
        printQuery(userInputQuery::answerLotteryInputQuery);

        List<LotteryNumber> lotteryNumbers = inputProvider.provideLineAsIntList()
                .stream()
                .map(LotteryNumber::new)
                .toList();

        printQuery(userInputQuery::bonusNumberInputQuery);

        LotteryNumber bonusLotteryNumber = new LotteryNumber(
                inputProvider.provideLineAsSingleInt()
        );

        return new AnswerLottery(lotteryNumbers, bonusLotteryNumber);
    }
}
