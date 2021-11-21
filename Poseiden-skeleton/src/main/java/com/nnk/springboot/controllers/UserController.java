package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.utils.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final String errorMessageInvalidPassword = "Invalid Password";

    @Autowired
    private UserService userService;

    /**
     * Create User Show Form add
     *
     * @return form user add
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Create user validate
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        // Valid Constraint Password
        String password = user.getPassword();
        boolean passwordIsValid = PasswordValidator.isValid(password);

        if (!result.hasErrors() && passwordIsValid) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.createUser(user);
            model.addAttribute("user", userService.findAll());
            logger.info(" SUCCESS POST /user/validate");
            return "redirect:/user/list";
        }

        if (!passwordIsValid) {
            model.addAttribute("errorMsg", errorMessageInvalidPassword);
            logger.error(" ERROR POST /user/validate > Invalid Password");
        }

        return "user/add";
    }

    /**
     * Read all user
     *
     * @return user list
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        List userList = userService.findAll();
        model.addAttribute("users", userList);
        return "user/list";
    }

    /**
     * Read - Get one User
     *
     * @param id The id of the user
     * @return An user list object full filled
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {

        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()) {
            logger.info(" SUCCESS READ /user/" + id);
            return userOptional.get();
        } else {
            logger.error(" ERROR READ /user/" + id);
            return null;
        }
    }


    /**
     * Update
     * show form update
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Update valid user
     *
     * @return user list
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {

        String password = user.getPassword();
        boolean passwordIsValid = PasswordValidator.isValid(password);

        if (!result.hasErrors() && passwordIsValid) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.createUser(user);
            model.addAttribute("user", userService.findAll());
            logger.info(" SUCCESS POST /user/update");
            return "redirect:/user/list";
        }
        if (!passwordIsValid) {
            model.addAttribute("errorMsg", errorMessageInvalidPassword);
            logger.error(" ERROR POST /user/update > Invalid Password");
        }

        return "redirect:/user/list";
    }


    /**
     * Delete User by Id
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteUserById(user.getId());
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

}
