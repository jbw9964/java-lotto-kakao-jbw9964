package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MatchTypeTest {

    @ParameterizedTest
    @MethodSource("matchOfArgs")
    @DisplayName("숫자 일치 개수, 보너스 일치 여부를 통해 일치 타입을 식별할 수 있다.")
    void testMatchOf(MatchOfMethodArgs args) {
        long matchCount = args.matchCount();
        boolean bonusNumberMatch = args.bonusNumberMatch();
        MatchType expectedMatchType = args.expectedMatchType();

        assertThat(MatchType.matchOf(matchCount, bonusNumberMatch))
                .isEqualTo(expectedMatchType);
    }

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("특정 일치 타입을 제외한 나머지 일치 타입들을 제공받을 수 있다.")
    void testValuesExcept(MatchType except) {
        List<MatchType> others = Arrays.stream(MatchType.values())
                .filter(m -> !m.equals(except))
                .toList();

        assertThat(MatchType.valuesExcept(except))
                .containsExactlyInAnyOrderElementsOf(others);
    }

    private static Stream<Arguments> matchOfArgs() {
        return Stream.of(
                Arguments.of(MatchOfMethodArgs.of(0, false, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(0, true, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(1, false, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(1, true, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(2, false, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(2, true, MatchType.NONE)),
                Arguments.of(MatchOfMethodArgs.of(3, false, MatchType.THREE)),
                Arguments.of(MatchOfMethodArgs.of(3, true, MatchType.THREE)),
                Arguments.of(MatchOfMethodArgs.of(4, false, MatchType.FOUR)),
                Arguments.of(MatchOfMethodArgs.of(4, true, MatchType.FOUR)),
                Arguments.of(MatchOfMethodArgs.of(5, false, MatchType.FIVE)),
                Arguments.of(MatchOfMethodArgs.of(5, true, MatchType.FIVE_WITH_BONUS)),
                Arguments.of(MatchOfMethodArgs.of(6, false, MatchType.SIX)),
                Arguments.of(MatchOfMethodArgs.of(6, true, MatchType.SIX))
        );
    }

    private static Stream<Arguments> allMatches() {
        return Arrays.stream(MatchType.values())
                .map(Arguments::of);
    }

    private record MatchOfMethodArgs(
            long matchCount, boolean bonusNumberMatch,
            MatchType expectedMatchType
    ) {

        private static MatchOfMethodArgs of(
                long matchCount, boolean bonusNumberMatch, MatchType expectedMatchType
        ) {
            return new MatchOfMethodArgs(matchCount, bonusNumberMatch, expectedMatchType);
        }
    }
}