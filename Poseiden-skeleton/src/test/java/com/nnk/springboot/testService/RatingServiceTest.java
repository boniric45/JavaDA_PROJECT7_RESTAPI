package com.nnk.springboot.testService;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingServiceTest {

    @InjectMocks
    RatingService ratingService;

    @Mock
    RatingRepository ratingRepository;

    /**
     * Test Create a new Rating
     */
    @Test
    @Order(1)
    public void testCreateRating() {
        // Given
        Rating rating = new Rating(1, "Moodys", "SandPRating", "FitchRating", 2);

        // When
        ratingService.createRating(rating);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // Then
        Rating savedRating = ratingRepository.save(rating);
        assertThat(savedRating.getFitchRating()).isNotNull();
    }

    /**
     * Test Read All Rating
     */
    @Test
    @Order(2)
    public void testGetAllRating() {
        // Given
        List<Rating> listRating = new ArrayList<>();
        Rating rating1 = new Rating(1, "Moodys1", "SandPRating1", "FitchRating1", 1);
        Rating rating2 = new Rating(2, "Moodys2", "SandPRating2", "FitchRating2", 2);
        Rating rating3 = new Rating(3, "Moodys3", "SandPRating3", "FitchRating3", 3);

        listRating.add(rating1);
        listRating.add(rating2);
        listRating.add(rating3);

        // When
        when(ratingService.findAll()).thenReturn(listRating);
        List<Rating> ratingList = ratingRepository.findAll();

        // Then
        assertEquals(3, ratingList.size());

    }

    /**
     * Test Read Rating by id
     */
    @Test
    @Order(3)
    public void testGetRatingById() {
        // Given
        when(ratingService.findById(1)).thenReturn(java.util.Optional.of(new Rating(1, "Moodys", "SandPRating", "FitchRating", 1)));

        // When
        Rating ratingResult = ratingService.findById(1).get();
        String ratingOrder = String.valueOf(ratingResult.getOrderNumber());

        // Then
        assertEquals("Moodys", ratingResult.getMoodysRating());
        assertEquals("SandPRating", ratingResult.getSandPRating());
        assertEquals("FitchRating", ratingResult.getFitchRating());
        assertEquals("1", ratingOrder);
    }

    /**
     * Test Update Rating
     */
    @Test
    @Order(4)
    public void testUpdateRating() {
        // Given
        Rating rating = new Rating(1, "Moodys", "SandPRating", "FitchRating", 1);

        given(ratingRepository.findById(rating.getId())).willReturn(Optional.of(rating));
        Rating ratingUpdated = new Rating(1, "MoodysUpdate", "SandPRatingUpdate", "FitchRatingUpdate", 10);

        // When
        ratingService.updateRating(ratingUpdated);

        // Then
        verify(ratingRepository).save(ratingUpdated);
    }

    /**
     * Test Delete Rating by ID
     */
    @Test
    @Order(5)
    public void testDeleteRatingById() {
        // Given
        Rating rating = new Rating(1, "Moodys", "SandPRating", "FitchRating", 1);

        // When
        when(ratingRepository.findById(rating.getId())).thenReturn(Optional.of(rating));
        ratingService.deleteRatingById(rating.getId());

        // Then
        verify(ratingRepository).deleteById(rating.getId());
    }

    /**
     * Test Rating exist in DB
     */
    @Test
    @Order(6)
    public void RatingExistInDbSucces() {
        // Given
        // Given
        Rating rating = new Rating(1, "Moodys", "SandPRating", "FitchRating", 1);

        List<Rating> ratingList = new ArrayList<>();

        // When
        ratingList.add(rating);
        when(ratingRepository.findAll()).thenReturn(ratingList);

        // Then
        List fetChedRating = ratingService.findAll();
        assertThat(fetChedRating.size()).isGreaterThan(0);
    }
}
