package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.CurvePoint;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurvePointTestH2Repository extends JpaRepository<CurvePoint, Integer> {

}
