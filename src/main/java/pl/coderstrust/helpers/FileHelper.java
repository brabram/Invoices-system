package pl.coderstrust.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;

public class FileHelper {

  public void create(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
  }

  public void delete(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }

  public boolean exists(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    return new File(filePath).exists();
  }

  public boolean isEmpty(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException("Cannot find file");
    }
    return file.length() == 0;
  }

  public void clear(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    FileUtils.write(new File(filePath), "");
  }

  public void writeLine(String filePath, String line) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (line == null) {
      throw new IllegalArgumentException("line cannot be null.");
    }
    FileUtils.writeLines(new File(filePath), Collections.singleton(line), true);
  }

  public List<String> readLines(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    return FileUtils.readLines(new File(filePath));
  }

  public String readLastLine(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    try (ReversedLinesFileReader reversedLinesReader = new ReversedLinesFileReader(
        new File(filePath))) {
      return reversedLinesReader.readLine();
    }
  }

  public void writeLines(String filePath, List<String> lines) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (lines == null) {
      throw new IllegalArgumentException("lines cannot be null.");
    }
    FileUtils.writeLines(new File(filePath), lines, true);
  }

  public void removeLine(String filePath, int lineNumber) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (lineNumber <= 0) {
      throw new IllegalArgumentException("lineNumber cannot be less or equal to zero.");
    }
    File file = new File(filePath);
    List<String> lines = readLines(file.getPath());
    lines.remove(lineNumber - 1);
    clear(filePath);
    writeLines(filePath, lines);
  }
}
