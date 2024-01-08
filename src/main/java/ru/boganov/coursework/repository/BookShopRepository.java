package ru.boganov.coursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boganov.coursework.entity.Book;
import ru.boganov.coursework.entity.BookShop;

import java.util.List;

public interface BookShopRepository extends JpaRepository<BookShop, Long> {
    List<BookShop> findByShop_Name(String shopName);
    List<BookShop> findByBook(Book book);

    void deleteByBook(Book book);
}