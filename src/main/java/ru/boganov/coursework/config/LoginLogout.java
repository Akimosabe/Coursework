package ru.boganov.coursework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import ru.boganov.coursework.service.LogService;

@Component
public class LoginLogout {
    @Autowired
    private LogService logService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        logService.logAction(username, "Вход пользователя");
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();
        logService.logAction(username, "Выход пользователя");
    }
}