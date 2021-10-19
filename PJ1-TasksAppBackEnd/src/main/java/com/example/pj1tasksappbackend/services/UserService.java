package com.example.pj1tasksappbackend.services;

import com.example.pj1tasksappbackend.models.User;
import com.example.pj1tasksappbackend.repositories.UserRepository;
import com.example.pj1tasksappbackend.utility.JwtUtil;
import com.example.pj1tasksappbackend.utility.UserUtil;
import com.example.pj1tasksappbackend.utility.ValidatorUtil;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final ValidatorUtil validatorUtil;
    public UserService(UserRepository userRepo, JwtUtil jwtUtil, UserUtil userUtil, ValidatorUtil validatorUtil){
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.userUtil = userUtil;
        this.validatorUtil = validatorUtil;
    }



    /**
     * Sign In Service
     *
     * This service provides the functionality for a user to sign in.
     *
     * This service takes a JSON Object that contains the following parameter(s)
     *      - email: The user's email | String
     *      - password: The user's hashed password | String
     *
     * This function check for the following case(s):
     *      1. If the user never signed up -> Code 404
     *      2. If the user entered the wrong email and/or password -> Code 401
     *
     * @param emailAndPassword A JSONObject containing the above parameters.
     * @return A ResponseEntity with a string JWT as a body with the user's email and the validity time of the JWT
     */
    public ResponseEntity<String> signInService(JSONObject emailAndPassword){
        String email = emailAndPassword.getAsString("email").trim();
        String password = emailAndPassword.getAsString("password").trim();

        Optional<User> optionalUser = userRepo.findUserByEmail(email);

        if (optionalUser.isPresent()) { // The User exists in DB
            User user = optionalUser.get();

            if (user.getPassword().equals(password)) { //  -- The user entered the correct email --
//                return new ResponseEntity<>(jwtUtil.generateJwt(user), HttpStatus.OK); // 200
                return ResponseEntity
                    .ok() // 200
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jwtUtil.generateJwt(user));

            } else { // -- Incorrect Password --
                return new ResponseEntity<>("Incorrect Credentials. Please check and try again.", HttpStatus.UNAUTHORIZED); // 401
            }

        } else { // -- User doesn't exist --
            return new ResponseEntity<>("The account doesn't exist. Please create an account.", HttpStatus.NOT_FOUND); // 404
        }
    }



    /**
     * Sign up service.
     *
     * This service provides the functionality for a user to create an account. It takes a JSON object and after the relevant checking,
     * it creates the object in the DB.
     *
     * This service takes a JSON Object that contains the following parameter(s)
     *      - firstName: The user's first name | String
     *      - lastName: The user's last name | String
     *      - email: The user's email | String
     *      - password: The user's hashed password | String
     *      - retypedPassword: The user's retyped hashed password | String
     *
     * This function check for the following case(s):
     *      1. The user already has an account -> Code 302
     *
     * @param jsonUser A JSONObject containing the above parameters.
     * @return A ResponseEntity with a string JWT as a body with the user's email and the validity time of the JWT
     */
    public ResponseEntity<String> signUpService(JSONObject jsonUser){
        Optional<User> optionalUser = userRepo.findUserByEmail( jsonUser.getAsString("email").trim() );

        if (optionalUser.isPresent()) { // The User exists in DB, already signed up
            return new ResponseEntity<>("There is already and account with this email. Please sign in", HttpStatus.FORBIDDEN); // 403

        } else { // -- User doesn't exist --
            User user = userUtil.createUserFromJson(jsonUser);

            if(!validatorUtil.isEmailValid(user.getEmail())){ // Validate Email
                return new ResponseEntity<>("Please enter a valid email.", HttpStatus.BAD_REQUEST); // 400

            } else if (!user.getPassword().equals(jsonUser.getAsString("retypedPassword").trim())){ //Check is passwords match
                return new ResponseEntity<>("Your passwords do not match.", HttpStatus.BAD_REQUEST); // 400

            } else if (!validatorUtil.isFirstNameAndLastNameValid(user.getFirstName(), user.getLastName())){
                return new ResponseEntity<>(
                        "Please enter your first and last name without spaces, special characters and/or numbers",
                        HttpStatus.BAD_REQUEST // 400
                );
            }

            userRepo.save(user);
            return new ResponseEntity<>(jwtUtil.generateJwt(user), HttpStatus.CREATED); // 201
        }
    }
}