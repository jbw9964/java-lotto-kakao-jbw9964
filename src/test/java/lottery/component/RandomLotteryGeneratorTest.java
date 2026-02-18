package lottery.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lottery.domain.Lottery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomLotteryGeneratorTest {

    private static final RandomLotteryGenerator randomLotteryGenerator
            = RandomLotteryGenerator.getInstance();

    @Test
    @DisplayName("주어진 개수만큼 랜던 로또를 생성할 수 있다.")
    void testGenerateRandomLotteries() {
        int testSize = 5;

        for (int numberOfLotteries = 0; numberOfLotteries < testSize; numberOfLotteries++) {
            List<Lottery> randomLotteries = randomLotteryGenerator.generateRandomLotteries(
                    numberOfLotteries
            );

            assertThat(randomLotteries).hasSize(numberOfLotteries);
        }
    }

    @Test
    @DisplayName("0 보다 적은 개수의 랜덤 로또는 생성할 수 없다.")
    void testNegativeNumberOfRandomLotteries() {
        assertThatThrownBy(() -> randomLotteryGenerator.generateRandomLotteries(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}