package com.project.StockAlarms.impl;

import com.project.StockAlarms.dto.LoginDTO;
import com.project.StockAlarms.dto.UserDTO;
import com.project.StockAlarms.model.User;
import com.project.StockAlarms.repository.UserRepository;
import com.project.StockAlarms.response.LoginMessage;
import com.project.StockAlarms.service.AuthenticationService;
import com.project.StockAlarms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserIMPL implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public String addUser(UserDTO userDTO) {
        User newUser = new User(
               // userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        );
        userRepository.save(newUser);

        return newUser.getEmail();
    }
    UserDTO userDTO;

    @Override
    public LoginMessage loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null) {

                Optional<User> user1 = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
                if (user1.isPresent()) {
                    return new LoginMessage("Login Successfully", true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
        } else {
                return new LoginMessage("Email not exits", false);
        }
    }

    public Long getIdUserByEmail(String email) {
        return userRepository.findByEmail(email).getId();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

