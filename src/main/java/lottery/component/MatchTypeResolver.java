package lottery.component;

import lottery.domain.AnswerLottery;
import lottery.domain.Lottery;
import lottery.domain.MatchType;

public class MatchTypeResolver {

    private static final MatchTypeResolver instance = new MatchTypeResolver();

    private MatchTypeResolver() {
    }

    public MatchType resolveMatchTypeWith(AnswerLottery answerLottery, Lottery givenLottery) {

        long matchCount = answerLottery.countMatchingLotteryNumbers(givenLottery);

        boolean containsBonusNumber = answerLottery.containsBonusNumber(givenLottery);

        return MatchType.matchOf(matchCount, containsBonusNumber);
    }

    public static MatchTypeResolver getInstance() {
        return instance;
    }
}
