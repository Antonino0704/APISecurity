package test.services;

import org.springframework.beans.factory.annotation.Autowired;
import test.models.User;
import test.models.UserAuthentication;
import test.repositories.UserAuthenticationRepository;
import test.repositories.UserRepository;

public class UserService {
  @Autowired private UserRepository userRepo;
  @Autowired private UserAuthenticationRepository userAuthRepo;

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
}
