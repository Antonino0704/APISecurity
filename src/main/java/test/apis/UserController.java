package test.apis;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.models.User;
import test.models.UserAuthentication;
import test.services.UserService;

@RestController
@RequestMapping("/apiV0/user")
public class UserController {
  @Autowired private UserService userService;

  @GetMapping("/{email}")
  public ResponseEntity<?> getUser(@PathVariable String email) {
    Map<String, String> map = new HashMap<>();
    try {
      map.put("user", userService.getUserService(email));
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/add")
  public ResponseEntity<?> addUser(@RequestBody ObjectNode objectNode) {
    try {
      userService.addUserService(
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

  @PutMapping("/edit/{email}")
  public ResponseEntity<?> editUserFirstName(@RequestBody User user, @PathVariable String email) {
    try {
      user.setEmail(email);
      userService.editUserFirstName(user);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
