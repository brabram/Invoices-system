package pl.coderstrust.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {

  private File file = null;

  public void create(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (file == null) {
      this.file = new File(filePath);
      file.createNewFile();
    }
  }

  public void delete(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    create(filePath);
    file.delete();
  }

  public boolean exists() throws FileNotFoundException {
    return file.exists();
  }

  public boolean isEmpty() throws FileNotFoundException {
    return file.length() == 0;
  }

  public void clear(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    PrintWriter printWriter = new PrintWriter(new File(filePath));
    printWriter.close();
  }

  public void writeLine(String filePath, String line) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    if (line == null) {
      throw new IllegalArgumentException("line cannot be null.");
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
    writer.append(line);
    writer.newLine();
    writer.close();
  }

  public List<String> readLines(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    List<String> lines = new ArrayList<>();
    Scanner scanner = new Scanner(new File(filePath));
    while (scanner.hasNextLine()) {
      lines.add(scanner.nextLine());
    }
    scanner.close();
    return lines;
  }

  public String readLastLine(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath cannot be null.");
    }
    Scanner scanner = new Scanner(new File(filePath));
    String lastLine = null;
    while (scanner.hasNextLine()) {
      lastLine = scanner.nextLine();
    }
    scanner.close();
    return lastLine;
  }

  public void removeLine(int lineNumber) throws FileNotFoundException {
    if (lineNumber <= 0) {
      throw new IllegalArgumentException("lineNumber cannot be less or equal to zero.");
    }
    List<String> lines = readLines(file.getPath());
    lines.remove(lineNumber - 1);
    PrintWriter printWriter = new PrintWriter(file.getPath());
    for (String line : lines) {
      printWriter.println(line);
    }
    printWriter.close();
  }
}
