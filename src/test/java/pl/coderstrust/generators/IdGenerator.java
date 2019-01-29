package pl.coderstrust.generators;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
  private static Random random = new Random();
  private static AtomicLong atomicLong = new AtomicLong(random.nextInt(999));

  public static Long getRandomId() {
    return atomicLong.incrementAndGet();
  }
}
