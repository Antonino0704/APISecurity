package test;

import apisecurity.services.JWTService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import test.services.AuthService;
import test.services.PrivilegeService;
import test.services.UserService;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "test.repositories")
public class ApiSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiSecurityApplication.class, args);
  }

  @Bean
  public UserService getUserService() {
    return new UserService();
  }

  @Bean
  public JWTService getJWTService() {
    return new JWTService();
  }

  @Bean
  public PrivilegeService getPrivilegeService() {
    return new PrivilegeService();
  }

  @Bean
  public AuthService getAuthService() {
    return new AuthService();
  }
}
