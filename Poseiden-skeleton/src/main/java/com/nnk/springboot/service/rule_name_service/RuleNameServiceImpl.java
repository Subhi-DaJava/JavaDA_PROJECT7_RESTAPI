package com.nnk.springboot.service.rule_name_service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RuleNameServiceImpl: CRUD
 * @author Subhi
 */
@Service
@Transactional
public class RuleNameServiceImpl implements RuleNameService {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameServiceImpl.class);
    private final RuleNameRepository ruleNameRepository;
    private final MapperService mapperService;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository, MapperService mapperService) {
        this.ruleNameRepository = ruleNameRepository;
        this.mapperService = mapperService;
    }

    /**
     * Get the all RuleName from DDB and transfer to RuleNameDTO
     * @return List of RuleNameDTO
     */
    @Override
    public List<RuleNameDTO> getRuleNames() {
        logger.debug("This getRuleNames(from RuleNameServiceImpl) starts here.");
        List<RuleNameDTO> ruleNameDTOs;
        List<RuleName> ruleNames = ruleNameRepository.findAll();

        if(ruleNames.isEmpty()){
            logger.info("RuleNames is empty in DDB!(from getRuleNames, RuleNameServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("RuleNames successfully loaded from DDB(from getRuleNames, RuleNameServiceImpl).");
        ruleNameDTOs = ruleNames.stream().map(ruleName ->
                mapperService.fromRuleName(ruleName)
        ).collect(Collectors.toList());
        return ruleNameDTOs;
    }
    /**
     * Find RuleName by its Id and return RuleNameDTO
     * @param id Integer
     * @return RuleNameDTO
     */
    @Override
    public RuleNameDTO getRuleNameById(Integer id) {
        logger.debug("This getRuleNameById(from RuleNameServiceImpl) starts here.");
        RuleName ruleName = getRuleNameByRuleNameId(id);

        logger.info("RuleName successfully found by its id: {} (from getRuleNameById, RuleNameServiceImpl).", id);
        return mapperService.fromRuleName(ruleName);
    }
    /**
     * Save a new RuleName via RuleNameDTO
     * @param ruleNameDTO RuleNameDTO
     * @return RuleNameDTO
     */
    @Override
    public RuleNameDTO saveNewRuleName(RuleNameDTO ruleNameDTO) {
        logger.debug("This saveNewRuleName(from RuleNameServiceImpl) starts here.");
        RuleName ruleName = mapperService.fromRuleNameDTO(ruleNameDTO);
        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        logger.info("New RuleName successfully saved into DDB(from saveNewRuleName, RuleNameServiceImpl).");
        RuleNameDTO returnRuleNameDTO = mapperService.fromRuleName(savedRuleName);

        return returnRuleNameDTO;
    }
    /**
     * Update a RuleName if its id exists in DDB
     * @param ruleNameDTO RuleNameDTO
     */
    @Override
    public void updateRuleName(RuleNameDTO ruleNameDTO) {
        logger.debug("This updateRuleName method(from RuleNameServiceImpl) starts here.");
        RuleName updateRuleName = mapperService.fromRuleNameDTO(ruleNameDTO);
        updateRuleName.setId(ruleNameDTO.getRuleNameId());

        logger.info("RuleName which id : {} successfully updated(from updateRuleName, RuleNameServiceImpl).", ruleNameDTO.getRuleNameId());
        ruleNameRepository.save(updateRuleName);
    }
    /**
     * Delete a RuleName existing in DDB by RuleName Id
     * @param id id
     */
    @Override
    public void deleteRuleNameById(Integer id) {
        logger.debug("This deleteRuleNameById(from RuleNameServiceImpl starts here.) ");
        RuleName ruleNameByRuleNameId = getRuleNameByRuleNameId(id);
        if(ruleNameByRuleNameId != null) {
            logger.info("RuleName which id :{} successfully deleted from DDB(from RuleNameServiceImpl)", id);
            ruleNameRepository.deleteById(id);
        }
    }

    /**
     * Get RuleName from DDB with its Id and throw ResourceNotFoundException if RuleName is not present.
     * @param id Integer
     * @return ruleName RuleName
     */
    private RuleName getRuleNameByRuleNameId(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> {
            logger.error("This ruleNameId:{} not found!", id);
            throw new ResourcesNotFoundException("This RuleName doesn't exist with this id : " + id + " , from getRuleNameByRuleNameId, RuleNameServiceImpl.");
        });
        return ruleName;
    }
}
