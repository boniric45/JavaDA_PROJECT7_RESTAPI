package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final OAuth2AuthorizedClientService authorizedClientService; // stock token secured
    private String FULLNAME;

    @Autowired
    private UserRepository userRepository;

    // Constructor
    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @RequestMapping("/loginUser")
    public String getUser(Principal user, HttpSession session) {

        // Authenticate Form Login
        if (user instanceof UsernamePasswordAuthenticationToken) {
            getUsernamePasswordLoginInfo(user, session);
            logger.info("SUCCESS REQUEST LOGIN  ");
        } // Authenticate Form Token Google
        else if (user instanceof OAuth2AuthenticationToken) {
            // Get Name Token
            getOauth2LoginInfo(user);
            // Get Name in Base
            List<User> userList = userRepository.findAll(); // get User list
            for (User userName : userList) {
                // Compare fullname in bdd with fullname token
                if (userName.getFullname().equals(FULLNAME)) {
                    session.setAttribute("name", FULLNAME);
                    logger.info("SUCCESS REQUEST LOGIN TOKEN GOOGLE ");
                    return "redirect:/bidList/list";
                }
            }
            return "403"; // bad Crédential
        }
        return "redirect:/bidList/list";
    }

    @RequestMapping("/logout")
    public String logoutUser() {
        return "/loginUser";
    }

    // authenticated by login
    private void getUsernamePasswordLoginInfo(Principal user, HttpSession session) {
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if (token.isAuthenticated()) {
            session.setAttribute("name", token.getName());
        }
    }

    // authenticated by token
    private void getOauth2LoginInfo(Principal user) {
        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        logger.info("Token Client"+authClient);
        if (authToken.isAuthenticated()) {
            OidcIdToken idToken = getIdToken(principal);
            FULLNAME = idToken.getFullName();
        }
    }

    private OidcIdToken getIdToken(OAuth2User principal) {
        if (principal instanceof DefaultOAuth2User) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            return oidcUser.getIdToken();
        }
        return null;
    }

    @GetMapping("secure/article-details")
    @RolesAllowed("ADMIN")
    public ModelAndView getAllUser() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }
}
