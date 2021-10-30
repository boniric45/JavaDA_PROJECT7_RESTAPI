package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.utils.PasswordValidator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final int ID = 1;
    private final String passwordValidTest = "Password12345!";
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    @InjectMocks
    UserController userController;
    @Mock
    UserService userService;
    @Mock
    User user;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    /**
     * Test Create a new User
     */
    @Test
    public void testCreateUser() {
        assertThat(userController.addUser(user), is("user/add"));
    }

    /**
     * Test Create a User then valid
     */
    @Test
    public void testCreateUserThenValid() {
        User user = new User(ID, "usename", passwordValidTest, "fullname", "user");
        assertThat(userController.validate(user, bindingResult, model), is("redirect:/user/list"));
    }

    /**
     * Test Create a User Password is not valid
     */
    @Test
    public void testCreateUserThenPasswordNotValid() {
        User user = new User(ID, "usename", "125", "fullname", "user");
        boolean passwordValid = PasswordValidator.isValid(user.getPassword());
        when(bindingResult.hasErrors() && passwordValid).thenReturn(false);
        assertThat(userController.validate(user, bindingResult, model), is("user/add"));
    }


    /**
     * Test Read a User by Id
     */
    @Test
    public void testGetUserById() {
        when(userService.findById(ID)).thenReturn(java.util.Optional.ofNullable(user));
        assertEquals(userController.getUserById(ID), user);
    }

    /**
     * Test Read a User then not valid
     */
    @Test
    public void testGetUserThenNotValid() {
        assertNull(userController.getUserById(0));
    }

    /**
     * Test read all User Home
     */
    @Test
    public void testGetUserListHome() {
        final Model model = new ExtendedModelMap();
        assertThat(userController.home(model), is("user/list"));
    }


    /**
     * Test Update a Home User
     */
    @Test
    public void testUpdateUser() {
        assertThat(userController.home(model), is("user/list"));
    }

    /**
     * Test Update a User is Present
     */
    @Test
    public void testUpdateUserWhenUserIsPresent() {
        when(userService.findById(ID)).thenReturn(java.util.Optional.ofNullable(user));
        assertThat(userController.showUpdateForm(ID, model), is("user/update"));
    }

    /**
     * Test Update a User then valid
     */
    @Test
    public void testUpdateUserThenValid() {
        User user = new User(ID, "usename", passwordValidTest, "fullname", "user");
        assertThat(userController.updateUser(ID, user, bindingResult, model), is("redirect:/user/list"));
    }

    /**
     * Test Update a User Password is not valid
     */
    @Test
    public void testUpdateUserThenPasswordNotValid() {
        User user = new User(ID, "usename", "125", "fullname", "user");
        boolean passwordValid = PasswordValidator.isValid(user.getPassword());
        when(!bindingResult.hasErrors() && passwordValid).thenReturn(true);
        assertThat(userController.updateUser(ID, user, bindingResult, model), is("redirect:/user/list"));
    }

    /**
     * Test IllegalArgument Update a User
     */
    @Test
    public void testIllegalArgumentUpdateUser() {
        int userId = -25;
        try {
            userController.showUpdateForm(userId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid user Id:" + userId));
        }
    }


    /**
     * Test Delete a User is Present
     */
    @Test
    public void testDeleteUserWhenUserIsPresent() {
        final Model model = new ExtendedModelMap();
        when(userService.createUser(user)).thenReturn(user);
        when(userService.findById(ID)).thenReturn(java.util.Optional.ofNullable(user));
        assertThat(userController.deleteUser(ID, model), is("redirect:/user/list"));
    }


    /**
     * Test IllegalArgument Delete a User
     */
    @Test
    public void testIllegalArgumentDeleteUser() {
        int userId = -25;
        try {
            userController.deleteUser(userId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid user Id:" + userId));
        }
    }
}
