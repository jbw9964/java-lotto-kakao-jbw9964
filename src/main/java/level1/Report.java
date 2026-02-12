package level1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import level1.domain.AnswerLottery;
import level1.domain.Lottery;
import level1.domain.Match;

public class Report {

    private final Map<Match, Long> matchCountMap;

    /*
    정답 로또와 다수의 로또를 비교한 결과를 지칭하는 class 입니다.

    기존 `representReport` 와 같은 메서드는 책임을 분리하는 것이 좋겠다 생각해 제거하였습니다.

    - 삭제된 메서드
        - #representReport(), #appendMatchStatistics() :
            출력과 관련된 부분이므로 제거하였습니다.
     */
    public Report(AnswerLottery answerLottery, List<Lottery> lotteries) {
        this.matchCountMap = lotteries.stream().collect(Collectors.groupingBy(
                lottery -> this.inspectMatch(answerLottery, lottery),
                Collectors.counting()
        ));
    }

    public long getTotalPrize() {
        long prizeSum = 0L;

        for (Map.Entry<Match, Long> entry : matchCountMap.entrySet()) {
            Match match = entry.getKey();
            long count = entry.getValue();
            prizeSum += match.calculatePrizeSum(count);
        }

        return prizeSum;
    }

    public long getMatchCount(Match match) {
        return matchCountMap.getOrDefault(match, 0L);
    }

    private Match inspectMatch(AnswerLottery answerLottery, Lottery lottery) {
        long matchCount = answerLottery.countMatchingLotteryNumbers(lottery);
        boolean containsBonusNumber = answerLottery.containsBonusNumber(lottery);

        return Match.matchOf(matchCount, containsBonusNumber);
    }
}
