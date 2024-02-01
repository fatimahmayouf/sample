package com.fatema.sample.configs;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSessionManager implements ApplicationListener<SessionDestroyedEvent> {

    @Value("${session.timeout.minutes}")
    private int sessionTimeoutMinutes;

    private Map<String, HttpSession> activeSessions = new HashMap<>();

    public void sessionCreated(HttpSession session) {
        activeSessions.put(session.getId(), session);
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        activeSessions.remove(event.getId());
    }

    public boolean isUserLoggedIn(String username) {
        for (HttpSession session : activeSessions.values()) {
            String sessionUsername = (String) session.getAttribute("username");
            if (sessionUsername != null && sessionUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public int getSessionTimeoutMinutes() {
        return sessionTimeoutMinutes;
    }

    public void setSessionTimeoutMinutes(int sessionTimeoutMinutes) {
        this.sessionTimeoutMinutes = sessionTimeoutMinutes;
    }
}

