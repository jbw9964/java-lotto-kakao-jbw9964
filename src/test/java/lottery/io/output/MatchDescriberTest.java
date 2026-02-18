package lottery.io.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Arrays;
import java.util.stream.Stream;
import lottery.domain.MatchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MatchDescriberTest {

    private static final MatchDescriber matchDescriber = MatchDescriber.getInstance();

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("타입별 설명 내용이 구비되어 있다.")
    void testDescriptionSuppliablePerMatchTypes(MatchType matchType) {
        assertThatCode(() -> matchDescriber.describe(matchType))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("allMatches")
    @DisplayName("타입별 설명 내용은 비어있지 않다.")
    void testDescriptionNotEmpty(MatchType matchType) {
        assertThat(matchDescriber.describe(matchType)).isNotEmpty();
    }

    private static Stream<Arguments> allMatches() {
        return Arrays.stream(MatchType.values())
                .map(Arguments::of);
    }
}