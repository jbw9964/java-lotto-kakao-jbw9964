package lottery.domain;

import java.util.Arrays;

public enum MatchType {
    NONE(0),
    THREE(3),
    FOUR(4),
    FIVE(5),
    FIVE_WITH_BONUS(5),
    SIX(6);

    private final int requiredMatchCount;

    MatchType(int requiredMatchCount) {
        this.requiredMatchCount = requiredMatchCount;
    }

    public static MatchType matchOf(long matchCount, boolean bonusNumberMatch) {

        if (equalsToFiveWithBonus(matchCount, bonusNumberMatch)) {
            return FIVE_WITH_BONUS;
        }

        return Arrays.stream(values())
                .filter(m -> m.requiredMatchCount == matchCount)
                .findFirst()
                .orElse(NONE);
    }

    private static boolean equalsToFiveWithBonus(long matchCount, boolean bonusNumberMatch) {
        return bonusNumberMatch &&
               matchCount == FIVE_WITH_BONUS.requiredMatchCount;
    }

    public static MatchType[] valuesExcept(MatchType expect) {
        return Arrays.stream(MatchType.values())
                .filter(m -> !m.equals(expect))
                .toArray(MatchType[]::new);
    }
}