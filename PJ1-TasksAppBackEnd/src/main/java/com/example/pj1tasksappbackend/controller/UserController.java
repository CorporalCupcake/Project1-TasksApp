package com.example.pj1tasksappbackend.controller;

import com.example.pj1tasksappbackend.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The type User controller.
 */
@RestController
@CrossOrigin(origins="http://localhost:8081")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) { this.userService = userService; }

    /**
     * Sign in api response entity.
     *
     * @param emailAndPassword the email and password
     * @return the response entity
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signInApi(@RequestBody JSONObject emailAndPassword) {
        return userService.signInService(emailAndPassword);
    }

    /**
     * Sign up api response entity.
     *
     * @param jsonUser the json user
     * @return the response entity
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUpApi(@RequestBody JSONObject jsonUser) {
        return userService.signUpService(jsonUser);
    }
}
