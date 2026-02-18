package lottery.component;

import lottery.domain.MatchType;

public class MatchPrizeResolver {

    private static final MatchPrizeResolver instance = new MatchPrizeResolver();

    private MatchPrizeResolver() {
    }

    public long resolvePrizeWith(MatchType matchType) {

        if (matchType == null) {
            throw new IllegalArgumentException("일치 타입은 null 일수 없습니다.");
        }

        return switch (matchType) {
            case NONE -> 0L;
            case THREE -> 5_000L;
            case FOUR -> 50_000L;
            case FIVE -> 1_500_000;
            case FIVE_WITH_BONUS -> 30_000_000;
            case SIX -> 2_000_000_000;
        };
    }

    public static MatchPrizeResolver getInstance() {
        return instance;
    }
}
