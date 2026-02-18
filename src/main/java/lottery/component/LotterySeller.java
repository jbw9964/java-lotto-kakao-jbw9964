package lottery.component;

import lottery.domain.Quantity;

public class LotterySeller {

    private static final int LOTTERY_PRICE = 1_000;

    private static final LotterySeller instance = new LotterySeller();

    private LotterySeller() {
    }

    public Quantity issueLotteryQuantity(int purchasePrice) {

        int lotteryPrice = LOTTERY_PRICE;

        if (purchasePrice < lotteryPrice) {
            throw new IllegalStateException(String.format(
                    "구입 금액은 %d 보다 크거나 같아야 합니다.",
                    lotteryPrice
            ));
        }

        int lotteryAmount = purchasePrice / lotteryPrice;

        return new Quantity(lotteryAmount);
    }

    public static LotterySeller getInstance() {
        return instance;
    }
}
