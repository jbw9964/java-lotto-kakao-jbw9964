package lottery.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lottery.domain.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LotterySellerTest {

    private static final LotterySeller lotterySeller = LotterySeller.getInstance();

    @Test
    @DisplayName("구입 금액으로 매입한 로또 수량을 제공받을수 있다.")
    void testIssueLotteryQuantity() {
        int purchasePrice = 2_100;
        Quantity expectedQuantity = new Quantity(2);

        assertThat(lotterySeller.issueLotteryQuantity(purchasePrice))
                .isEqualTo(expectedQuantity);
    }

    @Test
    @DisplayName("로또 금액보다 낮은 금액은 로또를 구매할 수 없다.")
    void testInsufficientPurchasePrice() {
        int insufficientPrice = 900;

        assertThatThrownBy(() -> lotterySeller.issueLotteryQuantity(insufficientPrice))
                .isInstanceOf(IllegalStateException.class);
    }
}