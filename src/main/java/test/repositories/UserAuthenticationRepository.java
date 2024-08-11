package test.repositories;

import org.springframework.data.repository.CrudRepository;
import test.models.UserAuthentication;

public interface UserAuthenticationRepository extends CrudRepository<UserAuthentication, String> {}
