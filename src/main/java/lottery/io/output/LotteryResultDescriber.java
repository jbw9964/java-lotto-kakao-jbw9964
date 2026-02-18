package lottery.io.output;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lottery.domain.LotteryResult;
import lottery.domain.MatchType;

public class LotteryResultDescriber {

    private final MatchTypesToIncludeResult matchesToIncludeResult;
    private final MatchDescriber matchDescriber;

    public LotteryResultDescriber(
            MatchType[] matchesToIncludeResult,
            MatchDescriber matchDescriber
    ) {
        this.matchesToIncludeResult = new MatchTypesToIncludeResult(matchesToIncludeResult);
        this.matchDescriber = matchDescriber;
    }

    public String describe(int purchasedPrice, LotteryResult lotteryResult) {

        if (purchasedPrice < 0) {
            throw new IllegalArgumentException("구입 금액은 0 보다 작을 수 없습니다.");
        }

        if (lotteryResult == null) {
            throw new IllegalArgumentException("로또 결과는 Null 일 수 없습니다.");
        }

        StringBuilder sb = getResultHeader();

        this.buildMatchResult(lotteryResult, sb);

        buildProfitResult(purchasedPrice, lotteryResult, sb);

        return sb.toString();
    }

    private static StringBuilder getResultHeader() {
        return new StringBuilder().append("\n")
                .append("당첨 통계").append("\n")
                .append("---------").append("\n");
    }

    private void buildMatchResult(LotteryResult lotteryResult, StringBuilder dst) {
        for (MatchType matchType : this.matchesToIncludeResult.getMatchTypes()) {
            String representation = matchDescriber.describe(matchType);

            long count = lotteryResult.countBy(matchType);

            dst.append(representation)
                    .append(" - ")
                    .append(count)
                    .append("개")
                    .append("\n");
        }
    }

    private static void buildProfitResult(
            int purchasedPrice, LotteryResult result, StringBuilder dst
    ) {
        double profitRate = getProfitRateWith(purchasedPrice, result);

        dst
                .append("총 수익률은").append(" ")
                .append(String.format("%.2f", profitRate))
                .append(" ").append("입니다.")
                .append("\n");
    }

    private static double getProfitRateWith(int purchasedPrice, LotteryResult result) {
        long totalPrize = result.getTotalPrize();
        return (double) totalPrize / purchasedPrice;
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class MatchTypesToIncludeResult {

        private final List<MatchType> matchTypes;

        private MatchTypesToIncludeResult(MatchType[] matchTypes) {
            this(
                    Arrays.asList(matchTypes)
            );
        }

        private MatchTypesToIncludeResult(List<MatchType> matchTypes) {
            this.matchTypes = Collections.unmodifiableList(matchTypes);
        }

        private List<MatchType> getMatchTypes() {
            return this.matchTypes;
        }
    }
}
