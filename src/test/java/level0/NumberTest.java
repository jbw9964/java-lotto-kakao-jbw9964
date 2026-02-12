package level0;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "-1", "-2", "-100"
    })
    @DisplayName("음수인 입력은 RuntimeException 이 발생한다.")
    void testNegativeInteger(String value) {
        assertThatThrownBy(() -> new Number(value))
                .hasMessage("음수는 허용되지 않습니다.")
                .isInstanceOf(RuntimeException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "이상한 값", "숫자가 아닌 값"
    })
    @DisplayName("숫자가 아닌 입력은 RuntimeException 이 발생한다.")
    void testInvalidValue(String value) {
        assertThatThrownBy(() -> new level0.Number(value))
                .hasMessage("숫자가 아닌 입력입니다.")
                .isInstanceOf(RuntimeException.class);
    }
}
