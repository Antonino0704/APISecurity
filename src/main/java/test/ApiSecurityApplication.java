package test;

import apisecurity.services.JWTService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "test.repositories")
public class ApiSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiSecurityApplication.class, args);
  }

  @Bean
  public JWTService getJWTService() {
    return new JWTService();
  }
}
