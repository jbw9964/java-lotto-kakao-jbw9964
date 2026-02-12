# 로또
## 진행 방법
* 로또 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

---

## 기능 구현

1. 사용자 금액으로 부터 로또 개수 파악해주는 기능
    - System
        - 랜덤 로또 제공
        - 사용자 입력으로 부터 로또 개수 파악하는거
        - 계산기한테 계산 위임
    - User
        - 사용자가 갖고 있는 lottery (저장)
    - Statics
        - 사용자 당첨 통계 내주기 --> 계산기

Lottery -- AnswerLottery (보너스 볼 포함)
-- UserLottery (결과 포함)

Lottery

- 음수값 있으면 안된다.
- 중복값 있으면 안된다.
