package org.persistence;

import org.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByName(String name);
    
    @Query(value = "INSERT INTO USER VALUES (?1,?2) ", nativeQuery= true)
	public boolean createUser(String name, String pwd);
}
