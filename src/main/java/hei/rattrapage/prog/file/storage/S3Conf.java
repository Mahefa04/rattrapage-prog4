package hei.rattrapage.prog.file.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Conf {

  private static final Region REGION = Region.EU_WEST_3;

  @Bean
  public S3Client s3Client() {
    return S3Client.builder().region(REGION).build();
  }

  @Bean
  public S3Presigner s3Presigner() {
    return S3Presigner.builder().region(REGION).build();
  }
}
