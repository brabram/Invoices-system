package pl.coderstrust.generators;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
  private static AtomicLong atomicLong = new AtomicLong(1);

  public static Long getNextId() {
    return atomicLong.incrementAndGet();
  }
}
