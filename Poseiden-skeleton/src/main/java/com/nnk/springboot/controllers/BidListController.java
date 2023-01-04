package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.bid_service.BidListService;
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
 * BidListController send requests to backend, getAllBidList, saveNewBidList, getBidListById, updateBidListExisting, deleteBidListExisting
 * @author Subhi
 */

@Controller
public class BidListController {
    private static final Logger logger = LoggerFactory.getLogger(BidListController.class);
   private BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }
    /**
     * Page list show all BidList
     * @param model Model
     * @return BidList page
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.debug("This home(from BidListController) starts here.");
        model.addAttribute("bidList", bidListService.getBidList());
        logger.info("BidList home page successfully loaded(from home, BidListController).");
        return "bidList/list";
    }
    /**
     * Show BidList addForm with the object BidListDTO
     * @return BidListAdd Page
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidListDTO bidListDTO) { // object BidListDTO interact with add.html
        logger.debug("This addBidForm(from BidListController) starts here.");
        return "bidList/add";
    }
    /**
     * Sava a BidList
     * @param bidList
     * @param result BindingResult
     * @return BidList Page
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListDTO bidList, BindingResult result, Model model) {
        logger.debug("This validatePostMapping(from BidListController) starts here.");

        if(!result.hasErrors()){
            bidListService.saveNewBidList(bidList);
            logger.info("New BidList successfully saved in DDB(from validatePostMapping, BidListController)");
            return "redirect:/bidList/list";
        }
        logger.error("result error= {}, (from validePostMapping, BidListController)", result.getFieldErrors());
        return "bidList/add";
    }
    /**
     * Show updateForm
     * @param id Integer
     * @param model Model
     * @return BidListUpdate Page with BidListDTO
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from BidListController) starts here.");
        BidListDTO bidListDTO = bidListService.getBidListById(id);
        model.addAttribute("bidListDTO", bidListDTO);
        logger.info("UpdateBidList page successfully loaded(from showUpdateForm, BidListController).");
        return "bidList/update";
    }
    /**
     * Update a BidList by id, checking the fields before call to service
     * @param id Integer
     * @param bidListDTO BidListDTO
     * @param result BindingResult
     * @return BidList Page
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDTO bidListDTO,
                             BindingResult result) {
        logger.debug("This updateBid method(from BidListController) starts here.");
        if(result.hasErrors()){
            logger.error("The field error= {} should be valid!", result.getFieldErrors());
            return "bidList/update";
        }
        bidListDTO.setBidId(id);
        bidListService.updateBidList(bidListDTO);
        logger.info("BidList with id: {} is successfully updated(from, updatePostMapping, BidListController)", id);
        return "redirect:/bidList/list";
    }

    /**
     * Delete BidList by given BidListId
     * @param id Integer
     * @return BidList Page
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteBid(from BidListController) starts here.");
        bidListService.deleteBidListById(id);
        logger.info("BidList successfully deleted by given Id: {}, from BidListController.", id);
        return "redirect:/bidList/list";
    }
}
