package com.project.StockAlarms.controller;

import com.project.StockAlarms.dto.AlarmDTO;
import com.project.StockAlarms.exception.AlarmException;
import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alarms")
@CrossOrigin(origins = "http://localhost:3000")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @PostMapping("/add")
    public ResponseEntity<String> addAlarm(@RequestBody AlarmDTO alarmDTO) {
        try {
            String result = alarmService.addAlarm(alarmDTO);
            return ResponseEntity.ok(result);
        } catch (AlarmException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @GetMapping("/getAll")
    public List<Alarm> getAllAlarms() {
        return alarmService.findAll();
    }

    @GetMapping("/getAllByUserId/{userId}")
    public List<Alarm> getAllAlarmsByUserId(@PathVariable Long userId) {
        return alarmService.findAllByUserId(userId);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteAlarm(@PathVariable long id) {
        alarmService.deleteById(id);
        return "Alarm with " + id + " has been deleted successfully.";
    }

    @PutMapping("/update/{id}")
    public String updateAlarm(@PathVariable long id, @RequestBody AlarmDTO alarmDTO) {
        return alarmService.updateAlarm(id, alarmDTO);
    }

}
