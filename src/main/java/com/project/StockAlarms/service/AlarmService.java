package com.project.StockAlarms.service;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.model.Alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmService {

    String addAlarm(AlarmDTO alarmDTO);

    List<Alarm> findAll();

    Optional<Alarm> findById(Long id);

    void deleteById(Long id);

    List<Alarm> findAllByUserId(Long userId);
}
