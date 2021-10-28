package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     *
     * Home Page
     */
    @RequestMapping("/")
    public String home() {
        logger.info("SUCCESS REQUEST /home");
        return "home";
    }

    /**
     *
     * Home page admin
     */
    @RequestMapping("/admin/home")
    public String adminHome() {
        logger.info("SUCCESS REQUEST /admin/home");
        return "redirect:/bidList/list";
    }


}
