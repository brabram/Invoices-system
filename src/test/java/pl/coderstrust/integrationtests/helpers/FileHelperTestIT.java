package pl.coderstrust.integrationtests.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.coderstrust.helpers.FileHelper;

class FileHelperTestIT {

  private FileHelper fileHelper = new FileHelper();

  @Test
  void checkIfCreateNewFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file1.txt";

    //When
    fileHelper.create(filePath);
    boolean fileExists = fileHelper.exists(filePath);

    //Then
    Assertions.assertTrue(fileExists);
  }

  @Test
  void checkIfDeleteFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file2.txt";
    fileHelper.create(filePath);

    //When
    fileHelper.delete(filePath);
    boolean fileExists = fileHelper.exists(filePath);

    //Then
    Assertions.assertFalse(fileExists);
  }

  @Test
  void checkIfFileExists() throws IOException {
    //Given
    String filePath = "src/test/test resources/file3.txt";

    //When
    boolean fileExists = fileHelper.exists(filePath);

    //Then
    Assertions.assertFalse(fileExists);
  }

  @Test
  void checkIfFileIsEmpty() throws IOException {
    //Given
    String filePath = "src/test/test resources/file4.txt";
    fileHelper.create(filePath);

    //When
    boolean emptyFile = fileHelper.isEmpty(filePath);

    //Then
    Assertions.assertTrue(emptyFile);
  }

  @Test
  void checkIfClearFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file5.txt";
    fileHelper.writeLine(filePath, "file is not empty");

    //When
    fileHelper.clear(filePath);
    boolean emptyFile = fileHelper.isEmpty(filePath);

    //Then
    Assertions.assertTrue(emptyFile);
  }

  @Test
  void checkIfWriteLineToFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file6.txt";

    //When
    fileHelper.writeLine(filePath, "file is not empty");
    String line = fileHelper.readLastLine(filePath);

    //Then
    Assertions.assertEquals("file is not empty", line);

  }

  @Test
  void checkIfReadLinesFromFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file7.txt";
    List<String> expected = new ArrayList<>();
    expected.add("first line");
    expected.add("second line");
    expected.add("third line");
    fileHelper.clear(filePath);
    fileHelper.writeLine(filePath, "first line");
    fileHelper.writeLine(filePath, "second line");
    fileHelper.writeLine(filePath, "third line");

    //When
    List<String> actual = fileHelper.readLines(filePath);

    //Then
    Assertions.assertEquals(expected.toString(), actual.toString());
  }

  @Test
  void checkIfReadLastLineFromFile() throws IOException {
    //Given
    String filePath = "src/test/test resources/file8.txt";
    String expected = "third line";
    fileHelper.writeLine(filePath, "first line");
    fileHelper.writeLine(filePath, "second line");
    fileHelper.writeLine(filePath, "third line");

    //When
    String actual = fileHelper.readLastLine(filePath);

    //Then
    Assertions.assertEquals(expected, actual);
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
    fileHelper.removeLine(filePath, 3);
    String actual = fileHelper.readLastLine(filePath);

    //Then
    Assertions.assertEquals(expected, actual);
  }
}
