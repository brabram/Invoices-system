package pl.coderstrust.database;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdentifierGenerator {

  private AtomicLong identifier;

  public void initalize(long initialValue){
    identifier = new AtomicLong(initialValue);
  }

  public long getNextId() throws IdentifierGeneratorException {
    if(identifier == null){
      throw new IdentifierGeneratorException("Generator has to be initialized before first usage");
    }
    return identifier.incrementAndGet();
  }
}
