package lottery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    @DisplayName("0 보다 작은 수량은 존재할 수 없다.")
    void testInvalidQuantity() {
        Assertions.assertThatThrownBy(() -> new Quantity(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("특정 수량을 소비할 수 있다.")
    void testReduceQuantity() {
        int amount = 10;
        int reduce = 3;
        int remainingAmount = amount - reduce;

        Quantity quantity = new Quantity(amount);
        Quantity remainingQuantity = new Quantity(remainingAmount);

        assertThatCode(() -> quantity.reduceQuantity(reduce))
                .doesNotThrowAnyException();

        assertThat(quantity).isEqualTo(remainingQuantity);
        assertThat(remainingQuantity).isEqualTo(quantity);
    }

    @Test
    @DisplayName("남아있는 모든 수량을 소비할 수 있다.")
    void testReduceAll() {
        int amount = 5;

        Quantity quantity = new Quantity(amount);
        Quantity zeroQuantity1 = new Quantity(0);
        Quantity zeroQuantity2 = new Quantity(0);

        assertThat(quantity.reduceAll()).isEqualTo(amount);
        assertThat(quantity).isEqualTo(zeroQuantity1);
        assertThat(zeroQuantity1).isEqualTo(quantity);

        assertThat(zeroQuantity1.reduceAll()).isEqualTo(0);
        assertThat(zeroQuantity1).isEqualTo(zeroQuantity2);
        assertThat(zeroQuantity2).isEqualTo(zeroQuantity1);
    }

    @Test
    @DisplayName("남은 재고보다 많은 수량은 소비할 수 없다.")
    void testInvalidReduceQuantity() {
        int amount = 3;
        int reduce = amount + 1;

        Quantity quantity = new Quantity(amount);

        Assertions.assertThatThrownBy(() -> quantity.reduceQuantity(reduce))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("감소시킬 수량은 0 보다 작을수 없다.")
    void testNegativeReduceAmount() {
        int amount = 5;
        int reduce = -1;

        Quantity quantity = new Quantity(amount);

        Assertions.assertThatThrownBy(() -> quantity.reduceQuantity(reduce))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
