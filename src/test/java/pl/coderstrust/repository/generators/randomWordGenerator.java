package pl.coderstrust.repository.generators;

import java.util.Random;

public class randomWordGenerator {

    public static String randomWordGenerator() {
        Random random = new Random();
        String tableOfChars = "abcdefghijklmnopqrstuvxyz";
        StringBuilder characters = new StringBuilder();
        while (characters.length() < 10) {
            int index = (int) (random.nextFloat() * tableOfChars.length());
            characters.append(tableOfChars.charAt(index));
        }
        String saltStr = characters.toString();
        return saltStr;
    }
}
