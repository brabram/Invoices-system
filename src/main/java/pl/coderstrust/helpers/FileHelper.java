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

class FileHelper {
Logger log = LoggerFactory.getLogger(FileHelper.class);
  private static final String ENCODING = "UTF-8";

  void create(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      log.info("Creating new file");
      file.createNewFile();
    }
  }

  void delete(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (file.exists()) {
      log.info("Deleting file");
      file.delete();
    }
  }

  boolean exists(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.info("Checking if file exists");
    return new File(filePath).exists();
  }

  boolean isEmpty(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException("Cannot find file");
    }
    log.info("Checking if file is empty");
    return file.length() == 0;
  }

  void clear(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.info("Clearing file");
    FileUtils.write(new File(filePath), "", ENCODING);
  }

  void writeLine(String filePath, String line) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (line == null) {
      throw new IllegalArgumentException("line cannot be null.");
    }
    log.info("Writing lines in file");
    FileUtils.writeLines(new File(filePath), ENCODING, Collections.singleton(line), true);
  }

  List<String> readLines(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    log.info("Reading lines from file");
    return FileUtils.readLines(new File(filePath), ENCODING);
  }

  String readLastLine(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    try (ReversedLinesFileReader reversedLinesReader = new ReversedLinesFileReader(
        new File(filePath), Charset.defaultCharset())) {
      log.info("Reading last line from file");
      return reversedLinesReader.readLine();
    }
  }

  void writeLines(String filePath, List<String> lines) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (lines == null) {
      throw new IllegalArgumentException("lines cannot be null.");
    }
    log.info("Writing lines to file");
    FileUtils.writeLines(new File(filePath), ENCODING, lines, true);
  }

  void removeLine(String filePath, int lineNumber) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (lineNumber <= 0) {
      throw new IllegalArgumentException("lineNumber cannot be less or equal to zero.");
    }
    log.info("Removing lines from file");
    File file = new File(filePath);
    List<String> lines = readLines(file.getPath());
    lines.remove(lineNumber - 1);
    clear(filePath);
    writeLines(filePath, lines);
  }
}
