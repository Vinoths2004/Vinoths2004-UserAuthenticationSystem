package com.example.UserAuthenticationSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.UserAuthenticationSystem.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	

    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByResetToken(String resettoken);
	 User findByEmailAndPassword(String email, String password);
	Optional<User> findByJwtToken(String token);;
}

