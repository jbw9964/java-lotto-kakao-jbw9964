package lottery.component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lottery.domain.AnswerLottery;
import lottery.domain.Lottery;
import lottery.domain.LotteryResult;
import lottery.domain.MatchType;

public class LotteryResultProvider {

    private final MatchTypeResolver matchTypeResolver;
    private final MatchPrizeResolver matchPrizeResolver;

    public LotteryResultProvider(
            MatchTypeResolver matchTypeResolver,
            MatchPrizeResolver matchPrizeResolver
    ) {
        this.matchTypeResolver = matchTypeResolver;
        this.matchPrizeResolver = matchPrizeResolver;
    }

    public LotteryResult getResultFrom(AnswerLottery answerLottery, List<Lottery> lotteries) {
        validate(answerLottery);
        validate(lotteries);

        List<MatchType> matchTypes = lotteries.stream()
                .map(lottery -> matchTypeResolver.resolveMatchTypeWith(answerLottery, lottery))
                .toList();

        long totalPrize = matchTypes.stream()
                .mapToLong(matchPrizeResolver::resolvePrizeWith)
                .sum();

        Map<MatchType, Long> matchTypeCountMap = matchTypes.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return new LotteryResult(totalPrize, matchTypeCountMap);
    }

    private static void validate(Lottery lottery) {
        if (lottery == null) {
            throw new IllegalArgumentException("Null 인 로또는 계산할 수 없습니다.");
        }
    }

    private static void validate(List<Lottery> lotteries) {
        if (lotteries == null) {
            throw new IllegalArgumentException("로또 목록은 null 일수 없습니다.");
        }

        if (lotteries.isEmpty()) {
            throw new IllegalArgumentException("빈 로또 목록이 제공되었습니다.");
        }

        lotteries.forEach(LotteryResultProvider::validate);
    }
}
