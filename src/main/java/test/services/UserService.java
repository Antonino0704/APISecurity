package test.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import test.models.Privilege;
import test.models.User;
import test.repositories.PrivilegeRepository;
import test.repositories.UserRepository;

public class UserService {
  @Autowired private UserRepository userRepo;
  @Autowired private PrivilegeRepository privilegeRepo;

  public String getUserService(String email) {
    User user = userRepo.findById(email).get();
    return user.getFirstName() + " " + user.getLastName();
  }

  public void editUserFirstName(User user) {
    User userUpdate = userRepo.findById(user.getEmail()).get();
    userUpdate.setFirstName(user.getFirstName());
    userRepo.save(userUpdate);
  }

  public void addPrivilege(String email, Privilege privilege) {
    User userUpdate = userRepo.findById(email).get();
    Privilege privilegeUpdate = privilegeRepo.findByPrivilege(privilege.getPrivilege());

    if (userUpdate.getPrivileges().contains(privilegeUpdate)) {
      throw new DataIntegrityViolationException("the user cannot have 2 equal privileges");
    }
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
