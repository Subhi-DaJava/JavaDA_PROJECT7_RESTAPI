package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleNameTestH2Repository extends JpaRepository<RuleName, Integer> {
}
