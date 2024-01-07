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
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "availability")
    private String availability;

    @Column(name = "created")
    private String created;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookShop> bookShops = new ArrayList<>();

    public void addBookShop(BookShop bookShop) {
        bookShops.add(bookShop);
        bookShop.setShop(this);
    }

    public void removeBookShop(BookShop bookShop) {
        bookShops.remove(bookShop);
        bookShop.setShop(null);
    }
}
