package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);
    @Autowired
    RatingRepository ratingRepository;

    public Rating createRating(Rating rating) {
        LOGGER.info("Creating rating list");
        return ratingRepository.save(rating);
    }

    public Optional<Rating> findById(Integer id) {
        LOGGER.info("Getting rating identified by id");
        return ratingRepository.findById(id);
    }

    public List findAll() {
        LOGGER.info("Read rating list");
        return ratingRepository.findAll();
    }

    public Rating updateRating(Rating rating) {
        LOGGER.info("Updating rating list");
        return ratingRepository.save(rating);
    }

    public void deleteRatingById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
