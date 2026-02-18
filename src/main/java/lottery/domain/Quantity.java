package lottery.domain;

public record Quantity(
        int amount
) {

    public Quantity {
        if (amount < 0) {
            throw new IllegalArgumentException("수량은 0 보다 적을수 없습니다.");
        }
    }

    public Quantity reduceQuantity(int reducingAmount) {
        if (reducingAmount < 0) {
            throw new IllegalArgumentException("감소시킬 수량은 0 보다 크거나 같아야합니다.");
        }

        int remainingAmount = this.amount - reducingAmount;

        if (remainingAmount < 0) {
            throw new IllegalStateException("감소시킬 수량은 남아있는 수량보다 클 수 없습니다.");
        }

        return new Quantity(remainingAmount);
    }

    public Quantity reduceQuantity(Quantity reducingQuantity) {
        return this.reduceQuantity(
                reducingQuantity.amount()
        );
    }
}
