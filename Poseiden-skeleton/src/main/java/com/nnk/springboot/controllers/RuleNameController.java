package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.service.rule_name_service.RuleNameService;
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
 * RuleNameController send requests to the backend, getAllRuleNames, saveNewRuleName, getRuleNameById, updateRuleNameExisting, deleteRuleNameExisting
 * @author Subhi
 */
@Controller
public class RuleNameController {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);
    private RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
    /**
     * Page list show all RuleName List
     * @param model Model
     * @return RuleNameList page
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        logger.debug("This home(from RuleNameController) starts here.");
        model.addAttribute("ruleNameList", ruleNameService.getRuleNames());
        logger.info("RuleNames home page successfully loaded(from home, RuleNameController).");
        return "ruleName/list";
    }
    /**
     * Show RuleName addForm with the object RuleNameDTO
     * @return RuleNameAdd Page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameDTO ruleNameDTO) { // object ruleNameDTO interact with add.html
        logger.debug("This addRuleForm(from RuleNameController) starts here.");
        return "ruleName/add";
    }
    /**
     * Sava a RuleName
     * @param ruleNameDTO RuleNameDTO
     * @param result BindingResult
     * @return RuleNameList Page
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDTO ruleNameDTO, BindingResult result, Model model) {
        logger.debug("This validatePostMapping(from RuleNameController) starts here.");
        if(!result.hasErrors()){
            ruleNameService.saveNewRuleName(ruleNameDTO);
            logger.info("New RuleName successfully saved in DDB(from validatePostMapping, RuleNameController)");
            return "redirect:/ruleName/list";
        }
        logger.error("result error = {}, (from validePostMapping, RuleNameController)", result.getFieldErrors());
        return "ruleName/add";
    }
    /**
     * Show RuleName updateForm
     * @param id Integer
     * @param model Model
     * @return RuleNameUpdate Page with RuleNameDTO
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from RuleNameController) starts here.");
        RuleNameDTO ruleNameDTO = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleNameDTO", ruleNameDTO);
        logger.info("UpdateRuleName page successfully loaded(from showUpdateForm, RuleNameController).");
        return "ruleName/update";
    }
    /**
     * Update a RuleName by id, checking the fields before call to service
     * @param id Integer
     * @param ruleNameDTO RuleNameDTO
     * @param result BindingResult
     * @return RuleNameList Page
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDTO ruleNameDTO,
                             BindingResult result, Model model) {
        logger.debug("This updateRuleName method(from RuleNameController) starts here.");
        if(result.hasErrors()){
            logger.error("The field error= {} should be valid!", result.getFieldErrors());
            return "ruleName/update";
        }
        ruleNameDTO.setRuleNameId(id);
        ruleNameService.updateRuleName(ruleNameDTO);
        logger.info("RuleName with id: {} is successfully updated(from, updateRuleName Post Mapping, RuleNameController)", id);
        return "redirect:/ruleName/list";
    }
    /**
     * Delete RuleName by given RuleNameId
     * @param id Integer
     * @return RuleNameList Page
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteRuleName(from RuleNameController) starts here.");
        ruleNameService.deleteRuleNameById(id);
        logger.info("RuleName successfully deleted by given Id: {}, from RuleNameController.", id);
        return "redirect:/ruleName/list";
    }
}
