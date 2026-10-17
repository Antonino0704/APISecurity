package test.apisecurity.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.exceptions.JWTVerificationException;

import apisecurity.services.JWTService;
import test.models.Privilege;
import test.models.User;

@SpringBootTest
public class JWTServiceTests {

  @Autowired private JWTService jwtService;

  @Test
  public void verifyValidToken() {
    User user = new User("testmail@test.com", null, null);
    ArrayList<Privilege> privileges = new ArrayList<>();
    privileges.add(new Privilege(1, "test privilege"));
    user.setPrivileges(privileges);

    String token = "Bearer " + jwtService.generateToken(user);
    assertDoesNotThrow(() -> jwtService.verifyJWT(token, user));
  }

  @Test
  public void verifyNotValidToken() {
    User user = new User("testmail@test.com", null, null);
    ArrayList<Privilege> privileges = new ArrayList<>();
    privileges.add(new Privilege(1, "test privilege"));
    user.setPrivileges(privileges);

    String token = "Bearer fake token";
    assertThrows(JWTVerificationException.class, () -> jwtService.verifyJWT(token, user));
  }
}
