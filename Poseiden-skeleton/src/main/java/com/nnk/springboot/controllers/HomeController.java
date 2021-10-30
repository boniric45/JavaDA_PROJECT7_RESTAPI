package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserRepository userRepository;

    /**
     * Home Page
     */
    @RequestMapping("/")
    public String home() {

        // Init Account Administrator
        User user = new User(1,"admin","$2a$10$M.JLktEWbkrQr2HeLMmV7.PdsHBq8vxnuoCYHPFtowLagohMnI4Ci","Administrator","ADMIN");

       // if User Administrator is not present, Create Account
        if (!userRepository.findById(1).isPresent()){
            userRepository.save(user);
        }

        logger.info("SUCCESS REQUEST /home");
        return "home";
    }

}
