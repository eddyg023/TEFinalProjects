package com.example.Team.controllers;

import com.example.Team.daos.UserDao;
import com.example.Team.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('COMMISSIONER')")
public class UserController {
    private UserDao userDao;

    /**
     * Constructor
     * @param userDao
     */
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Get all users
     * @return List of all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Get user by username
     * @param username
     * @return User
     */
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userDao.getUserByUsername(username);
    }

    /**
     * Create a new user
     * @param user
     * @return User
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User createUser(@RequestBody User user) {
       return userDao.createUser(user);
    }

    /**
     * Update a user
     * @param username
     * @param user
     * @return User
     */
    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        user.setUsername(username);
        return userDao.updateUser(user);
    }

    /**
     * Delete a user
     * @param username
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userDao.deleteUser(username);
    }

    /**
     * Get user roles
     * @param username
     * @return List of roles
     */
    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable String username) {
        return userDao.getUserRoles(username);
    }

    /**
     * Add role to user
     * @param username
     * @param role
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{username}/roles")
    public void addRoleToUser(@PathVariable String username, @RequestBody String role) {
        userDao.addRoleToUser(username, role);
    }

    /**
     * Delete role from user
     * @param username
     * @param role
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{username}/roles/{role}")
    public void deleteRoleFromUser(@PathVariable String username, @PathVariable String role) {
        userDao.removeRoleFromUser(username, role);
    }
}
