package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.trade_service.TradeService;
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
 * TradeController send requests to the backend, getAllTrades, saveNewTrade, getTradeById, updateTradeExisting, deleteTradeExisting
 * @author Subhi
 */
@Controller
@RequestMapping("/api")
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }
    /**
     * Page list show all Trade List
     * @param model Model
     * @return TradeList page
     */
    @GetMapping("/trade/list")
    public String home(Model model) {
        logger.debug("This home(from TradeController) starts here.");
        model.addAttribute("tradeList", tradeService.getTrades());
        logger.info("Trades home page successfully loaded(from home, TradeController).");
        return "trade/list";
    }
    /**
     * Show Trade addForm with the object TradeDTO
     * @return TradeAdd Page
     */
    @GetMapping("/trade/add")
    public String addTradeForm(TradeDTO tradeDTO) { // object tradeDTO interact with add.html
        logger.debug("This addTradeForm(from TradeController) starts here.");
        return "trade/add";
    }
    /**
     * Sava a Trade
     * @param tradeDTO TradeDTO
     * @param result BindingResult
     * @return TradeList Page
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDTO tradeDTO, BindingResult result, Model model) {
        logger.debug("This validatePostMapping(from TradeController) starts here.");
        if(!result.hasErrors()){
            tradeService.saveNewTrade(tradeDTO);
            logger.info("New Trade successfully saved in DDB(from validatePostMapping, TradeController)");
            return "redirect:/api/trade/list";
        }
        logger.error("result error= {}, (from validePostMapping, TradeController)", result.getFieldErrors());
        return "trade/add";
    }
    /**
     * Show Trade updateForm
     * @param id Integer
     * @param model Model
     * @return TradeUpdate Page with TradeDTO
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from TradeController) starts here.");
        TradeDTO tradeDTO = tradeService.getTradeById(id);
        model.addAttribute("tradeDTO", tradeDTO);
        logger.info("UpdateTrade page successfully loaded(from showUpdateForm, TradeController).");
        return "trade/update";
    }
    /**
     * Update a Trade by id, checking the fields before call to service
     * @param id Integer
     * @param tradeDTO TradeDTO
     * @param result BindingResult
     * @return TradeList Page
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDTO tradeDTO,
                             BindingResult result, Model model) {
        logger.debug("This updateTrade method(from TradeController) starts here.");
        if(result.hasErrors()){
            logger.error("The field error= {} should be valid!", result.getFieldErrors());
            return "trade/update";
        }
        tradeDTO.setId(id);
        tradeService.updateTrade(tradeDTO);
        logger.info("Trade with id: {} is successfully updated(from, updateTrade Post Mapping, TradeController)", id);
        return "redirect:/api/trade/list";
    }
    /**
     * Delete Trade by given TradeId
     * @param id Integer
     * @return TradeList Page
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteTrade(from TradeController) starts here.");
        tradeService.deleteTradeById(id);
        logger.info("Trade successfully deleted by given Id: {}, from TradeController.", id);
        return "redirect:/api/trade/list";
    }
}
