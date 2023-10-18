package com.project.StockAlarms.controller;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @PostMapping("/alarm")
    public String addAlarm(@RequestBody AlarmDTO alarmDTO) {
        String result = alarmService.addAlarm(alarmDTO);
        return result;
    }

    @GetMapping("/alarms")
    List<Alarm> getAllAlarms() {
        return alarmService.findAll();
    }

    @GetMapping("/alarms/{userId}")
    List<Alarm> getAllAlarmsByUserId(@PathVariable Long userId) {
        return alarmService.findAllByUserId(userId);
    }

    @DeleteMapping("/alarm/{id}")
    String deleteAlarm(@PathVariable long id) {
        alarmService.deleteById(id);
        return "Alarm with " + id + " has been deleted successfully.";
    }

}
