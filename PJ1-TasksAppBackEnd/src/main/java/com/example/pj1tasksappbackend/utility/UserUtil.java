package com.example.pj1tasksappbackend.utility;

import com.example.pj1tasksappbackend.models.User;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * The type User util.
 */
@Component
public class UserUtil {

    /**
     * Create user from json user.
     *
     * @param jsonUser the json user
     * @return the user
     */
    public User createUserFromJson(JSONObject jsonUser){
        return new User(
            jsonUser.getAsString("firstName").trim(),
            jsonUser.getAsString("lastName").trim(),
            jsonUser.getAsString("email").trim(),
            jsonUser.getAsString("password").trim()
        );
    }
}
