package lottery.domain;

import java.util.function.Function;

public record LotteryExpression(
        String leftBracket,
        String rightBracket,
        String numberDeliminator,
        NumberExpression numberExpression
) {

    private static final String
            DEFAULT_LEFT_BRACKET = "[",
            DEFAULT_RIGHT_BRACKET = "]",
            DEFAULT_LOTTERY_NUMBER_DELIMINATOR = ", ";

    private static final NumberExpression DEFAULT_NUMBER_EXPRESSION
            = NumberExpression.defaultExpression();

    public LotteryExpression(
            String leftBracket, String rightBracket, String numberDeliminator
    ) {
        this(
                leftBracket, rightBracket, numberDeliminator,
                DEFAULT_NUMBER_EXPRESSION
        );
    }

    public LotteryExpression {
        if (leftBracket == null || rightBracket == null) {
            throw new IllegalArgumentException("로또 괄호는 Null 일수 없습니다.");
        }

        if (numberDeliminator == null) {
            throw new IllegalArgumentException("번호 구분자는 Null 일수 없습니다.");
        }

        if (numberExpression == null) {
            throw new IllegalArgumentException("번호 문자열 변환식은 Null 일수 없습니다.");
        }
    }

    public static LotteryExpression defaultExpression() {
        return new LotteryExpression(
                DEFAULT_LEFT_BRACKET, DEFAULT_RIGHT_BRACKET,
                DEFAULT_LOTTERY_NUMBER_DELIMINATOR,
                DEFAULT_NUMBER_EXPRESSION
        );
    }

    public record NumberExpression(
            Function<Integer, String> numberToStringConverter
    ) {

        private static final Function<Integer, String>
                DEFAULT_NUMBER_FORMATTER = String::valueOf;

        public NumberExpression {
            if (numberToStringConverter == null) {
                throw new IllegalArgumentException("번호 문자열 변환기는 Null 일수 없습니다.");
            }
        }

        public static NumberExpression defaultExpression() {
            return new NumberExpression(
                    DEFAULT_NUMBER_FORMATTER
            );
        }
    }
}
