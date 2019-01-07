package pl.coderstrust.repository.generators;

import java.util.Random;

public class WordGenerator {

  public static String getRandomWord() {
    Random random = new Random();
    char[] word = new char[random.nextInt(8) + 3];
    for (int i = 0; i < word.length; i++) {
      word[i] = (char) ('a' + random.nextInt(26));
    }
    return new String(word);
  }
}
