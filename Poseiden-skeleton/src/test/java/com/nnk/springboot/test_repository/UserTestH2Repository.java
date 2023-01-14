package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTestH2Repository extends JpaRepository<User, Integer> {
}
