package com.example.pj1tasksappbackend.repositories;

import com.example.pj1tasksappbackend.models.User;
import com.example.pj1tasksappbackend.utility.repositoryUtil;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String>, repositoryUtil {

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     */
    @Query( value = ("SELECT * FROM " + userTable + " WHERE email = ?1"),
            nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    /**
     * Gets user uuid from email.
     *
     * @param email the email
     * @return the user uuid from email
     */
    @Query( value = ("SELECT uuid FROM " + userTable + " WHERE email = ?1"),
            nativeQuery = true)
    String getUserUuidFromEmail(String email);
}
