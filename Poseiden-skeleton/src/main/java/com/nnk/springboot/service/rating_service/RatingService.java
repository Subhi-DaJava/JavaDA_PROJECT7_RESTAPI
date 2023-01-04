package com.nnk.springboot.service.rating_service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;

import java.util.List;
/**
 * CurvePointService allows to insert the business logic
 * in the Curve business domain.
 * @author Subhi
 */
public interface RatingService {
    List<RatingDTO> getRatings();
    RatingDTO getRatingById(Integer id);
    RatingDTO saveNewRating(RatingDTO ratingDTO);
    void updateRating(RatingDTO ratingDTO);
    void deleteRatingById(Integer id);
}
