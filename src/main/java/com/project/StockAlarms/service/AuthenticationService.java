package com.project.StockAlarms.service;

import com.project.StockAlarms.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private  UserSessionService userSessionService;


    public String authenticateUser(String email, Long userId) {
        UserSession userSession = new UserSession();
        userSession.setEmail(email);
        userSession.setUserId(userId);

        String sessionId = UUID.randomUUID().toString();

        userSessionService.addUserSession(sessionId, userSession);

        return  sessionId;
    }

    public UserSession getUserSessionBySessionId(String sessionId) {
        return userSessionService.getUserSession(sessionId);
    }

    public void removeSession(String sessionId) {
        userSessionService.removeUserSession(sessionId);
    }
}
