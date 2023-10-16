package com.project.StockAlarms.repository;

import com.project.StockAlarms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
