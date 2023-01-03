package com.nnk.springboot.service.curve_point_service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

/**
 * CurvePointService allows to insert the business logic
 * in the Curve business domain.
 * @author Subhi
 */
public interface CurvePointService {
    List<CurvePoint> getCurvePoints();
    CurvePoint getCurvePointById(Integer id);
    CurvePoint saveNewCurvePoint(CurvePoint curvePoint);
    void updateCurvePoint(CurvePoint curvePoint);
    void deleteCurvePointById(Integer id);

}
