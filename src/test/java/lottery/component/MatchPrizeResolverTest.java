package lottery.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Arrays;
import java.util.stream.Stream;
import lottery.domain.MatchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MatchPrizeResolverTest {

    private static final MatchPrizeResolver matchPrizeResolver = MatchPrizeResolver.getInstance();

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("타입별 상금액이 구비되어 있다.")
    void testMatchPrizeResolvablePerMatchTypes(MatchType matchType) {
        assertThatCode(() -> matchPrizeResolver.resolvePrizeWith(matchType))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("타입별 상금액은 0 보다 크거나 같다.")
    void testMatchPrizeNonNegative(MatchType matchType) {
        assertThat(matchPrizeResolver.resolvePrizeWith(matchType))
                .isGreaterThanOrEqualTo(0);
    }

    private static Stream<Arguments> allMatches() {
        return Arrays.stream(MatchType.values())
                .map(Arguments::of);
    }
}