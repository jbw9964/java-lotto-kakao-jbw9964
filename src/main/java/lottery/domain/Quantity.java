package lottery.domain;

import java.util.Objects;

public class Quantity {

    private int amount;

    public Quantity(int amount) {
        this.amount = amount;

        if (amount < 0) {
            throw new IllegalArgumentException("수량은 0 보다 적을수 없습니다.");
        }
    }

    public void reduceQuantity(int reduceAmount) {

        if (reduceAmount < 0) {
            throw new IllegalArgumentException("감소시킬 수량은 0 보다 크거나 같아야합니다.");
        }

        if (this.amount < reduceAmount) {
            throw new IllegalArgumentException("남아있는 수량이 부족합니다.");
        }

        this.amount -= reduceAmount;
    }

    public int reduceAll() {
        int reducedAmount = amount;

        this.amount = 0;

        return reducedAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Quantity quantity1)) {
            return false;
        }
        return amount == quantity1.amount;
    }
}
