package pl.coderstrust.helpers;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHelperTestIT {

  private static final String INPUT_FILE = "src/test/resource/helpers/input_file";
  private static final String EXPECTED_FILE = "src/test/resource/helpers/expected_file";
  private static final String ENCODING = "UTF-8";
  private FileHelper fileHelper;

  @BeforeEach
  void setup() throws IOException {
    fileHelper = new FileHelper();
    File inputFile = new File(INPUT_FILE);
    if (inputFile.exists()) {
      inputFile.delete();
    }
    File expectedFile = new File(EXPECTED_FILE);
    if (expectedFile.exists()) {
      expectedFile.delete();
    }
  }

  @Test
  void shouldCreateFile() throws IOException {
    //When
    fileHelper.create(INPUT_FILE);

    //Then
    assertTrue(new File(INPUT_FILE).exists());
  }

  @Test
  void shouldDeleteNotExistingFile() throws IOException {
    //When
    fileHelper.delete(INPUT_FILE);

    //Then
    assertFalse(new File(INPUT_FILE).exists());
  }

  @Test
  void shouldDeleteExistingFile() throws IOException {
    //Given
    fileHelper.create(INPUT_FILE);

    //When
    fileHelper.delete(INPUT_FILE);

    //Then
    assertFalse(new File(INPUT_FILE).exists());
  }

  @Test
  void shouldReturnTrueIfFileExists() throws IOException {
    //Given
    File file = new File(INPUT_FILE);
    file.createNewFile();

    //When
    boolean result = fileHelper.exists(INPUT_FILE);

    //Then
    assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfFileDoesNotExists() throws IOException {
    //Given
    File file = new File(INPUT_FILE);

    //When
    boolean result = fileHelper.exists(INPUT_FILE);

    //Then
    assertFalse(result);
  }

  @Test
  void shouldReturnTrueIfFileIsEmpty() throws IOException {
    //Given
    fileHelper.create(INPUT_FILE);

    //When
    boolean result = fileHelper.isEmpty(INPUT_FILE);

    //Then
    Assertions.assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfFileIsNotEmpty() throws IOException {
    //Given
    fileHelper.create(INPUT_FILE);
    fileHelper.writeLine(INPUT_FILE, "test");

    //When
    boolean result = fileHelper.isEmpty(INPUT_FILE);

    //Then
    Assertions.assertFalse(result);
  }

  @Test
  void shouldClearFile() throws IOException {
    //Given
    File expectedFile = new File(EXPECTED_FILE);
    File inputFile = new File(INPUT_FILE);
    expectedFile.createNewFile();
    FileUtils.writeLines(inputFile, Collections.singleton("bla bla bla"), ENCODING, true);

    //When
    fileHelper.clear(INPUT_FILE);

    //Then
    assertTrue(FileUtils.contentEquals(expectedFile, inputFile));
  }

  @Test
  void shouldWriteLineToFile() throws IOException {
    //Given
    File expectedFile = new File(EXPECTED_FILE);
    File inputFile = new File(INPUT_FILE);
    expectedFile.createNewFile();
    FileUtils.writeLines(expectedFile, ENCODING, Collections.singleton("bla bla bla"), true);

    //When
    fileHelper.writeLine(INPUT_FILE, "bla bla bla");

    //Then
    assertTrue(FileUtils.contentEquals(expectedFile, inputFile));
  }

  @Test
  void shouldReadLinesFromFile() throws IOException {
    //Given
    File inputFile = new File(INPUT_FILE);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla bla bla"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla1 bla1 bla1"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla2 bla2 bla2"), true);
    List<String> expected = new ArrayList<>();
    expected.add("bla bla bla");
    expected.add("bla1 bla1 bla1");
    expected.add("bla2 bla2 bla2");

    //When
    List<String> result = fileHelper.readLines(INPUT_FILE);

    //Then
    assertEquals(expected, result);
  }

  @Test
  void shouldReadLastLineFromFile() throws IOException {
    //Given
    File inputFile = new File(INPUT_FILE);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla1 bla1 bla1"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla1 bla1 bla1"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla2 bla2 bla2"), true);
    String expected = "bla2 bla2 bla2";

    //When
    String result = fileHelper.readLastLine(INPUT_FILE);

    //Then
    assertEquals(expected, result);
  }

  @Test
  void shouldRemoveLineFromFile() throws IOException {
    //Given
    File expectedFile = new File(EXPECTED_FILE);
    File inputFile = new File(INPUT_FILE);
    expectedFile.createNewFile();
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla bla bla"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla1 bla1 bla1"), true);
    FileUtils.writeLines(inputFile, ENCODING, Collections.singleton("bla2 bla2 bla2"), true);
    FileUtils.writeLines(expectedFile, ENCODING, Collections.singleton("bla bla bla"), true);
    FileUtils.writeLines(expectedFile, ENCODING, Collections.singleton("bla2 bla2 bla2"), true);

    //When
    fileHelper.removeLine(INPUT_FILE, 2);

    //Then
    assertTrue(FileUtils.contentEquals(expectedFile, inputFile));

  }

  @Test
  void createMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.create(null));
  }

  @Test
  void deleteMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.delete(null));
  }

  @Test
  void existMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.exists(null));
  }

  @Test
  void isEmptyMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.isEmpty(null));
  }

  @Test
  void clearMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.clear(null));
  }

  @Test
  void writeLineMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.writeLine(null, "test"));
  }

  @Test
  void readLinesMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.readLines(null));
  }

  @Test
  void readLastLineMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.readLastLine(null));
  }

  @Test
  void writeLinesMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.writeLines(null, new ArrayList<>()));
  }

  @Test
  void removeLineMethodShouldThrowExceptionForNullAsFilePath() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.removeLine(null, 1));
  }

  @Test
  void writeLineMethodShouldThrowExceptionForNullAsLine() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.writeLine(INPUT_FILE, null));
  }

  @Test
  void writeLinesMethodShouldThrowExceptionForNullAsLines() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.writeLines(INPUT_FILE, null));
  }

  @Test
  void removeLineMethodShouldThrowExceptionForNumberLowerThanZeroAsLineNumber() {
    assertThrows(IllegalArgumentException.class, () -> fileHelper.removeLine(INPUT_FILE, -1));
  }
}
