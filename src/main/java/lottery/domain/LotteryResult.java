package lottery.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class LotteryResult {

    private final long totalPrize;
    private final MatchTypeCount matchTypeCount;

    public LotteryResult(long totalPrize, Map<MatchType, Long> matchTypeCountMap) {

        if (totalPrize < 0) {
            throw new IllegalArgumentException("총 상금은 0 보다 작을수 없습니다.");
        }

        this.totalPrize = totalPrize;
        this.matchTypeCount = new MatchTypeCount(matchTypeCountMap);
    }

    public long countBy(MatchType matchType) {
        return matchTypeCount.getOrZero(matchType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalPrize(), matchTypeCount);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LotteryResult that)) {
            return false;
        }
        return getTotalPrize() == that.getTotalPrize() && Objects.equals(matchTypeCount,
                that.matchTypeCount);
    }

    public long getTotalPrize() {
        return totalPrize;
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class MatchTypeCount {

        private final Map<MatchType, Long> matchTypeCountMap;

        private MatchTypeCount(Map<MatchType, Long> matchTypeCountMap) {

            if (matchTypeCountMap == null) {
                throw new IllegalArgumentException("일치 횟수 map 은 null 일수 없습니다.");
            }

            this.matchTypeCountMap = Collections.unmodifiableMap(matchTypeCountMap);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(matchTypeCountMap);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MatchTypeCount that)) {
                return false;
            }
            return Objects.equals(matchTypeCountMap, that.matchTypeCountMap);
        }

        private long getOrZero(MatchType matchType) {
            return matchTypeCountMap.getOrDefault(matchType, 0L);
        }
    }
}
