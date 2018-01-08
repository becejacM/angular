package org.levi9.repository;

import org.levi9.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

	User findByEmail(String email);

}
