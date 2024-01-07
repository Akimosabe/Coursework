package ru.boganov.coursework.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class Authentication {
    public org.springframework.security.core.Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}