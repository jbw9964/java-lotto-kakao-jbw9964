package lottery.component;

import java.util.ArrayList;
import java.util.List;
import lottery.domain.Lottery;

public class RandomLotteryGenerator {

    private static final RandomLotteryGenerator instance = new RandomLotteryGenerator();

    private RandomLotteryGenerator() {
    }

    public List<Lottery> generateRandomLotteries(int numberOfLotteries) {

        if (numberOfLotteries < 0) {
            throw new IllegalArgumentException("로또 개수는 0 보다 작을수 없습니다.");
        }

        List<Lottery> randomLotteries = new ArrayList<>(numberOfLotteries);

        for (int i = 0; i < numberOfLotteries; i++) {
            Lottery randomLottery = Lottery.createRandomLottery();
            randomLotteries.add(randomLottery);
        }

        return randomLotteries;
    }

    public static RandomLotteryGenerator getInstance() {
        return instance;
    }
}
