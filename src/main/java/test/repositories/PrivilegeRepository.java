package test.repositories;

import org.springframework.data.repository.CrudRepository;
import test.models.Privilege;

public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {
  public Privilege findByPrivilege(String privilege);
}
