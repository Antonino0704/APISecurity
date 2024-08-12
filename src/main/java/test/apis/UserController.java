package test.apis;

import apisecurity.services.JWTService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.models.Privilege;
import test.models.User;
import test.repositories.PrivilegeRepository;
import test.repositories.UserRepository;
import test.services.UserService;

@RestController
@RequestMapping("/apiV0/user")
public class UserController {
  @Autowired private UserService userService;
  @Autowired private JWTService jwtService;
  @Autowired private UserRepository userRepo;
  @Autowired private PrivilegeRepository privilegeRepo;

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

  @PutMapping("/edit/{email}")
  public ResponseEntity<?> editUserFirstName(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody User user,
      @PathVariable String email) {
    try {
      jwtService.verifyJWT(bearerToken, userRepo.findById(email).get());
      user.setEmail(email);
      userService.editUserFirstName(user);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NoSuchElementException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (JWTVerificationException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @PostMapping("/{email}/privilege")
  public ResponseEntity<?> addPrivilege(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Privilege privilege,
      @PathVariable String email) {
    try {
      jwtService.verifyJWT(bearerToken, privilegeRepo.findByPrivilege("CEO"));
      userService.addPrivilege(email, privilege);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NullPointerException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (DataIntegrityViolationException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    } catch (JWTVerificationException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } catch (NoSuchElementException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
