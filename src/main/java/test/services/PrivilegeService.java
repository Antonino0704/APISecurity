package test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.models.Privilege;
import test.repositories.PrivilegeRepository;

@Service
public class PrivilegeService {
  @Autowired private PrivilegeRepository privilegeRepo;

  public String addPrivilege(Privilege privilege) {
    return privilegeRepo.save(privilege).getPrivilege();
  }
}
