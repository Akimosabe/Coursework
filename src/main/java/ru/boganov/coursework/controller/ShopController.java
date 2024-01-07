package ru.boganov.coursework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.boganov.coursework.config.Authentication;
import ru.boganov.coursework.entity.Shop;
import ru.boganov.coursework.repository.ShopRepository;
import ru.boganov.coursework.service.LogService;

import java.util.Optional;
@Slf4j
@Controller
public class ShopController {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private Authentication authentication;

    @Autowired
    private LogService logService;

    @GetMapping("/list-shops")
    public ModelAndView getAllBooks() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-shops");
        mav.addObject("shops", shopRepository.findAll());
        return mav;
    }

    @GetMapping("/addShopForm")
    public ModelAndView addShopForm() {
        if (authentication.getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("READ_ONLY"))) {
            return new ModelAndView("redirect:/list-shops");
        } else {
            ModelAndView mav = new ModelAndView("add-shop-form");
            Shop shop = new Shop();
            mav.addObject("shop", shop);
            return mav;
        }
    }
    @PostMapping("/saveShop")
    public String saveBook(@ModelAttribute Shop shop) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        shop.setCreated(currentPrincipalName);
        shopRepository.save(shop);
        logService.logAction(currentPrincipalName, "Создание магазина");
        return "redirect:/list-shops";
    }
    @GetMapping("/showUpdateFormShop")
    public ModelAndView showUpdateForm(@RequestParam Long shopId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (shop.getCreated().equals(currentPrincipalName))) {
                ModelAndView mav = new ModelAndView("add-shop-form");
                mav.addObject("shop", shop);
                logService.logAction(currentPrincipalName, "Изменение магазина");
                return mav;
            } else {
                return new ModelAndView("redirect:/list-shops");
            }
        } else {
            return new ModelAndView("redirect:/list-shops");
        }
    }
    @GetMapping("/deleteShop")
    public String deleteShop(@RequestParam Long shopId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (shop.getCreated().equals(currentPrincipalName))) {
                shopRepository.deleteById(shopId);
                logService.logAction(currentPrincipalName, "Удаление магазина");
            }
        }
        return "redirect:/list-shops";
    }
}