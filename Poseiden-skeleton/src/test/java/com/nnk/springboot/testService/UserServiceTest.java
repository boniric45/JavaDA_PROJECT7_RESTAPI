package com.nnk.springboot.testService;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private static final long id = 1;
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    /**
     * Test Create User
     */
    @Test
    public void testCreateUser() {
        User user = mock(User.class);
        userService.createUser(user);
        verify(userRepository).save(user);
    }


    /**
     * Test Read User by id
     */
    @Test
    public void testReadUser() {
        userService.findById(1);
        verify(userRepository).findById(1);
    }

    /**
     * Test Read all User
     */
    @Test
    public void testReadAllUser() {
        userService.findAll();
        verify(userRepository).findAll();
    }


    /**
     * Test Update User
     */
    @Test
    public void testUpdateUser() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(user.getFullname()).thenReturn("Test Fullname");
        when(user.getUsername()).thenReturn("Test Username");
        when(user.getPassword()).thenReturn("123");
        when(user.getRole()).thenReturn("user");
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));
        userService.updateUser(user);

        verify(userRepository).save(user);
    }


    /**
     * Test Delete User by id
     */
    @Test
    public void testDeleteUserById() {
        userService.deleteUserById(1);
        verify(userRepository).deleteById(1);
    }

}

