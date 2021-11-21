package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserEntityRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    // Junit test for Save User
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createUserTest() {
        // Given
        User user = User.builder()
                .id(2)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        // When
        userRepository.save(user);

        // Then
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    // Junit test for Read User
    @Test
    @Order(3)
    public void getUserTest() {
        // Given
        User user = User.builder()
                .id(2)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        // When
        userRepository.save(user);

        User userResult = userRepository.findById(2).get();

        // Then
        assertThat(userResult.getId()).isEqualTo(2);
    }

    // Junit test for Read All User
    @Test
    @Order(4)
    public void getListOfUserTest() {
        // Given
        User user = User.builder()
                .id(2)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        // When
        userRepository.save(user);

        List<User> listUser = userRepository.findAll();

        // Then
        Assertions.assertThat(listUser.size()).isGreaterThan(0);
    }

    // Junit test for Update User
    @Test
    @Order(5)
    public void updateUserTest() {
        // Given
        User user = User.builder()
                .id(1)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        // When
        userRepository.save(user);

        User userResult = userRepository.findById(1).get();
        userResult.setFullname("Update FullName");
        userResult.setUsername("Update Username");
        userResult.setRole("Update Role");

        User userUpdate = userRepository.save(userResult);

        // Then
        assertThat(userUpdate.getFullname()).isEqualTo("Update FullName");
        assertThat(userUpdate.getUsername()).isEqualTo("Update Username");
        assertThat(userUpdate.getRole()).isEqualTo("Update Role");
    }

    // Junit test for delete User
    @Test
    @Order(6)
    public void deleteUserTest() {
        // Given
        User user = User.builder()
                .id(2)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        User user2 = null;

        // When
        userRepository.save(user);
        userRepository.delete(user);


        Optional<User> optionalUser = userRepository.findById(2);
        if (optionalUser.isPresent()) {
            user2 = optionalUser.get();
        }

        // Then
        Assertions.assertThat(user2).isNull();

    }

    // Junit test for delete User by Id
    @Test
    @Order(7)
    public void deleteUserByIdTest() {
        // Given
        User user = User.builder()
                .id(1)
                .fullname("Test FullName")
                .username("Test Username")
                .role("Test Role")
                .build();

        User user2 = null;

        // When
        userRepository.save(user);
        userRepository.deleteById(1);


        Optional<User> optionalUser = userRepository.findById(1);
        if (optionalUser.isPresent()) {
            user2 = optionalUser.get();
        }

        // Then
        Assertions.assertThat(user2).isNull();

    }

}
