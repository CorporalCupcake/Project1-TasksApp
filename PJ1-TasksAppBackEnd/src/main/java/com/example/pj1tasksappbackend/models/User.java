package com.example.pj1tasksappbackend.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * The type User.
 */
@Entity
@Table(name = "users_test")
public class User {

    //-------------------- Variables --------------------
    @Id
    @Column(name="uuid", length=40)
    private String uuid;

    @Column(name="first_name", length=20)
    private String firstName;

    @Column(name="last_name", length=20)
    private String lastName;

    @Column(name="email", length=50)
    private String email;

    @Column(name="password", length=50)
    private String password;

    @Column(name="created_at_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAtTimestamp;

    /**
     * Instantiates a new User.
     */
//-------------------- Constructors --------------------
    public User() {};

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param password  the password
     */
    public User(String firstName, String lastName, String email, String password) {
        this.uuid = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAtTimestamp = new Date(System.currentTimeMillis());
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
//-------------------- Getters and Setters --------------------
    public String getUuid() {
        return uuid;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets created at timestamp.
     *
     * @return the created at timestamp
     */
    public Date getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAtTimestamp=" + createdAtTimestamp +
                ", password='" + password + '\'' +
                '}';
    }
}