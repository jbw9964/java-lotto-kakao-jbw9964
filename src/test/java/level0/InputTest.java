package level0;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InputTest {

    private static Stream<Arguments> customDeliminatorArgs() {
        return Stream.of(
                Arguments.of("//;\\n1;2;3", ";"),
                Arguments.of("//6\\n16263", "6"),
                Arguments.of("//f\\n1f2,3", "f")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//;\\n1;2;3", "//!\\n1!2:3", "1,2,3"
    })
    @DisplayName("입력으로부터 숫자를 파싱할 수 있다.")
    void testParsingNumber(String inputString) {
        Input input = new Input(inputString);

        Numbers numbers = input.getNumbers();

        level0.Number number1 = new level0.Number(1);
        Number number2 = new level0.Number(2);
        level0.Number number3 = new level0.Number(3);

        Numbers expectedNumbers = new Numbers(number1, number2, number3);
        assertThat(numbers).isEqualTo(expectedNumbers);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1:2:3", "1,2"
    })
    @DisplayName("입력으로부터 기본 구분자를 이용해 파싱할 수 있다.")
    void testDefaultDeliminator(String inputString) {
        Input input = new Input(inputString);

        assertThat(input.getDeliminators())
                .containsExactlyInAnyOrderElementsOf(Input.DEFAULT_DELIMINATORS);
    }

    @ParameterizedTest
    @MethodSource("customDeliminatorArgs")
    @DisplayName("입력으로부터 커스텀 구분자를 이용해 파싱할 수 있다.")
    void testCustomDeliminator(String inputString, String expectedDeliminator) {
        Input input = new Input(inputString);

        Set<String> deliminators = input.getDeliminators();
        List<String> expectedDeliminators = new ArrayList<>(Input.DEFAULT_DELIMINATORS);
        expectedDeliminators.add(expectedDeliminator);

        assertThat(deliminators).containsExactlyInAnyOrderElementsOf(expectedDeliminators);
    }
}
