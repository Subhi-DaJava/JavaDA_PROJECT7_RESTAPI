package com.nnk.springboot.service.rating_service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {
    @InjectMocks
    private RatingServiceImpl ratingService;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private MapperService mapperService;
    @Test
    void getRatings() {
        RatingDTO ratingDTO1 = new RatingDTO();
        RatingDTO ratingDTO2 = new RatingDTO();


        Rating rating1 = new Rating(5, "moodsRating1", "sandPRating1", "fitchRating1", 123);
        Rating rating2 = new Rating(6, "moodsRating2", "sandPRating2", "fitchRating2", 321);

        List<Rating> ratings = new ArrayList<>(List.of(rating1, rating2));

        when(mapperService.fromRating(rating1)).thenReturn(ratingDTO1);
        when(mapperService.fromRating(rating2)).thenReturn(ratingDTO2);

       ratingDTO1.setRatingID(rating1.getId());
       ratingDTO1.setMoodysRating(rating1.getMoodysRating());
       ratingDTO1.setSandPRating(rating1.getSandPRating());
       ratingDTO1.setFitchRating(rating1.getFitchRating());
       ratingDTO1.setOrderNumber(rating1.getOrderNumber());

       ratingDTO2.setRatingID(rating2.getId());
       ratingDTO2.setMoodysRating(rating2.getMoodysRating());
       ratingDTO2.setSandPRating(rating2.getSandPRating());
       ratingDTO2.setFitchRating(rating2.getFitchRating());
       ratingDTO2.setOrderNumber(rating2.getOrderNumber());

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<RatingDTO> ratingDTOList = ratingService.getRatings();

        assertThat(ratingDTOList.size()).isEqualTo(2);
        assertThat(ratingDTOList.get(0).getOrderNumber()).isEqualTo(123);
        assertThat(ratingDTOList.get(1).getOrderNumber()).isEqualTo(321);
        assertThat(ratingDTOList).isNotNull();
    }

    @Test
    void getEmptyRatingList() {
        List<Rating> ratings = new ArrayList<>();

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<RatingDTO> ratingDTOList = ratingService.getRatings();

        assertThat(ratingDTOList.size()).isEqualTo(0);
    }
    @Test
    void getRatingById() {
        RatingDTO ratingDTO= new RatingDTO();
        Rating rating = new Rating(8, "moodsRating", "sandPRating", "fitchRating", 452);

        //this mock will map any Rating to RatingDTO
        when(mapperService.fromRating(any())).thenReturn(ratingDTO);
        ratingDTO.setRatingID(rating.getId());
        ratingDTO.setOrderNumber(452);
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        RatingDTO ratingDTOGetById = ratingService.getRatingById(rating.getId());
        assertThat(ratingDTOGetById.getRatingID()).isEqualTo(8);
        assertThat(ratingDTOGetById.getOrderNumber()).isEqualTo(rating.getOrderNumber());
    }

    @Test
    void getRatingByNotExistingId() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ratingService.getRatingById(anyInt()));
    }

    @Test
    void saveNewRating() {
        RatingDTO ratingDTO= new RatingDTO();
        Rating rating = new Rating( "moodsRating", "sandPRating", "fitchRating", 522);

        //this mock will map any Rating to RatingDTO
        when(mapperService.fromRating(any())).thenReturn(ratingDTO);

        ratingDTO.setMoodysRating(rating.getMoodysRating());
        ratingDTO.setSandPRating(rating.getSandPRating());
        ratingDTO.setFitchRating(rating.getFitchRating());
        ratingDTO.setOrderNumber(rating.getOrderNumber());

        when(mapperService.fromRatingDTO(any())).thenReturn(rating);
        when(ratingRepository.save(any())).thenReturn(rating);

        RatingDTO ratingDTOSaved = ratingService.saveNewRating(ratingDTO);

        assertThat(ratingDTOSaved.getOrderNumber()).isEqualTo(rating.getOrderNumber());
        verify(ratingRepository, times(1)).save(any());
        verify(mapperService, times(1)).fromRating(any());
        verify(mapperService, times(1)).fromRatingDTO(any());
    }

    @Test
    void updateRating() {
        Rating rating;
        RatingDTO ratingDTO = new RatingDTO();

        rating = new Rating( 23,"moodsRating", "sandPRating", "fitchRating", 522);

        when(ratingRepository.save(any())).thenReturn(rating);
        when(mapperService.fromRatingDTO(any())).thenReturn(rating);

        ratingDTO.setSandPRating("sand_p_rating");
        ratingDTO.setOrderNumber(235);

        rating.setOrderNumber(235);
        rating.setSandPRating("sand_p_rating");

       ratingService.updateRating(ratingDTO);

        assertThat(rating.getOrderNumber()).isEqualTo(ratingDTO.getOrderNumber());
        assertThat(rating.getSandPRating()).isEqualTo(ratingDTO.getSandPRating());
    }

    @Test
    void deleteRatingById() {
        Rating rating = new Rating( 89,"moodsRating", "sandPRating", "fitchRating", 522);
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));
        doNothing().when(ratingRepository).deleteById(anyInt());
        ratingService.deleteRatingById(anyInt());
        verify(ratingRepository,times(1)).deleteById(anyInt());
        verify(ratingRepository, times(1)).findById(anyInt());
        assertThat(ratingService.getRatingById(rating.getId())).isNull();
    }
}