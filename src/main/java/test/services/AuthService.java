package test.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import test.models.Privilege;
import test.models.User;
import test.models.UserAuthentication;
import test.repositories.PrivilegeRepository;
import test.repositories.UserAuthenticationRepository;
import test.repositories.UserRepository;

public class AuthService {
  @Autowired private UserRepository userRepo;
  @Autowired private UserAuthenticationRepository userAuthRepo;
  @Autowired private PrivilegeRepository privilegeRepo;

  public void registration(User user, UserAuthentication userAuth) {
    Privilege privilege = privilegeRepo.findByPrivilege("employee");
    List<Privilege> privileges = new ArrayList<>();

    if (!userRepo.findById(user.getEmail()).isEmpty()) {
      throw new DataIntegrityViolationException("user already exists");
    }

    privileges.add(privilege);
    user.setPrivileges(privileges);
    privilege.getUsers().add(user);

    userRepo.save(user);
    privilegeRepo.save(privilege);
    userAuthRepo.save(userAuth);
  }

  public User login(UserAuthentication userAuth) {
    return userAuthRepo
        .findByEmailAndPassword(userAuth.getEmail(), userAuth.getPassword())
        .getUser();
  }
}
