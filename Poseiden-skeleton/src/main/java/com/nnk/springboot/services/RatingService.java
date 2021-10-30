package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    /**
     * Create Rating
     */
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Read Rating by id
     */
    public Optional<Rating> findById(Integer id) {
        return ratingRepository.findById(id);
    }

    /**
     * Read all Rating
     */
    public List findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Update Rating
     */
    public Rating updateRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Delete Rating by id
     */
    public void deleteRatingById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
