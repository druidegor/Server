package org.example.tasktraker.service;

import org.example.tasktraker.data.UserDao;
import org.example.tasktraker.entity.User;
import org.example.tasktraker.exception.EntityNotFoundException;
import org.example.tasktraker.exception.TrackerException;
import org.example.tasktraker.exception.ValidationException;

import java.util.List;

public class UserService {

    private final UserDao userDao = new UserDao();

    public User login(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Email is empty");
        }
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is empty");
        }

        User user = userDao.login(email.trim(), password.trim());
        if (user == null) {
            throw new ValidationException("Invalid email or password");
        }
        return user;
    }

    public void register(String name, String email, String password, String confirmPassword, String role) {
    
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || role == null) {
            throw new ValidationException("Fill all fields");
        }


        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new ValidationException("Invalid email format");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long");
        }

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match");
        }


        if (userDao.existsByEmail(email)) {
            throw new ValidationException("User already exists");
        }

        int roleId = userDao.getRoleIdByName(role);
        if (roleId == -1) {
            throw new EntityNotFoundException("Role not found");
        }

        User user = new User(0, name, email, role, password);
        boolean saved = userDao.save(user, roleId);

        if (!saved) {
            throw new TrackerException("Registration failed due to server error");
        }
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserById(int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
        return user;
    }
}
