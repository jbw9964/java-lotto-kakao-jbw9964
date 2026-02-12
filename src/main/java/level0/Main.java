package level0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String inputString = br.readLine();
        Input input = new Input(inputString);

        Numbers numbers = input.getNumbers();
        Number sum = numbers.getSum();

        System.out.println(sum);
    }
}
