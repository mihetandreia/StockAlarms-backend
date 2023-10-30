package com.project.StockAlarms.service;

import com.project.StockAlarms.model.Alarm;
import com.project.StockAlarms.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TriggeredAlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private MailSenderService mailSenderService;

    public boolean updateAlarmsForSymbol(String symbol, Double currentPrice, Double variance) {
        List<Alarm> alarms = alarmRepository.findAllByStock(symbol)
                .stream()
                .filter(alarm -> alarm.getStatus())
                .collect(Collectors.toList());
        System.out.println(alarms);
        int triggeredAlarmCount = 0;
        for (Alarm alarm : alarms) {
            updateCurrentPriceAndVariance(alarm, currentPrice, variance);
            boolean triggeredAlarm = checkAlarmTargets(alarm);
            triggeredAlarmCount += triggeredAlarm ? 1 : 0;
        }
        return keepInStocksToRefresh(alarms.size(), triggeredAlarmCount);
    }

    public boolean keepInStocksToRefresh(int alarmsSize, int triggeredAlarmCount) {
        if (alarmsSize == triggeredAlarmCount) {
            return false;
        }
        return true;
    }

    public void updateCurrentPriceAndVariance(Alarm alarm, Double currentPrice, Double variance) {
        alarm.setCurrentPrice(currentPrice);
        alarm.setChangePercent(variance);
        alarmRepository.save(alarm);
    }

    private boolean checkAlarmTargets(Alarm alarm) {
        if (alarm.getChangePercent() > alarm.getUpperTarget() || alarm.getChangePercent() < alarm.getLowerTarget()) {
            sendMail(alarm);
            return true;
        }
        return false;
    }

    private void sendMail(Alarm alarm) {
        String to = alarm.getUser().getEmail();
        String subject = "Stock Alarm Triggered for " + alarm.getStock();
        String body = "The stock alarm you set for " + alarm.getStock() +" has been triggered.\n\n" +
                "Alarm Details:\n" +
                "- Stock Symbol: "+ alarm.getStock() +"\n" +
                "- Original Price: " + alarm.getPriceWhenAlarmWasDefined() +"\n" +
                "- New Price: " + alarm.getCurrentPrice() +"\n\n" +
                "The stock price has met the conditions you specified, and the alarm is now marked as inactive.\n\n" +
                "Thank you for using our stock alarm service.";
        mailSenderService.sendNewMail(to, subject, body);
        markAlarmAsInactive(alarm);

    }

    private void markAlarmAsInactive(Alarm alarm) {
        alarm.setStatus(false);
        alarmRepository.save(alarm);
    }
}
