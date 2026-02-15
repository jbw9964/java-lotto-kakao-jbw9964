package lottery.io.output;

import lottery.domain.LotteryResult;
import lottery.domain.MatchType;

public class LotteryResultDescriber {

    private final MatchType[] matchesToIncludeResult;
    private final MatchDescriber matchDescriber;

    public LotteryResultDescriber(MatchType[] matchesToIncludeResult,
            MatchDescriber matchDescriber) {
        this.matchesToIncludeResult = matchesToIncludeResult;
        this.matchDescriber = matchDescriber;
    }

    public String describe(int purchasedPrice, LotteryResult lotteryResult) {
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
        for (MatchType matchType : this.matchesToIncludeResult) {
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
}
