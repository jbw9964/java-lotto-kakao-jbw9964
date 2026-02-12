package level1.domain;

import java.util.List;
import level1.exception.DuplicateLotteryNumberException;

public class AnswerLottery extends Lottery {

    private final int bonusLotteryNumber;

    /*
    `규칙 3: 모든 원시값과 문자열을 포장한다.`
    로또 번호의 객체가 있다면 로또 번호만의 책임을 부여할수있지 않을까요?

    --> `AnswerLottery` 는 "로또 번호 + 보너스 번호 정보 소유" 에 집중해 리팩터링 하였습니다.
        - 삭제된 메서드
            - #judge(Lottery) :
                `Match` 객체가 `AnswerLottery`, `Lottery` 를 비교한 점?수 를 지칭하긴 하지만,
                해당 책임을 `AnswerLottery` 가 갖는 것은 아닌것 같아 제거하였습니다.
     */
    public AnswerLottery(List<Integer> lotteryNumbers, int bonusLotteryNumber) {
        super(lotteryNumbers);

        this.bonusLotteryNumber = bonusLotteryNumber;

        if (this.contains(bonusLotteryNumber)) {
            throw new DuplicateLotteryNumberException("보너스 볼은 기존 로또 번호와 중복되지 않아야 합니다.");
        }
    }

    public boolean containsBonusNumber(Lottery givenLottery) {
        return givenLottery.contains(this.bonusLotteryNumber);
    }
}
