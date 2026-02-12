package level1.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import level1.exception.DuplicateLotteryNumberException;

public class Lottery {

    private final Set<Integer> lotteryNumbers;

    /*
    `규칙 3: 모든 원시값과 문자열을 포장한다.`
    로또 번호의 객체가 있다면 로또 번호만의 책임을 부여할수있지 않을까요?

    --> `Lottery` 의 책임을 "로또 번호 정보 소유" 에 집중해 리팩터링 해봤습니다.
        - 추가된 메서드
            - #countMatchingLotteryNumbers(Lottery) :
                다른 `Lottery` 와 번호가 얼마나 일치하는지 비교할 수 있다.

        - 삭제된 메서드
            - #parseNumber(String) :
                string 을 int 로 파싱하는 기능은 `로또 번호 정보 소유` 책임과 무관한 것 같아 제거했습니다.

        - 삭제 또는 변경된 내부 로직
            - 생성자의 `로또 번호의 최소 최대값 검증` 을 제거하였습니다. 다만 `중복된 번호 검증` 은 남겨두었습니다.
            - `Set<Integer> lotteryNumbers` 의 접근 제어자를 `protect` 에서 `private` 으로 변경하였습니다.
     */
    public Lottery(List<Integer> numbers) {
        this.lotteryNumbers = new HashSet<>(numbers);

        if (this.lotteryNumbers.size() != numbers.size()) {
            throw new DuplicateLotteryNumberException("중복된 로또 번호가 제공되었습니다.");
        }
    }

    public boolean contains(int given) {
        return this.lotteryNumbers.contains(given);
    }

    public long countMatchingLotteryNumbers(Lottery givenLottery) {
        return this.lotteryNumbers.stream()
                .filter(givenLottery.lotteryNumbers::contains)
                .count();
    }

    public int getLotteryNumberLength() {
        return this.lotteryNumbers.size();
    }

    public String represent() {
        List<Integer> sortedNumbers = this.lotteryNumbers.stream()
                .sorted()
                .toList();

        return sortedNumbers.toString();
    }
}
