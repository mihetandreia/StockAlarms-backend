package com.project.StockAlarms.impl;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.repository.AlarmRepository;
import com.project.StockAlarms.service.AlarmService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlarmIMPL implements AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public String addAlarm(AlarmDTO alarmDTO) {
        Alarm newAlarm = new Alarm(
                alarmDTO.getUser(),
                alarmDTO.getStock(),
                alarmDTO.getUpperTarget(),
                alarmDTO.getLowerTarget(),
                alarmDTO.getStatus()
        );
        alarmRepository.save(newAlarm);

        String result = newAlarm.getStock() + " " +  newAlarm.getUser().getEmail();
        return result;
    }

    public List<Alarm> findAll() {
        return alarmRepository.findAll();
    }

    public Optional<Alarm> findById(Long id) {
        return alarmRepository.findById(id);
    }

    public void deleteById(Long id) {
        alarmRepository.deleteById(id);
    }

    public List<Alarm> findAllByUserId(Long userId){
        return alarmRepository.findAllByUserId(userId);
    }
}
