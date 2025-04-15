package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> { }
