package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
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
public class RatingControllerTest {

    private static final int ID = 1;

    @InjectMocks
    RatingController ratingController;

    @Mock
    RatingService ratingService;

    @Mock
    Rating rating;

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
     * Test Create a new Rating
     */
    @Test
    public void testCreateRating() {
        assertThat(ratingController.addRatingForm(rating), is("rating/add"));
    }

    /**
     * Test Create a Rating then valid
     */
    @Test
    public void testCreateRatingThenValid() {
        when(rating.getMoodysRating()).thenReturn("Moodys Rating");
        when(rating.getSandPRating()).thenReturn("Sand PRating");
        when(rating.getFitchRating()).thenReturn("Fitch Rating");
        when(rating.getOrderNumber()).thenReturn(2);
        assertThat(ratingController.validate(rating, bindingResult, model), is("redirect:/rating/list"));
    }

    /**
     * Test Create a Rating then not valid
     */
    @Test
    public void testCreateRatingThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ratingController.validate(rating, bindingResult, model), is("rating/add"));
    }


    /**
     * Test Read a Rating by Id
     */
    @Test
    public void testGetRatingById() {
        when(ratingService.findById(ID)).thenReturn(java.util.Optional.ofNullable(rating));
        assertEquals(ratingController.getRatingById(ID), rating);
    }

    /**
     * Test Read a Rating then not valid
     */
    @Test
    public void testGetRatingThenNotValid() {
        assertNull(ratingController.getRatingById(0));
    }


    /**
     * Test read all Rating Home
     */
    @Test
    public void testGetRatingHome() {
        final Model model = new ExtendedModelMap();
        assertThat(ratingController.home(model), is("rating/list"));
    }

    /**
     * Test show Update a Rating
     */
    @Test
    public void testShowUpdateRatingTest() {
        final Model model = new ExtendedModelMap();
        rating.setId(ID);
        when(ratingService.findById(ID)).thenReturn(java.util.Optional.ofNullable(rating));
        assertThat(ratingController.showUpdateForm(ID, model), is("rating/update"));
    }


    /**
     * Test Update a Rating is Present
     */
    @Test
    public void testUpdateRatingWhenRatingIsPresent() {
        final Model model = new ExtendedModelMap();
        rating.setId(ID);
        when(ratingService.findById(ID)).thenReturn(java.util.Optional.ofNullable(rating));
        assertThat(ratingController.showUpdateForm(ID, model), is("rating/update"));
    }

    /**
     * Test Update a Rating then valid
     */
    @Test
    public void testUpdateRatingThenValid() {
        when(rating.getMoodysRating()).thenReturn("Moodys Rating");
        when(rating.getSandPRating()).thenReturn("Sand PRating");
        when(rating.getFitchRating()).thenReturn("Fitch Rating");
        when(rating.getOrderNumber()).thenReturn(2);
        assertThat(ratingController.updateRating(ID, rating, bindingResult, model), is("redirect:/rating/list"));
    }

    /**
     * Test update a Rating then not valid
     */
    @Test
    public void testUpdateRatingThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ratingController.updateRating(ID, rating, bindingResult, model), is("rating/update"));
    }

    /**
     * Test Update a Rating then error
     */
    @Test
    public void testUpdateRatingThenValidError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ratingController.updateRating(ID, rating, bindingResult, model), is("rating/update"));
    }

    /**
     * Test IllegalArgument Update a Rating
     */
    @Test
    public void testIllegalArgumentUpdateRating() {
        int ratingId = -25;
        try {
            ratingController.showUpdateForm(ratingId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Rating Id:" + ratingId));
        }
    }


    /**
     * Test Delete a Rating is Present
     */
    @Test
    public void testDeleteRatingTestWhenRatingIsPresent() {
        final Model model = new ExtendedModelMap();
        when(ratingService.createRating(rating)).thenReturn(rating);
        when(ratingService.findById(ID)).thenReturn(java.util.Optional.ofNullable(rating));
        assertThat(ratingController.deleteRating(ID, model), is("redirect:/rating/list"));
    }

    /**
     * Test IllegalArgument Delete a Rating
     */
    @Test
    public void testIllegalArgumentDeleteRating() {
        int ratingId = -25;
        try {
            ratingController.deleteRating(ratingId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid rating Id:" + ratingId));
        }
    }
}
