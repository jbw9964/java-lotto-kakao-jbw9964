package level0;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumbersTest {

    @Test
    @DisplayName("여러 수의 합을 구할 수 있다.")
    void getSum() {
        int val1 = 1, val2 = 2, val3 = 3;
        int sum = val1 + val2 + val3;
        Numbers numbers;

        {
            level0.Number num1 = new level0.Number(1);
            level0.Number num2 = new level0.Number(2);
            Number num3 = new level0.Number(3);

            numbers = new Numbers(num1, num2, num3);
        }

        assertThat(numbers.getSum()).isEqualTo(new level0.Number(sum));
    }
}
