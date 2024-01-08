package ru.boganov.coursework.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class BookDto {
    private String name;
    private String author;
    private String genre;
    private int salary;
    private String shopName;
}