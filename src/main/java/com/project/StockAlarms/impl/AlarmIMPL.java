package com.project.StockAlarms.impl;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.exception.AlarmException;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.model.StockWrapper;
import com.project.StockAlarms.repository.AlarmRepository;
import com.project.StockAlarms.service.AlarmService;
import com.project.StockAlarms.service.StockService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AlarmIMPL implements AlarmService {

    @Autowired
    private  AlarmRepository alarmRepository;

    @Autowired
    private  StockService stockService;

    @PostConstruct
    public void initialize() {
        initializeStocksToRefresh();
    }

    public void initializeStocksToRefresh() {
        List<String> stockSymbols = findAllStockFromActiveAlarms();
        System.out.println("Symbols from existing active alarms: " + stockSymbols);
        List<StockWrapper> result = stockService.findSocks(stockSymbols);
    }

    @Override
    public String addAlarm(AlarmDTO alarmDTO) {
        if (existsAlarmForSymbol(alarmDTO.getStock(), alarmDTO.getUser().getId())) {
            throw new AlarmException("There is already an alarm for " + alarmDTO.getStock());
        }
            final StockWrapper stock = stockService.findStock(alarmDTO.getStock());

            if (stock == null) {
                throw new AlarmException("Stock with symbol " + alarmDTO.getStock() + " cannot be found");
            }

            Double priceWhenAlarmWasDefined = stock.getStock().getPrice();
            Double changePercent = stock.getStock().getChangePercent();
            System.out.println("ADD ALARM "+ alarmDTO.getStock() + ": " + priceWhenAlarmWasDefined + ",  " + changePercent);
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

            String result = "Added " + newAlarm.getStock() + " for user " + newAlarm.getUser().getEmail();
            return result;
        }

    public boolean existsAlarmForSymbol(String symbol, Long userId) {
        List<Alarm> alarms = findAllByUserId(userId);
        for (Alarm alarm : alarms) {
            if (alarm.getStock().equals(symbol) && alarm.getStatus()) {
                return true;
            }
        }
        return false;
    }

    public List<Alarm> findAll() {
        return alarmRepository.findAll();
    }

    public Optional<Alarm> findById(Long id) {
        return alarmRepository.findById(id);
    }

    public void deleteById(Long id) {
        deleteFromStockToRefresh(id);
        alarmRepository.deleteById(id);
    }

    public void deleteFromStockToRefresh(Long id) {
        Optional<Alarm> alarm = findById(id);
        List<Alarm> alarms = findAllActiveAlarmByStock(alarm.get().getStock());
        if (alarms.size() == 1) {
            stockService.deleteFromStockToRefresh(alarm.get().getStock());
        }
    }

    @Override
    public String updateAlarm(Long id, AlarmDTO alarmDTO){
        Optional<Alarm> optionalAlarm = alarmRepository.findById(id);

        if (optionalAlarm.isEmpty()) {
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

    private List<Alarm> findAllActiveAlarmByStock(String symbol) {
        List<Alarm> alarms = findAll();
        return alarms.stream()
                .filter(alarm -> alarm.getStock().equals(symbol) && alarm.getStatus())
                .collect(Collectors.toList());
    }

    public List<Alarm> findAllByUserId(Long userId){
        return alarmRepository.findAllByUserId(userId);

    }

    public List<String> findAllStockFromActiveAlarms() {
        List<Alarm> alarms = findAll();
        return alarms.stream()
                .filter(Alarm::getStatus)
                .map(Alarm::getStock)
                .collect(Collectors.toList());
    }
}
