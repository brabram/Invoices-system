package pl.coderstrust.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

class FileHelperTest {

  private FileHelper fileHelper = new FileHelper();

  @Test
  void checkIfCreateNewFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file1.txt";

    //When
    fileHelper.create(filePath);
    boolean isCreated = fileHelper.exists();

    //Then
    Assert.assertTrue(isCreated);
  }

  @Test
  void checkIfDeleteFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file2.txt";

    //When
    fileHelper.delete(filePath);
    boolean isCreated = fileHelper.exists();

    //Then
    Assert.assertFalse(isCreated);
  }

  @Test
  void checkIfFileExists() throws IOException {
    //Given
    String filePath = "src/test/test resources/file3.txt";

    //When
    fileHelper.create(filePath);
    boolean isCreated = fileHelper.exists();

    //Then
    Assert.assertTrue(isCreated);
  }

  @Test
  void checkIfFileIsEmpty() throws IOException {
    //Given
    String filePath = "src/test/test resources/file4.txt";
    fileHelper.create(filePath);

    //When
    boolean isEmpty = fileHelper.isEmpty();

    //Then
    Assert.assertTrue(isEmpty);
  }

  @Test
  void checkIfClearFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file5.txt";
    fileHelper.create(filePath);
    fileHelper.writeLine(filePath, "file is not empty");

    //When
    fileHelper.clear(filePath);
    boolean isEmpty = fileHelper.isEmpty();

    //Then
    Assert.assertTrue(isEmpty);
  }

  @Test
  void checkIfWriteLineToFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file6.txt";
    fileHelper.create(filePath);

    //When
    fileHelper.writeLine(filePath, "file is not empty");
    String line = fileHelper.readLastLine(filePath);

    //Then
    Assert.assertEquals("file is not empty", line);

  }

  @Test
  void checkIfReadLinesFromFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file7.txt";
    List<String> expected = new ArrayList<>();
    expected.add("first line");
    expected.add("second line");
    expected.add("third line");

    //When
    List<String> actual = fileHelper.readLines(filePath);

    //Then
    Assert.assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void checkIfReadLastLineFromFile() throws FileNotFoundException {
    //Given
    String filePath = "src/test/test resources/file8.txt";
    String expected = "third line";

    //When
    String actual = fileHelper.readLastLine(filePath);

    //Then
    Assert.assertEquals(expected, actual);
  }

  @Test
  void checkIfRemoveLineFromFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file9.txt";
    fileHelper.clear(filePath);
    fileHelper.writeLine(filePath, "first line");
    fileHelper.writeLine(filePath, "second line");
    fileHelper.writeLine(filePath, "third line");
    String expected = "second line";

    //When
    fileHelper.create(filePath);
    fileHelper.removeLine(3);
    String actual = fileHelper.readLastLine(filePath);

    //Then
    Assert.assertEquals(expected, actual);
  }
}