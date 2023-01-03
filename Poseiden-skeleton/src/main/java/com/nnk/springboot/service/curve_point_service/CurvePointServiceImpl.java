package com.nnk.springboot.service.curve_point_service;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {
    @Override
    public List<CurvePoint> getCurvePoints() {
        return null;
    }

    @Override
    public CurvePoint getCurvePointById(Integer id) {
        return null;
    }

    @Override
    public CurvePoint saveNewCurvePoint(CurvePoint curvePoint) {
        return null;
    }

    @Override
    public void updateCurvePoint(CurvePoint curvePoint) {

    }

    @Override
    public void deleteCurvePointById(Integer id) {

    }
}
