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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.models.Privilege;
import test.repositories.PrivilegeRepository;
import test.services.PrivilegeService;

@RestController
@RequestMapping("/apiV0/privilege")
public class PrivilegeController {
  @Autowired PrivilegeService privilegeService;
  @Autowired private JWTService jwtService;
  @Autowired private PrivilegeRepository privilegeRepo;

  @PostMapping("/")
  public ResponseEntity<?> addPrivilege(
      @RequestHeader("Authorization") String bearerToken, @RequestBody Privilege privilege) {
    Map<String, String> map = new HashMap<>();
    try {
      jwtService.verifyJWT(bearerToken, privilegeRepo.findByPrivilege("manager"));
      privilegeService.addPrivilege(privilege);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (DataIntegrityViolationException e) {
      return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    } catch (JWTVerificationException | NoSuchElementException e) {
      System.out.println(e);
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } catch (Exception e) {
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
  }
}
