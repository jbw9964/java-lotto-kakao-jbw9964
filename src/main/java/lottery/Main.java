package lottery;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import lottery.component.LotteryResultProvider;
import lottery.component.LotterySeller;
import lottery.component.MatchPrizeResolver;
import lottery.component.MatchTypeResolver;
import lottery.component.RandomLotteryGenerator;
import lottery.domain.AnswerLottery;
import lottery.domain.Lottery;
import lottery.domain.LotteryExpression;
import lottery.domain.LotteryResult;
import lottery.domain.MatchType;
import lottery.domain.Quantity;
import lottery.io.input.InputProvider;
import lottery.io.input.UserInputInvoker;
import lottery.io.input.UserInputQuery;
import lottery.io.output.LotteryDescriber;
import lottery.io.output.LotteryResultDescriber;
import lottery.io.output.MatchDescriber;

public class Main {

    private static final UserInputInvoker userInputInvoker;
    private static final LotteryDescriber lotteryDescriber;
    private static final LotteryResultDescriber lotteryResultDescriber;

    private static final LotterySeller lotterySeller;
    private static final RandomLotteryGenerator randomLotteryGenerator;
    private static final LotteryResultProvider lotteryResultProvider;


    static {
        userInputInvoker = new UserInputInvoker(
                UserInputQuery.getInstance(),
                new InputProvider(new Scanner(
                        System.in
                ))
        );
        lotteryDescriber = new LotteryDescriber(
                LotteryExpression.defaultExpression()
        );
        lotteryResultDescriber = new LotteryResultDescriber(
                MatchType.valuesExcept(MatchType.NONE),
                MatchDescriber.getInstance()
        );

        lotterySeller = LotterySeller.getInstance();
        randomLotteryGenerator = RandomLotteryGenerator.getInstance();
        lotteryResultProvider = new LotteryResultProvider(
                MatchTypeResolver.getInstance(),
                MatchPrizeResolver.getInstance()
        );
    }

    public static void main(String[] args) {

        int purchasePrice = userInputInvoker.getPurchasePriceInput();

        Quantity totalLotteryQuantity = lotterySeller.issueLotteryQuantity(purchasePrice);

        List<Lottery> purchasedLotteries = getAllLotteriesAndPrintAllWith(totalLotteryQuantity);

        AnswerLottery answerLottery = userInputInvoker.getAnswerLotteryInput();

        LotteryResult lotteryResult = lotteryResultProvider.getResultFrom(
                answerLottery, purchasedLotteries
        );

        String report = lotteryResultDescriber.describe(purchasePrice, lotteryResult);

        System.out.println(report);
    }

    private static List<Lottery> getAllLotteriesAndPrintAllWith(Quantity totalLotteryQuantity) {
        int numberOfManualLotteries = userInputInvoker.getNumberOfManualLotteriesInput();

        Quantity randomLotteryQuantity = totalLotteryQuantity.reduceQuantity(
                numberOfManualLotteries
        );

        Quantity manualLotteryQuantity = totalLotteryQuantity.reduceQuantity(
                randomLotteryQuantity
        );

        List<Lottery> manualLotteries = getManualLotteries(manualLotteryQuantity);

        List<Lottery> randomLotteries = getRandomLotteries(randomLotteryQuantity);

        return concatLotteriesAndPrintAll(manualLotteries, randomLotteries);
    }

    private static List<Lottery> getManualLotteries(
            Quantity manualLotteryQuantity
    ) {
        int numberOfManualLotteries = manualLotteryQuantity.amount();

        return userInputInvoker.getManualLotteriesInput(numberOfManualLotteries);
    }

    private static List<Lottery> getRandomLotteries(Quantity randomLotteryQuantity) {
        int numberOfRandomLotteries = randomLotteryQuantity.amount();

        return randomLotteryGenerator.generateRandomLotteries(numberOfRandomLotteries);
    }

    private static List<Lottery> concatLotteriesAndPrintAll(
            List<Lottery> manualLotteries, List<Lottery> randomLotteries
    ) {
        System.out.printf(
                "\n수동으로 %d장, 자동으로 %d개를 구매했습니다.\n",
                manualLotteries.size(), randomLotteries.size()
        );

        List<Lottery> allLotteries = Stream.concat(
                manualLotteries.stream(), randomLotteries.stream()
        ).toList();

        String lotteryRepresentation = lotteryDescriber.describe(allLotteries);

        System.out.println(lotteryRepresentation);

        return allLotteries;
    }
}
