package test.apis;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.models.Privilege;
import test.services.PrivilegeService;

@RestController
@RequestMapping("/apiV0/privilege")
public class PrivilegeController {
  @Autowired PrivilegeService privilegeService;

  @PostMapping("/")
  public ResponseEntity<?> addPrivilege(@RequestBody Privilege privilege) {
    Map<String, String> map = new HashMap<>();
    try {
      privilegeService.addPrivilege(privilege);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
  }
}
