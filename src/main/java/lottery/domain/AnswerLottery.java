package lottery.domain;

import java.util.List;

public class AnswerLottery extends Lottery {

    private final LotteryNumber bonusLotteryNumber;

    public AnswerLottery(
            List<LotteryNumber> lotteryNumbers,
            LotteryNumber bonusLotteryNumber
    ) {
        super(lotteryNumbers);

        this.bonusLotteryNumber = bonusLotteryNumber;

        if (this.contains(bonusLotteryNumber)) {
            throw new IllegalArgumentException("보너스 볼은 기존 로또 번호와 중복되지 않아야 합니다.");
        }
    }

    public boolean containsBonusNumber(Lottery givenLottery) {
        return givenLottery.contains(this.bonusLotteryNumber);
    }
}
