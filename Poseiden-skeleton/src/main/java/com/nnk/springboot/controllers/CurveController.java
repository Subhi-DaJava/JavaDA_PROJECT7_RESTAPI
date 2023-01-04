package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.curve_point_service.CurvePointService;
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
 * CurvePointController send requests to backend, getAllCurvePoints, saveNewCurvePoint, getCurvePointById, updateCurvePontExisting, deleteCurvePointExisting
 * @author Subhi
 */
@Controller
public class CurveController {
    private static final Logger logger = LoggerFactory.getLogger(CurveController.class);
    private CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }
    /**
     * Page list show all CurvePoint List
     * @param model Model
     * @return CurvePointList page
     */

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.debug("This home(from CurveController) starts here.");
        model.addAttribute("curvePointList", curvePointService.getCurvePoints());
        logger.info("CurvePoint home page successfully loaded(from home, CurveController).");
        return "curvePoint/list";
    }
    /**
     * Show CuvePoint addForm with the object CurvePointDTO
     * @return CurvePointAdd Page
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePointDTO curvePointDTO) {  // object CurvePointDTO interact with add.html
        logger.debug("This addCurveForm(from CurveController) starts here.");
        return "curvePoint/add";
    }
    /**
     * Sava a CurvePoint
     * @param curvePoint CurvePointDTO
     * @param result BindingResult
     * @return CurvePointList Page
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDTO curvePoint, BindingResult result, Model model) {
        logger.debug("This validatePostMapping(from CurveController) starts here.");
        if(!result.hasErrors()){
            curvePointService.saveNewCurvePoint(curvePoint);
            logger.info("New CurvePoint successfully saved in DDB(from validatePostMapping, CurveController)");
            return "redirect:/curvePoint/list";
        }
        logger.error("result error= {}, (from validePostMapping, CurveController)", result.getFieldErrors());
        return "curvePoint/add";
    }
    /**
     * Show CurvePoint updateForm
     * @param id Integer
     * @param model Model
     * @return CurvePointUpdate Page with CurvePointDTO
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from CurveController) starts here.");
        CurvePointDTO curvePointDTO = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePointDTO", curvePointDTO);
        logger.info("UpdateCurvePoint page successfully loaded(from showUpdateForm, CurveController).");
        return "curvePoint/update";
    }
    /**
     * Update a CurvePoint by id, checking the fields before call to service
     * @param id Integer
     * @param curvePointDTO CurvePointDTO
     * @param result BindingResult
     * @return CurvePointList Page
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePointDTO curvePointDTO,
                             BindingResult result, Model model) {
        logger.debug("This updateCurvePoint method(from CurveController) starts here.");
        if(result.hasErrors()){
            logger.error("The field error= {} should be valid!", result.getFieldErrors());
            return "curvePoint/update";
        }
        curvePointDTO.setCurveid(id);
        curvePointService.updateCurvePoint(curvePointDTO);
        logger.info("CurvePoint with id: {} is successfully updated(from, updateCurvePoint Post Mapping, CurveController)", id);
        return "redirect:/curvePoint/list";
    }
    /**
     * Delete CurvePoint by given CurvePointId
     * @param id Integer
     * @return CurvePointList Page
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteCurvePoint(from CurveController) starts here.");
        curvePointService.deleteCurvePointById(id);
        logger.info("CurvePoint successfully deleted by given Id: {}, from CurveController.", id);
        return "redirect:/curvePoint/list";
    }
}
