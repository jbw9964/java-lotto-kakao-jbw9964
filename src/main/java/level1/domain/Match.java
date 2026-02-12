package level1.domain;

import java.util.Arrays;

public enum Match {
    NONE(
            0, 0, ""
    ),
    THREE(
            3, 5_000, "3개 일치 (5000원)"
    ),
    FOUR(
            4, 50_000, "4개 일치 (50000원)"
    ),
    FIVE(
            5, 1_500_000, "5개 일치 (1500000원)"
    ),
    FIVE_WITH_BONUS(
            5, 30_000_000, "5개 일치, 보너스 볼 일치(30000000원)"
    ),
    SIX(
            6, 2_000_000_000, "6개 일치 (2000000000원)"
    );

    private final int matchCount;
    private final int prize;
    private final String description;

    Match(int matchCount, int prize, String description) {
        this.matchCount = matchCount;
        this.prize = prize;
        this.description = description;
    }

    public static Match matchOf(long matchCount, boolean bonusNumberMatch) {

        if (matchCount == FIVE.matchCount) {
            return !bonusNumberMatch ? FIVE : FIVE_WITH_BONUS;
        }

        return Arrays.stream(values())
                .filter(m -> m.matchCount == matchCount)
                .findFirst()
                .orElse(NONE);
    }

    public static Match[] valuesExcept(Match expect) {
        return Arrays.stream(Match.values())
                .filter(m -> !m.equals(expect))
                .toArray(Match[]::new);
    }

    public int getPrize() {
        return prize;
    }

    public String getDescription() {
        return description;
    }

    public long calculatePrizeSum(long matchCount) {
        return matchCount * this.prize;
    }
}