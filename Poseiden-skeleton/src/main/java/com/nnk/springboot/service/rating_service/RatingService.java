package com.nnk.springboot.service.rating_service;

import com.nnk.springboot.domain.Rating;

import java.util.List;
/**
 * CurvePointService allows to insert the business logic
 * in the Curve business domain.
 * @author Subhi
 */
public interface RatingService {
    List<Rating> getRatings();
    Rating getRatingById(Integer id);
    Rating saveNewRating(Rating rating);
    void updateRating(Rating rating);
    void deleteRatingById(Integer id);
}
