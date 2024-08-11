package test.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import test.models.Privilege;
import test.models.User;
import test.models.UserAuthentication;
import test.repositories.PrivilegeRepository;
import test.repositories.UserAuthenticationRepository;
import test.repositories.UserRepository;

public class UserService {
  @Autowired private UserRepository userRepo;
  @Autowired private UserAuthenticationRepository userAuthRepo;
  @Autowired private PrivilegeRepository privilegeRepo;

  public String getUserService(String email) {
    User user = userRepo.findById(email).get();
    return user.getFirstName() + " " + user.getLastName();
  }

  public void addUserService(User user, UserAuthentication userAuth) {
    userRepo.save(user);
    userAuthRepo.save(userAuth);
  }

  public void editUserFirstName(User user) {
    User userUpdate = userRepo.findById(user.getEmail()).get();
    userUpdate.setFirstName(user.getFirstName());
    userRepo.save(userUpdate);
  }

  public User login(UserAuthentication userAuth) {
    return userAuthRepo
        .findByEmailAndPassword(userAuth.getEmail(), userAuth.getPassword())
        .getUser();
  }

  public void addPrivilege(String email, Privilege privilege) {
    User userUpdate = userRepo.findById(email).get();
    Privilege privilegeUpdate = privilegeRepo.findByPrivilege(privilege.getPrivilege());

    List<Privilege> privileges = userUpdate.getPrivileges();
    List<User> users = privilegeUpdate.getUsers();

    privileges.add(privilege);
    users.add(userUpdate);

    userUpdate.setPrivileges(privileges);
    privilegeUpdate.setUsers(users);

    userRepo.save(userUpdate);
    privilegeRepo.save(privilegeUpdate);
  }
}
