package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotteryResultTest {

    private static final long totalPrize = 1_000L;
    private static final Map<MatchType, Long> matchCountMap = Map.of(
            MatchType.FIVE, 5L,
            MatchType.SIX, 6L
    );

    @Test
    @DisplayName("일치 타입별 로또 결과의 일치 횟수를 확인할 수 있다.")
    void testCountBy() {
        LotteryResult lotteryResult = new LotteryResult(totalPrize, matchCountMap);

        for (Map.Entry<MatchType, Long> entry : matchCountMap.entrySet()) {
            MatchType matchType = entry.getKey();
            long count = entry.getValue();

            assertThat(lotteryResult.countBy(matchType)).isEqualTo(count);
        }
    }

    @Test
    @DisplayName("로또 결과의 총 상금액을 확인할 수 있다.")
    void testGetTotalPrize() {
        LotteryResult lotteryResult = new LotteryResult(totalPrize, matchCountMap);

        assertThat(lotteryResult.getTotalPrize()).isEqualTo(totalPrize);
    }

    @Test
    @DisplayName("로또 결과의 총 상금액은 0 보다 작을 수 없다.")
    void testNegativeTotalPrize() {
        assertThatThrownBy(() -> new LotteryResult(-1L, matchCountMap))
                .isInstanceOf(IllegalArgumentException.class);
    }
}