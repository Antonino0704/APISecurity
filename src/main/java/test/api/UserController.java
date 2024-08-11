package test.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiV0/user")
public class UserController {

  @GetMapping("/")
  public String getUser() {
    // TO DO
    return null;
  }

  @PostMapping("/add")
  public ResponseEntity<?> addUser(
      @RequestBody String firstName,
      @RequestBody String lastName,
      @RequestBody String email,
      @RequestBody String password) {
    // TO DO
    return null;
  }

  @PutMapping("/edit")
  public ResponseEntity<?> editUserFirstName(
      @RequestHeader("Authorization") String bearerToken, @RequestBody String firstName) {
    // TO DO
    return null;
  }
}
