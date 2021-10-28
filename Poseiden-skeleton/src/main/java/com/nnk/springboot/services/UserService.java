package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    public List findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        LOGGER.info("Adding new User list");
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        LOGGER.info("Updating User list");
        return userRepository.save(user);
    }

    public void deleteUserById(Integer userId) {
        LOGGER.info("Deleting User list");
        userRepository.deleteById(userId);
    }


}
