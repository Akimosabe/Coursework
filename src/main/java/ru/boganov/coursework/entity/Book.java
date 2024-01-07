package ru.boganov.coursework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "salary")
    private int salary;

    @Column(name = "created")
    private String created;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookShop> bookShops = new ArrayList<>();

    public void addBookShop(BookShop bookShop) {
        bookShops.add(bookShop);
        bookShop.setBook(this);
    }

    public void removeBookShop(BookShop bookShop) {
        bookShops.remove(bookShop);
        bookShop.setBook(null);
    }
}
