package com.example.Team.daos;

import com.example.Team.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    /**
     * Creates a new product dao with the given JDBC template.
     *
     * @param dataSource The JDBC data source to use.
     */
    private PasswordEncoder passwordEncoder;
    public UserDao(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all users from the datastore, ordered by user.
     *
     * @return All users as User objects in a List.
     */
    public List<User> getAllUsers() {
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT * FROM users");
        List<User> users = new ArrayList<>();
        while (rows.next()) {
            users.add(mapRowSetToUser(rows));
        }
        return users;
    }

    /**
     * Get a user from the datastore that has the given username.
     * If the username is not found, return null.
     *
     * @param username The username of the user.
     * @return A filled out User object, null if the username isn't in the database.
     */
    public User getUserByUsername(String username) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE username = ?", username);
        if (rows.next()) {
            return mapRowSetToUser(rows);
        } else {
            return null;
        }
    }

    /**
     * Create a new user in the database.
     *
     * @param user The user to create.
     * @return The user that was created.
     */
    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?)", user.getUsername(), hashedPassword);
        return getUserByUsername(user.getUsername());
    }

    /**
     * Update a user in the database.
     *
     * @param user The user to update.
     * @return The user that was updated.
     */
    public User updateUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", hashedPassword, user.getUsername());
        return getUserByUsername(user.getUsername());
    }

    /**
     * Delete a user from the database.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        jdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
    }

    /**
     * Get all roles associated with a user.
     *
     * @param username The username of the user.
     * @return All roles for a user as Strings in a List.
     */
    public List<String> getUserRoles(String username) {
        SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT rolename FROM roles WHERE username = ?", username);
        List<String> roles = new ArrayList<>();
        while (rows.next()) {
            roles.add(rows.getString("rolename"));
        }
        return roles;
    }

    /**
     * Add a role to a user.
     *
     * @param username The username of the user.
     * @param role The role to add.
     */
    public void addRoleToUser(String username, String role) {
        jdbcTemplate.update("INSERT INTO roles (username, rolename) VALUES (?, ?)", username, role);
    }

    /**
     * Remove a role from a user.
     *
     * @param username The username of the user.
     * @param role The role to remove.
     */
    public void removeRoleFromUser(String username, String role) {
        jdbcTemplate.update("DELETE FROM roles WHERE username = ? AND rolename = ?", username, role);
    }

    /**
     * Map a row set to a user object.
     *
     * @param results The row set to map.
     * @return The user object.
     */
    private User mapRowSetToUser(SqlRowSet results) {
        User user = new User();
        user.setUsername(results.getString("username"));
        user.setPassword(results.getString("password"));
        return user;
    }
}
