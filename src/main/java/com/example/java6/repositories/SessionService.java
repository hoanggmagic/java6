package com.example.java6.repositories;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final HttpSession session;

    public SessionService(HttpSession session) {
        this.session = session;
    }

    public void set(String key, Object value) {
        session.setAttribute(key, value);
    }

    public <T> T get(String key, T defaultValue) {
        T value = (T) session.getAttribute(key);
        return (value != null) ? value : defaultValue;
    }

    public void remove(String key) {
        session.removeAttribute(key);
    }
}
