package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    @DisplayName("0 보다 작은 수량은 존재할 수 없다.")
    void testInvalidQuantity() {
        assertThatThrownBy(() -> new Quantity(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("특정 양을 소비한 수량을 제공받을 수 있다. (int)")
    void testReduceQuantityWithInt() {
        int totalAmount = 5;

        Quantity totalQuantity = new Quantity(totalAmount);

        for (int reducingAmount = 0; reducingAmount <= totalAmount; reducingAmount++) {

            Quantity expectedQuantity = new Quantity(
                    totalAmount - reducingAmount
            );

            assertThat(totalQuantity.reduceQuantity(reducingAmount))
                    .isEqualTo(expectedQuantity);
        }
    }

    @Test
    @DisplayName("특정 양을 소비한 수량을 제공받을 수 있다. (Quantity)")
    void testReduceQuantityWithQuantity() {
        int totalAmount = 5;

        Quantity totalQuantity = new Quantity(totalAmount);

        for (int reducingAmount = 0; reducingAmount <= totalAmount; reducingAmount++) {

            Quantity reducingQuantity = new Quantity(reducingAmount);

            Quantity expectedQuantity = new Quantity(
                    totalAmount - reducingAmount
            );

            assertThat(totalQuantity.reduceQuantity(reducingQuantity))
                    .isEqualTo(expectedQuantity);
        }
    }

    @Test
    @DisplayName("남은 수량보다 더 많은 수량은 소비할 수 없다.")
    void testInvalidReduceQuantity() {
        int totalAmount = 3;
        int reducingAmount = totalAmount + 1;

        Quantity quantity = new Quantity(totalAmount);

        assertThatThrownBy(() -> quantity.reduceQuantity(reducingAmount))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("감소시킬 수량은 0 보다 작을수 없다.")
    void testNegativeReduceAmount() {
        int totalAmount = 5;
        int reducingAmount = -1;

        Quantity quantity = new Quantity(totalAmount);

        assertThatThrownBy(() -> quantity.reduceQuantity(reducingAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
