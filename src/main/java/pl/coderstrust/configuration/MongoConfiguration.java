package pl.coderstrust.configuration;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "mongo")
@Import({MongoDataAutoConfiguration.class})
public class MongoConfiguration {

  @Autowired
  private MongoDatabaseProperties mongoDatabaseProperties;

  @Bean
  @ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "mongo")
  public MongoClient mongoClient() {
    return new MongoClient(mongoDatabaseProperties.getHost(), mongoDatabaseProperties.getPort());
  }

  @Bean
  @ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "mongo")
  public MongoDbFactory getMongoDbFactory(MongoClient mongoClient) {
    return new SimpleMongoDbFactory(mongoClient, mongoDatabaseProperties.getDatabaseName());
  }

  @Bean
  @ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "mongo")
  public MongoTemplate getMmongoTemplate(MongoDbFactory mongoDbFactory) {
    MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
    mongoTemplate.setWriteConcern(new WriteConcern(1));
    return mongoTemplate;
  }
}
