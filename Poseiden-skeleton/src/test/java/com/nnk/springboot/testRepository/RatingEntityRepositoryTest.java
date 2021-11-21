package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
public class RatingEntityRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RatingRepository ratingRepository;


    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(ratingRepository).isNotNull();
    }

    // Junit test for Save Rating
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createRatingTest() {
        // Given
        Rating rating = new Rating(2, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);

        // When
        ratingRepository.save(rating);


        // Then
        Assertions.assertThat(rating.getId()).isGreaterThan(0);
    }

    // Junit test for Read Rating
    @Test
    @Order(3)
    public void getRatingTest() {
        // Given
        Rating rating = new Rating(3, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);

        // When
        ratingRepository.save(rating);
        Rating ratingResult = ratingRepository.getOne(rating.getId());

        // Then
         assertThat(ratingResult.getId()).isEqualTo(3);
    }

    // Junit test for Read All Rating
    @Test
    @Order(4)
    public void getListOfRatingTest() {

        // Given
        Rating rating = new Rating(4, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);

        // When
        ratingRepository.save(rating);
        List<Rating> listRating = ratingRepository.findAll();

        // Then
        Assertions.assertThat(listRating.size()).isGreaterThan(0);
    }

    // Junit test for Update Rating
    @Test
    @Order(5)
    public void updateRatingTest() {
        // Given
        Rating rating = new Rating(5, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);

        // When
        ratingRepository.save(rating);
        Rating ratingResult = new Rating();

        Optional<Rating> optionalRating = ratingRepository.findById(5);
        if (optionalRating.isPresent()) {
            ratingResult = optionalRating.get();
        }


        ratingResult.setMoodysRating("Update Moodys");
        ratingResult.setSandPRating("Update SandPRating");
        ratingResult.setFitchRating("Update FitchRating");
        ratingResult.setOrderNumber(12);

        Rating ratingUpdated = ratingRepository.save(ratingResult);

        // Then
        assertThat(ratingUpdated.getMoodysRating()).isEqualTo("Update Moodys");
        assertThat(ratingUpdated.getSandPRating()).isEqualTo("Update SandPRating");
        assertThat(ratingUpdated.getFitchRating()).isEqualTo("Update FitchRating");
        assertThat(ratingUpdated.getOrderNumber()).isEqualTo(12);
    }

    // Junit test for delete Rating
    @Test
    @Order(6)
    public void deleteRatingTest() {
        // Given
        Rating rating = new Rating(6, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);
        Rating ratingResult = null;

        // When
        ratingRepository.save(rating);
        ratingRepository.delete(rating);

        Optional<Rating> optionalRating = ratingRepository.findById(6);
        if (optionalRating.isPresent()) {
            ratingResult = optionalRating.get();
        }

        // Then
        Assertions.assertThat(ratingResult).isNull();

    }

    // Junit test for delete Rating by Id
    @Test
    @Order(7)
    public void deleteRatingByIdTest() {
        // Given
        Rating rating = new Rating(7, "Test Moodys", "Test SandPRating", "Test FitchRating", 10);
        Rating ratingResult = null;

        // When
        ratingRepository.save(rating);
        ratingRepository.deleteById(7);

        Optional<Rating> optionalRating = ratingRepository.findById(7);
        if (optionalRating.isPresent()) {
            ratingResult = optionalRating.get();
        }

        // Then
        Assertions.assertThat(ratingResult).isNull();
    }

}
