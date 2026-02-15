package lottery.io.output;

import java.util.List;
import java.util.stream.Collectors;
import lottery.domain.Lottery;
import lottery.domain.LotteryExpression;

public class LotteryDescriber {

    private static final String LOTTERY_DELIMINATOR = "\n";

    private final LotteryExpression lotteryExpression;

    public LotteryDescriber(LotteryExpression lotteryExpression) {
        this.lotteryExpression = lotteryExpression;
    }

    public String describe(List<Lottery> lotteries) {

        if (lotteries == null) {
            throw new IllegalArgumentException("Null 인 로또 목록은 표현할 수 없습니다.");
        }

        return lotteries.stream()
                .map(this::describe)
                .collect(Collectors.joining(LOTTERY_DELIMINATOR));
    }

    public String describe(Lottery lottery) {

        if (lottery == null) {
            throw new IllegalArgumentException("Null 인 로또는 설명할 수 없습니다.");
        }

        return lottery.representWith(this.lotteryExpression);
    }
}
