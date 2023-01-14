package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingTestH2Repository extends JpaRepository<Rating, Integer> {
}
