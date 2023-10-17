package com.project.StockAlarms.controller;

import com.project.StockAlarms.dto.LoginDTO;
import com.project.StockAlarms.dto.UserDTO;
import com.project.StockAlarms.exception.UserNotFoundException;
import com.project.StockAlarms.model.User;
import com.project.StockAlarms.response.LoginMessage;
import com.project.StockAlarms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public String addUser(@RequestBody UserDTO userDTO) {
        String email = userService.addUser(userDTO);
        return email;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        LoginMessage loginMessage = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginMessage);
    }


    @GetMapping("/users")
    List<User> getAllUsers(){
        System.out.println(userService.findAll());
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    Optional<User> getUserById(@PathVariable Long id){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        return userService.findById(id);
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userService.deleteById(id);
        return  "User with id "+ id +" has been deleted successfully.";
    }
}
