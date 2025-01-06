package com.system.journal.usermanagement.repository;

import com.system.journal.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);

}
