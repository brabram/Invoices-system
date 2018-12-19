package pl.coderstrust.helloWorld;

import java.util.ArrayList;
import java.util.List;

public class FooBar {
    public static void main(String[] args) {
        for (String line : getFooBar(100)) {
            System.out.println(line);
        }
    }

    public static List<String> getFooBar(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be greater than 0.");
        }
        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= number; i++) {
            stringBuilder.append(i + " ");
            if (i % 3 == 0) {
                stringBuilder.append("Foo");
            }
            if (i % 5 == 0) {
                stringBuilder.append("Bar");
            }
            result.add(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
        return result;
    }
}