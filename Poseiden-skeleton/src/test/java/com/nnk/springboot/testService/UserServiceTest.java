package com.nnk.springboot.testService;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    /**
     * Test Create a new User
     */
    @Test
    @Order(1)
    public void testCreateUser() {
        // Given
        User user = new User(2, "FullName", "password", "UserName", "Role");

        // When
        userService.createUser(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Then
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getUsername()).isNotNull();
    }

    /**
     * Test Read All User
     */
    @Test
    @Order(2)
    public void testGetAllUser() {
        // Given
        List<User> userList = new ArrayList<>();
        User user1 = new User(2, "FullName2", "password2", "UserName2", "Role2");
        User user2 = new User(3, "FullName3", "password3", "UserName3", "Role3");
        User user3 = new User(4, "FullName4", "password4", "UserName4", "Role4");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        // When
        when(userService.findAll()).thenReturn(userList);
        List<User> userListResult = userService.findAll();

        // Then
        assertEquals(3, userListResult.size());

    }

    /**
     * Test Read User by id
     */
    @Test
    @Order(3)
    public void testGetUserById() {
        // Given
        when(userService.findById(2)).thenReturn(java.util.Optional.of(new User(2, "UserName", "password", "FullName", "Role")));

        // When
        User userResult = userService.findById(2).get();

        // Then
        assertEquals("FullName", userResult.getFullname());
        assertEquals("password", userResult.getPassword());
        assertEquals("UserName", userResult.getUsername());
        assertEquals("Role", userResult.getRole());
    }

    /**
     * Test Update User
     */
    @Test
    @Order(4)
    public void testUpdateUser() {
        // Given
        User user = new User(2, "FullName", "password", "UserName", "Role");
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        User userUpdated = new User(2, "FullName", "password", "UserName", "Role");

        // When
        userService.updateUser(userUpdated);

        // Then
        verify(userRepository).save(userUpdated);
    }

    /**
     * Test Delete User by ID
     */
    @Test
    @Order(5)
    public void testDeleteUserById() {
        // Given
        User user = new User(2, "FullName", "password", "UserName", "Role");

        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUserById(user.getId());

        // Then
        verify(userRepository).deleteById(user.getId());
    }

    /**
     * Test User exist in DB
     */
    @Test
    @Order(6)
    public void userExistInDbSucces() {
        // Given
        User user = new User(2, "FullName", "password", "UserName", "Role");
        List<User> userList = new ArrayList<>();


        // When
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        // Then
        List fetChedUser = userService.findAll();
        assertThat(fetChedUser.size()).isGreaterThan(0);
    }

}

