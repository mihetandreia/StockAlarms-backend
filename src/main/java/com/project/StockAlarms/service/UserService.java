package com.project.StockAlarms.service;

import com.project.StockAlarms.dto.LoginDTO;
import com.project.StockAlarms.dto.UserDTO;
import com.project.StockAlarms.model.User;
import com.project.StockAlarms.response.LoginMessage;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String addUser(UserDTO userDTO);

    LoginMessage loginUser(LoginDTO loginDTO);

    Long getIdUserByEmail(String email);

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
