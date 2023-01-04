package com.nnk.springboot.service.curve_point_service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;

import java.util.List;

/**
 * CurvePointService allows to insert the business logic
 * in the Curve business domain.
 * @author Subhi
 */
public interface CurvePointService {
    List<CurvePointDTO> getCurvePoints();
    CurvePointDTO getCurvePointById(Integer id);
    CurvePointDTO saveNewCurvePoint(CurvePointDTO curvePointDTO);
    void updateCurvePoint(CurvePointDTO curvePointDTO);
    void deleteCurvePointById(Integer id);

}
