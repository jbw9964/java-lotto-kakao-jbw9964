package lottery.io.output;

import lottery.domain.MatchType;

public class MatchDescriber {

    private static final MatchDescriber instance = new MatchDescriber();

    private MatchDescriber() {
    }

    public static MatchDescriber getInstance() {
        return instance;
    }

    public String describe(MatchType matchType) {

        if (matchType == null) {
            throw new IllegalArgumentException("Null 인 일치 타입은 설명할 수 없습니다.");
        }

        return switch (matchType) {
            case NONE -> "2 개 이하 일치 (0원)";
            case THREE -> "3 개 일치 (5,000원)";
            case FOUR -> "4 개 일치 (50,000원)";
            case FIVE -> "5 개 일치 (1,500,000원)";
            case FIVE_WITH_BONUS -> "5 개 일치, 보너스 볼 일치 (30,000,000원)";
            case SIX -> "6 개 일치 (2,000,000,000원)";
        };
    }
}
