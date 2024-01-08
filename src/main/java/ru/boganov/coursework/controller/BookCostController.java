package ru.boganov.coursework.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.boganov.coursework.config.Authentication;
import ru.boganov.coursework.dto.BookDto;
import ru.boganov.coursework.entity.BookShop;
import ru.boganov.coursework.repository.BookShopRepository;
import ru.boganov.coursework.repository.ShopRepository;
import ru.boganov.coursework.service.LogService;

import java.util.List;

@Controller
public class BookCostController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private BookShopRepository bookShopRepository;

    @Autowired
    private Authentication authentication;

    @Autowired
    private LogService logService;

    @GetMapping("/book-cost-form")
    public String showBookCostForm(Model model) {
        model.addAttribute("shops", shopRepository.findAll());
        model.addAttribute("bookDto", new BookDto());
        return "book-cost-form";
    }

    @PostMapping("/calculateBookCost")
    public String calculateBookCost(@ModelAttribute BookDto bookDto, Model model) {
        String shopName = bookDto.getShopName();

        // Найдем все записи BookShop для выбранного магазина
        List<BookShop> bookShops = bookShopRepository.findByShop_Name(shopName);

        // Рассчитаем общую стоимость книг в магазине
        int totalCost = bookShops.stream()
                .mapToInt(bookShop -> bookShop.getBook().getSalary())
                .sum();

        model.addAttribute("shopName", shopName);
        model.addAttribute("totalCost", totalCost);

        // Логирование
        logService.logAction(getCurrentUser(), "Выполнен расчет общей стоимости книг в магазине " + shopName +
                ". Общая стоимость: " + totalCost);

        return "book-cost-form"; // Возвращаем на ту же страницу с результатами
    }

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}