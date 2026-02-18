package lottery.io.input;

public class UserInputQuery {

    private static final UserInputQuery instance = new UserInputQuery();

    private UserInputQuery() {
    }

    public static UserInputQuery getInstance() {
        return instance;
    }

    public String purchasePriceInputQuery() {
        return "구입금액을 입력해 주세요.";
    }

    public String numberOfManualLotteryInputQuery() {
        return "\n수동으로 구매할 로또 수를 입력해 주세요.";
    }

    public String manualLotteryInputQuery() {
        return "\n수동으로 구매할 번호를 입력해 주세요.";
    }

    public String answerLotteryInputQuery() {
        return "\n지난 주 당첨 번호를 입력해 주세요.";
    }

    public String bonusNumberInputQuery() {
        return "보너스 볼을 입력해 주세요.";
    }
}
