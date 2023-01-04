package com.nnk.springboot.service.curve_point_service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * CurvePointServiceImpl: CRUD
 * @author Subhi
 */
@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {
    private static final Logger logger = LoggerFactory.getLogger(CurvePointServiceImpl.class);
    private final CurvePointRepository curvePointRepository;
    private final MapperService mapperService;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository, MapperService mapperService) {
        this.curvePointRepository = curvePointRepository;
        this.mapperService = mapperService;
    }


    /**
     * Get the all CurvePoint from DDB and transfer to CurvePointDTO
     * @return List of BidListDTO
     */
    @Override
    public List<CurvePointDTO> getCurvePoints() {
        logger.debug("This getCurvePoints(from CurvePointServiceImpl) starts here.");
        List<CurvePointDTO> curvePointDTOs;
        List<CurvePoint> curvePoints = curvePointRepository.findAll();

        if(curvePoints.isEmpty()){
            logger.info("CurvePoints is empty in DDB!(from getCurvePoints, CurvePointServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("CurvePoints successfully loaded from DDB(from getCurvePoints, CurvePointServiceImpl).");
        curvePointDTOs = curvePoints.stream().map(curvePoint ->
                mapperService.fromCurvePoint(curvePoint)
        ).collect(Collectors.toList());
        return curvePointDTOs;
    }

    /**
     * Find CurvePoint by its Id and return CurvePointDTO
     * @param id Integer
     * @return CurvePointDTO
     */
    @Override
    public CurvePointDTO getCurvePointById(Integer id) {
        logger.debug("This getCurvePointById(from CurvePointServiceImpl) starts here.");
        CurvePoint curvePoint = getCurvePointByCurvePointId(id);

        logger.info("CurvePoint successfully found by its id: {} (from getCurvePointById, CurvePointServiceImpl).", id);
        return mapperService.fromCurvePoint(curvePoint);
    }
    /**
     * Get CurvePoint from DDB with its Id and throw ResourceNotFoundException if CurvePoint is not present.
     * @param id Integer
     * @return curvePoint CurvePoint
     */
    private CurvePoint getCurvePointByCurvePointId(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> {
            logger.error("This curvePointId:{} not found!", id);
            throw new ResourcesNotFoundException("This CurvePoint doesn't exist with this id : " + id + " , from getCurvePointByCurvePontId, CurvePointServiceImpl.");
        });
        return curvePoint;
    }

    /**
     * Save a new CurvePoint via CurvePointDTO
     * @param curvePointDTO CurvePointDTO
     * @return CurvePointDTO
     */
    @Override
    public CurvePointDTO saveNewCurvePoint(CurvePointDTO curvePointDTO) {
        logger.debug("This saveNewCurvePoint(from CurvePointServiceImpl) starts here.");
        CurvePoint curvePoint = mapperService.fromCurvePointDTO(curvePointDTO);
        CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
        logger.info("New CurvePoint successfully saved into DDB(from saveNewCurvePoint, CurvePointServiceImpl).");
        CurvePointDTO returnCurvePointDTO = mapperService.fromCurvePoint(savedCurvePoint);
        return returnCurvePointDTO;
    }

    /**
     * Update a CurvePoint if its id exists in DDB
     * @param curvePointDTO CurvePointDTO
     */
    @Override
    public void updateCurvePoint(CurvePointDTO curvePointDTO) {
        logger.debug("This updateCurvePoint method(from CurvePointServiceImpl) starts here.");
        CurvePoint updateCurvePoint = mapperService.fromCurvePointDTO(curvePointDTO);
        updateCurvePoint.setId(curvePointDTO.getCurveid());

        logger.info("CurvePoint which id : {} successfully updated(from updateCurvePoint, CurvePointServiceImpl).", curvePointDTO.getCurveid());
        curvePointRepository.save(updateCurvePoint);
    }
    /**
     * Delete a CurvePoint existing in DDB by CurvePointId
     * @param id id
     */
    @Override
    public void deleteCurvePointById(Integer id) {
        logger.debug("This deleteCurvePointById(from CurvePointServiceImpl starts here.) ");
        CurvePoint curvePointById = getCurvePointByCurvePointId(id);
        if(curvePointById != null) {
            logger.info("CurvePoint which id :{} successfully deleted from DDB(from CurvePointServiceImpl)", id);
            curvePointRepository.deleteById(id);
        }
    }
}
