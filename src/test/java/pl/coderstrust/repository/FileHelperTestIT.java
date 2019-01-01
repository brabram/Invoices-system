package pl.coderstrust.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

public class FileHelperTestIT {

  @Test
  public void shouldProcessFile() throws IOException {
    //Given
    FileHelper fileHelper = new FileHelper();
    String actualFile = "src/test/test resources/actual.txt";
    String expectedFile = "src/test/test resources/expected.txt";
    byte[] expected = Files.readAllBytes(Paths.get(expectedFile));
    String expectedLastLine = "line5";

    //When
    fileHelper.create(actualFile);
    fileHelper.writeLine(actualFile, "line1");
    fileHelper.writeLine(actualFile, "line2");
    fileHelper.clear(actualFile);
    fileHelper.writeLine(actualFile, "line3");
    fileHelper.removeLine(1);
    fileHelper.writeLine(actualFile, "line4");
    fileHelper.writeLine(actualFile, "line5");
    String actualLastLine = fileHelper.readLastLine(actualFile);
    byte[] actual = Files.readAllBytes(Paths.get(actualFile));

    //Then
    Assert.assertArrayEquals(expected, actual);
    Assert.assertEquals(expectedLastLine, actualLastLine);
  }
}
