package com.nnk.springboot.service.rating_service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * RatingServiceImpl: CRUD
 * @author Subhi
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    private final RatingRepository ratingRepository;
    private final MapperService mapperService;

    public RatingServiceImpl(RatingRepository ratingRepository, MapperService mapperService) {
        this.ratingRepository = ratingRepository;
        this.mapperService = mapperService;
    }

    /**
     * Get the all Rating from DDB and transfer to RatingDTO
     * @return List of RatingDTO
     */
    @Override
    public List<RatingDTO> getRatings() {
        logger.debug("This getRatings(from RatingServiceImpl) starts here.");
        List<RatingDTO> ratingDTOs;
        List<Rating> ratings = ratingRepository.findAll();

        if(ratings.isEmpty()){
            logger.info("Ratings is empty in DDB!(from getRatings, RatingServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("Ratings successfully loaded from DDB(from getRatings, RatingServiceImpl).");
        ratingDTOs = ratings.stream().map(rating ->
                mapperService.fromRating(rating)
        ).collect(Collectors.toList());
        return ratingDTOs;
    }
    /**
     * Find Rating by its Id and return RatingDTO
     * @param id Integer
     * @return RatingDTO
     */
    @Override
    public RatingDTO getRatingById(Integer id) {
        logger.debug("This getRatingByRatingId(from RatingServiceImpl) starts here.");
        Rating rating = getRatingByRatingId(id);

        logger.info("Rating successfully found by its id: {} (from getRatingById, RatingServiceImpl).", id);
        return mapperService.fromRating(rating);
    }
    /**
     * Save a new Rating via RatingDTO
     * @param ratingDTO RatingDTO
     * @return RatingDTO
     */
    @Override
    public RatingDTO saveNewRating(RatingDTO ratingDTO) {
        logger.debug("This saveNewRating(from RatingServiceImpl) starts here.");
        Rating rating = mapperService.fromRatingDTO(ratingDTO);
        Rating savedRating = ratingRepository.save(rating);
        logger.info("New Rating successfully saved into DDB(from saveNewRating, RatingServiceImpl).");
        RatingDTO returnRatingDTO = mapperService.fromRating(savedRating);

        return returnRatingDTO;
    }
    /**
     * Update a Rating if its id exists in DDB
     * @param ratingDTO RatingDTO
     */
    @Override
    public void updateRating(RatingDTO ratingDTO) {
        logger.debug("This updateRating method(from RatingServiceImpl) starts here.");
        Rating updateRating = mapperService.fromRatingDTO(ratingDTO);
        updateRating.setId(ratingDTO.getRatingID());

        logger.info("Rating which id : {} successfully updated(from updateRating, RatingServiceImpl).", ratingDTO.getRatingID());
       ratingRepository.save(updateRating);
    }
    /**
     * Delete a Rating existing in DDB by RatingId
     * @param id id
     */
    @Override
    public void deleteRatingById(Integer id) {
        logger.debug("This deleteRatingById(from RatingServiceImpl starts here.) ");
        Rating ratingGetRatingId = getRatingByRatingId(id);
        if(ratingGetRatingId != null) {
            logger.info("Rating which id :{} successfully deleted from DDB(from RatingServiceImpl)", id);
            ratingRepository.deleteById(id);
        }
    }
    /**
     * Get Rating from DDB with its Id and throw ResourceNotFoundException if Rating is not present.
     * @param id Integer
     * @return rating Rating
     */
    private Rating getRatingByRatingId(Integer id) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> {
            logger.error("This ratingId:{} not found!", id);
            throw new ResourcesNotFoundException("This Rating doesn't exist with this id : " + id + " , from getRatingByRatingId, RatingServiceImpl.");
        });
        return rating;
    }
}
