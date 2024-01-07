package ru.boganov.coursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.boganov.coursework.config.Authentication;
import ru.boganov.coursework.dto.BookDto;
import ru.boganov.coursework.dto.UserDto;
import ru.boganov.coursework.entity.User;
import ru.boganov.coursework.service.LogService;
import ru.boganov.coursework.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Controller
public class SecurityController {

    @Autowired
    private Authentication authentication;

    @Autowired
    private LogService logService;

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user")UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
            result.rejectValue("username", null,
                    "На это имя пользователя уже зарегистрирована учётная запись.");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/add-role")
    public String role(Model model) {
        return "add-role";
    }

    @PostMapping("/add-role")
    public String addRoleToUser(@RequestParam String username, @RequestParam String role) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (this.authentication.getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
            userService.addRoleToUser(username, role);
            logService.logAction(currentPrincipalName, "Изменение роли пользователя");
        }
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String user(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/book-cost-form")
    public String showBookCostForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        return "book-cost-form";
    }

    @PostMapping("/calculate-book-cost")
    public String calculateBookCost(@ModelAttribute("bookDto") BookDto bookDto, Model model) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        double totalCost = calculateTotalBookCost(bookDto);
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("totalCost", totalCost);
        logService.logAction(currentPrincipalName, "Расчёт стоимости книги");
        return "book-cost-form";
    }

    private double calculateTotalBookCost(BookDto book) {
        return (book.getProductionCosts() + book.getDistributionCosts() + book.getOtherPublishingCosts()) / book.getQuantity();
    }
}