package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.utils.DigitalFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RatingController {
    // TODO: Inject Rating service
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    RatingService ratingService;

    @Autowired
    RatingRepository ratingRepository;


    /**
     * Create - Add a new rating
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validate rating
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(rating.getOrderNumber())) {
            logger.info(" SUCCESS POST /rating/validate");
            ratingService.createRating(rating);
            model.addAttribute("rating", ratingService.findAll());
            return "redirect:/rating/list";
        } else {
            rating.setOrderNumber(0);
            logger.error(" ERROR POST /rating/validate");
        }

        return "rating/add";
    }

    /**
     * Read all Rating
     *
     * @return model
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        model.addAttribute("rating", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Read - Get one rating
     *
     * @param id The id of the rating
     * @return An rating object full filled
     */
    @GetMapping("/rating/{id}")
    public Rating getRatingById(@PathVariable("id") int id) {

        Optional<Rating> ratingOptional = ratingService.findById(id);

        if (ratingOptional.isPresent()) {
            logger.info(" SUCCESS READ /rating/" + id);
            return ratingOptional.get();
        } else {
            logger.error(" ERROR READ /rating/" + id);
            return null;
        }
    }

    /**
     * Update rating
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form

        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
        model.addAttribute("rating", rating);
        logger.info(" SUCCESS GET /rating/update/" + id);

        return "rating/update";
    }

    /**
     * Update Post validate rating
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(rating.getOrderNumber())) {
            rating.setId(id);
            ratingService.createRating(rating);
            model.addAttribute("rating", ratingService.findAll());
            logger.info(" SUCCESS POST /rating/update/" + id);
            return "redirect:/rating/list";

        } else {
            logger.error(" ERROR POST /rating/update/" + id);
            rating.setOrderNumber(0);
            return "rating/update";
        }

    }

    /**
     * Delete rating
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
                Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
            ratingService.deleteRatingById(rating.getId());
            model.addAttribute("rating", ratingService.findAll());
            logger.info(" SUCCESS DELETE /rating/delete/" + id);
        return "redirect:/rating/list";
    }
}
