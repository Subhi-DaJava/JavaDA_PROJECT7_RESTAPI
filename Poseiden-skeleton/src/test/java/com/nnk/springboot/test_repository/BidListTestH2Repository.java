package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidListTestH2Repository extends JpaRepository<BidList, Integer> {


}
