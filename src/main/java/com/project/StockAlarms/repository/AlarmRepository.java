package com.project.StockAlarms.repository;

import com.project.StockAlarms.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByUserId(Long userId);
}
