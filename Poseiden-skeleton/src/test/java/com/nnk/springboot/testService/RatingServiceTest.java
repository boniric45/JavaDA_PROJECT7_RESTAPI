package com.nnk.springboot.testService;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RatingServiceTest {

    private static final int id = 1;

    @InjectMocks
    RatingService ratingService;

    @Mock
    RatingRepository ratingRepository;

    /**
     * Test Create Rating
     */
    @Test
    public void testCreateRating() {
        Rating rating = mock(Rating.class);
        ratingService.createRating(rating);
        verify(ratingRepository).save(rating);
    }

    /**
     * Test Read Rating by id
     */
    @Test
    public void testReadRatingById() {
        ratingService.findById(id);
        verify(ratingRepository).findById(1);
    }

    /**
     * Test Read all Rating
     */
    @Test
    public void testReadAllRating() {
        ratingService.findAll();
        verify(ratingRepository).findAll();
    }

    /**
     * Test Update Rating
     */
    @Test
    public void testUpdateRating() {
        Rating rating = mock(Rating.class);
        when(rating.getId()).thenReturn(id);
        when(rating.getMoodysRating()).thenReturn("Test Moodys");
        when(rating.getSandPRating()).thenReturn("Test SandPRating");
        when(rating.getFitchRating()).thenReturn("Test FitchRating");
        when(rating.getOrderNumber()).thenReturn(3);
        when(ratingRepository.findById(id)).thenReturn(java.util.Optional.of(rating));
        ratingService.updateRating(rating);
        verify(ratingRepository).save(rating);
    }

    /**
     * Test Delete Rating by id
     */
    @Test
    public void testDeleteRatingById() {
        ratingService.deleteRatingById(1);
        verify(ratingRepository).deleteById(1);
    }
}
