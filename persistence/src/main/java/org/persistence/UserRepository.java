package org.persistence;

import org.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {

}
