package ru.boganov.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.boganov.coursework.entity.UserLogs;
import ru.boganov.coursework.service.LogService;

import java.util.List;

@Controller
public class LogsController {
    private final LogService logService;

    @Autowired
    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/user-action-logs")
    public String getUserActionLogs(Model model) {
        List<UserLogs> userLogs = logService.getAllUserLogs();
        model.addAttribute("userActionLogs", userLogs);
        return "user-action-logs";
    }
}