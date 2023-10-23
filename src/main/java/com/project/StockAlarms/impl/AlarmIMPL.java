package com.project.StockAlarms.impl;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.repository.AlarmRepository;
import com.project.StockAlarms.service.AlarmService;
import com.project.StockAlarms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmIMPL implements AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private StockService stockService;

    @Override
    public String addAlarm(AlarmDTO alarmDTO) {
        final StockWrapper stock = stockService.findStock(alarmDTO.getStock());
        Double priceWhenAlarmWasDefined = stock.getStock().getPrice();
        Double changePercent = stock.getStock().getChangePercent();
        System.out.println("ADD ALARM " + priceWhenAlarmWasDefined + ",  " + changePercent);
        Alarm newAlarm = new Alarm(
                alarmDTO.getUser(),
                alarmDTO.getStock(),
                priceWhenAlarmWasDefined,
                priceWhenAlarmWasDefined,
                changePercent,
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

    @Override
    public String updateAlarm(Long id, AlarmDTO alarmDTO){
        Optional<Alarm> optionalAlarm = alarmRepository.findById(id);

        if (!optionalAlarm.isPresent()) {
            return "Alarm not found";
        }
        Alarm alarm = optionalAlarm.get();
        alarm.setStock(alarmDTO.getStock());
        alarm.setUpperTarget(alarmDTO.getUpperTarget());
        alarm.setLowerTarget(alarmDTO.getLowerTarget());
        alarm.setStatus(alarmDTO.getStatus());

        alarmRepository.save(alarm);
        return "Updated Successfully";

    }
    public List<Alarm> findAllByUserId(Long userId){
        return alarmRepository.findAllByUserId(userId);
    }
}
