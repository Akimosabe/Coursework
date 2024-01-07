package ru.boganov.coursework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.boganov.coursework.config.Authentication;
import ru.boganov.coursework.entity.Book;
import ru.boganov.coursework.entity.BookShop;
import ru.boganov.coursework.entity.Shop;
import ru.boganov.coursework.repository.BookRepository;
import ru.boganov.coursework.repository.BookShopRepository;
import ru.boganov.coursework.repository.ShopRepository;
import ru.boganov.coursework.service.LogService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private BookShopRepository bookShopRepository;

    @Autowired
    private Authentication authentication;

    @Autowired
    private LogService logService;

    @GetMapping("/list-books")
    public ModelAndView getAllBooks() {
        log.info("/list-books -> connection");
        ModelAndView mav = new ModelAndView("list-books");
        mav.addObject("books", bookRepository.findAll());
        mav.addObject("shops", shopRepository.findAll());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm() {
        if (authentication.getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("READ_ONLY"))) {
            return new ModelAndView("redirect:/list-books");
        } else {
            ModelAndView mav = new ModelAndView("add-book-form");
            Book book = new Book();
            mav.addObject("book", book);
            mav.addObject("shops", shopRepository.findAll());
            return mav;
        }
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book, @RequestParam List<Long> shopIds) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        book.setCreated(currentPrincipalName);
        bookRepository.save(book);

        // Связываем книгу с магазинами
        for (Long shopId : shopIds) {
            Shop shop = shopRepository.findById(shopId).orElseThrow();
            bookShopRepository.save(new BookShop(book, shop));
        }

        logService.logAction(currentPrincipalName, "Добавил книгу");
        return "redirect:/list-books";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (book.getCreated().equals(currentPrincipalName))) {
                ModelAndView mav = new ModelAndView("add-book-form");
                mav.addObject("book", book);
                mav.addObject("shops", shopRepository.findAll());
                logService.logAction(currentPrincipalName, "Изменил книгу");
                return mav;
            } else {
                return new ModelAndView("redirect:/list-books");
            }
        } else {
            return new ModelAndView("redirect:/list-books");
        }
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (authentication.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ADMIN")) ||
                    (book.getCreated().equals(currentPrincipalName))) {
                // Удаляем связи книги с магазинами перед удалением книги
                bookShopRepository.deleteByBook(book);
                bookRepository.deleteById(bookId);
                logService.logAction(currentPrincipalName, "Удалил книгу");
            }
        }
        return "redirect:/list-books";
    }
}