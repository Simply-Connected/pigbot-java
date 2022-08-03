package com.simplyconnected.pigbot.repository;

import com.simplyconnected.pigbot.model.Pig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PigRepository extends JpaRepository<Pig, Long> {
    List<Pig> findTop10ByOrderByWeightDesc();
}
