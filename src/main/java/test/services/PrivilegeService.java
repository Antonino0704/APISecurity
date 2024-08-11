package test.services;

import org.springframework.beans.factory.annotation.Autowired;
import test.models.Privilege;
import test.repositories.PrivilegeRepository;

public class PrivilegeService {
  @Autowired private PrivilegeRepository privilegeRepo;

  public String addPrivilege(Privilege privilege) {
    return privilegeRepo.save(privilege).getPrivilege();
  }
}
