package test.apis;

import apisecurity.services.JWTService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.models.User;
import test.models.UserAuthentication;
import test.services.AuthService;

@RestController
@RequestMapping("/apiV0/auth")
public class AuthController {
  @Autowired private AuthService authService;
  @Autowired private JWTService jwtService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserAuthentication userAuth) {
    Map<String, String> map = new HashMap<>();
    try {
      User user = authService.login(userAuth);
      map.put("token", jwtService.generateToken(user));
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (DataIntegrityViolationException e) {
      return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      map.put("error", "user not found");
      return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/registration")
  public ResponseEntity<?> addUser(@RequestBody ObjectNode objectNode) {
    try {
      authService.registration(
          new User(
              objectNode.get("email").asText(),
              objectNode.get("firstName").asText(),
              objectNode.get("lastName").asText()),
          new UserAuthentication(
              objectNode.get("email").asText(), objectNode.get("password").asText()));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
