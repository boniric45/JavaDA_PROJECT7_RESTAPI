package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Create a user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Read all user
     */
    public List findAll() {
        return userRepository.findAll();
    }

    /**
     * Read a user by id
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Update a user
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Delete a user by id
     */
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }


}
