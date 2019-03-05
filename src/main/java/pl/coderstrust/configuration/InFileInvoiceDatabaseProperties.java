package pl.coderstrust.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:in-file-database.properties")
@ConfigurationProperties(prefix = "pl.coderstrust.database.in-file")
public class InFileInvoiceDatabaseProperties {

  private String filePath;

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
