package com.project.StockAlarms.repository;

import com.project.StockAlarms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmailAndPassword(String email, String Password);
    User findByEmail(String email);

}
