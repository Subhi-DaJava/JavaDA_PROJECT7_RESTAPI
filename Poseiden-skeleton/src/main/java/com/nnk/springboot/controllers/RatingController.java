package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.rating_service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
/**
 * RatingController send requests to the backend, getAllRatings, saveNewRating, getRatingByRatingId, updateRatingExisting, deleteRatingExisting
 * @author Subhi
 */
@Controller
@RequestMapping("/api")
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);
    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    /**
     * Page list show all Rating List
     * @param model Model
     * @return RatingList page
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.debug("This home(from RatingController) starts here.");
        model.addAttribute("ratingList", ratingService.getRatings());
        logger.info("Rating home page successfully loaded(from home, RatingController).");
        return "rating/list";
    }
    /**
     * Show Rating addForm with the object RatingDTO
     * @return RatingAdd Page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(RatingDTO ratingDTO) { // object ratingDTO interact with add.html
        logger.debug("This addRatingForm(from RatingController) starts here.");
        return "rating/add";
    }
    /**
     * Sava a Rating
     * @param ratingDTO RatingPointDTO
     * @param result BindingResult
     * @return RatingList Page
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDTO ratingDTO, BindingResult result, Model model) {
        logger.debug("This validatePostMapping(from RatingController) starts here.");
        if(!result.hasErrors()){
            ratingService.saveNewRating(ratingDTO);
            logger.info("New Rating successfully saved in DDB(from validatePostMapping, RatingController)");
            return "redirect:/api/rating/list";
        }
        logger.error("result error = {}, (from validePostMapping, RatingController)", result.getFieldErrors());
        return "rating/add";

        // other option
        /*logger.debug("This validateAddRating(from RatingController) starts here.");
        if(result.hasErrors()){
            logger.error("The result error ={}, from validatePostMapping, RatingController.", result.getFieldErrors());
            return "rating/add";
        }
        ratingService.saveNewRating(ratingDTO);
        logger.info("Rating successfully saved in DDB(from validatePostMapping, RatingController).");
        return "redirect:/rating/list";*/
    }
    /**
     * Show Rating updateForm
     * @param id Integer
     * @param model Model
     * @return RatingUpdate Page with RatingDTO
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from RatingController) starts here.");
        RatingDTO ratingDTO = ratingService.getRatingById(id);
        model.addAttribute("ratingDTO", ratingDTO);
        logger.info("UpdateRating page successfully loaded(from showUpdateForm, RatingController).");
        return "rating/update";
    }
    /**
     * Update a Rating by id, checking the fields before call to service
     * @param id Integer
     * @param ratingDTO RatingDTO
     * @param result BindingResult
     * @return RatingList Page
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDTO ratingDTO,
                             BindingResult result, Model model) {
        logger.debug("This updateRating method(from RatingController) starts here.");
        if(result.hasErrors()){
            logger.error("The field error= {} should be valid!", result.getFieldErrors());
            return "rating/update";
        }
        ratingDTO.setRatingID(id);
        ratingService.updateRating(ratingDTO);
        logger.info("Rating with id: {} is successfully updated(from, updateRating Post Mapping, RatingController)", id);
        return "redirect:/api/rating/list";
    }
    /**
     * Delete Rating by given RatingId
     * @param id Integer
     * @return RatingList Page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteRating(from RatingController) starts here.");
        ratingService.deleteRatingById(id);
        logger.info("Rating successfully deleted by given Id: {}, from RatingController.", id);
        return "redirect:/api/rating/list";
    }
}
