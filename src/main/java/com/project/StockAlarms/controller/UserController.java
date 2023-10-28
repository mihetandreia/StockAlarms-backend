package com.project.StockAlarms.controller;

import com.project.StockAlarms.dto.LoginDTO;
import com.project.StockAlarms.dto.UserDTO;
import com.project.StockAlarms.exception.UserNotFoundException;
import com.project.StockAlarms.model.User;
import com.project.StockAlarms.model.UserSession;
import com.project.StockAlarms.response.LoginMessage;
import com.project.StockAlarms.service.AuthenticationService;
import com.project.StockAlarms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    private String userSessionId;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public String addUser(@RequestBody UserDTO userDTO) {
        String email = userService.addUser(userDTO);
        return email;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        LoginMessage loginMessage = userService.loginUser(loginDTO);
        if (loginMessage.getMessage() == "Login Successfully") {
            Long idUser = userService.getIdUserByEmail(loginDTO.getEmail());
            this.userSessionId = authenticationService.authenticateUser(loginDTO.getEmail(), idUser);
            System.out.println("Login session " + this.userSessionId);
        }
        return ResponseEntity.ok(loginMessage);
    }

    @GetMapping("/whoAmI")
    public Optional<User> whoAmI() {
        UserSession userSession = authenticationService.getUserSessionBySessionId(this.userSessionId);
        Optional<User> user = userService.findById(userSession.getUserId());
        System.out.println("Logged in user " + this.userSessionId + " " + userSession.getEmail());

        return user;
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        System.out.println(userService.findAll());
        return userService.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        return userService.findById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteUser(@PathVariable Long id){
        if(!userService.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userService.deleteById(id);
        return  "User with id " + id + " has been successfully deleted.";
    }

    @GetMapping("/logout")
    public String logout() {
        authenticationService.removeSession(this.userSessionId);
        this.userSessionId = null;

        return "Logout successfully!";
    }
}
