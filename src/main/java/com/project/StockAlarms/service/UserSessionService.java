package com.project.StockAlarms.service;

import com.project.StockAlarms.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionService {
    private final Map<String, UserSession> userSessions = new HashMap<>();

    public void addUserSession(String sessionId, UserSession userSession) {
        userSessions.put(sessionId, userSession);
    }

    public UserSession getUserSession(String sessionId) {
        return userSessions.get(sessionId);
    }


    public void removeUserSession(String sessionId) {
        userSessions.remove(sessionId);
    }
}
