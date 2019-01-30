package pl.coderstrust.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
  private static Logger log = LoggerFactory.getLogger(FileHelper.class);
  private static final String ENCODING = "UTF-8";

  public void create(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Creating new file: {}", filePath);
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
  }

  public void delete(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Deleting file: {}", filePath);
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }

  public boolean exists(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Checking if file exists: {}", filePath);
    return new File(filePath).exists();
  }

  public boolean isEmpty(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Checking if file is empty: {}", filePath);
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
    log.debug("Clearing file: {}", filePath);
    FileUtils.write(new File(filePath), "", ENCODING);
  }

  public void writeLine(String filePath, String line) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (line == null) {
      throw new IllegalArgumentException("line cannot be null.");
    }
    log.debug("Writing lines in file: {}, line: {}", filePath, line);
    FileUtils.writeLines(new File(filePath), ENCODING, Collections.singleton(line), true);
  }

  public List<String> readLines(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Reading lines from file: {}", filePath);
    return FileUtils.readLines(new File(filePath), ENCODING);
  }

  public String readLastLine(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.debug("Reading last line from file: {}", filePath);
    try (ReversedLinesFileReader reversedLinesReader = new ReversedLinesFileReader(
        new File(filePath), Charset.defaultCharset())) {
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
    log.debug("Writing lines to file: {}, lines: {}", filePath, lines);
    FileUtils.writeLines(new File(filePath), ENCODING, lines, true);
  }

  public void removeLine(String filePath, int lineNumber) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (lineNumber <= 0) {
      throw new IllegalArgumentException("lineNumber cannot be less or equal to zero.");
    }
    log.debug("Removing lines from file: {}, lineNumber: {}", filePath, lineNumber);
    File file = new File(filePath);
    List<String> lines = readLines(file.getPath());
    lines.remove(lineNumber - 1);
    clear(filePath);
    writeLines(filePath, lines);
  }
}
