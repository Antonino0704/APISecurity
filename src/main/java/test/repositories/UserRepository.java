package test.repositories;

import org.springframework.data.repository.CrudRepository;
import test.models.User;

public interface UserRepository extends CrudRepository<User, String> {}
