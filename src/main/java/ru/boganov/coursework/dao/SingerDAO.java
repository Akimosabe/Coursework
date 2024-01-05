package ru.boganov.coursework.dao;

import ru.boganov.coursework.entity.Singer;

import java.util.List;

public interface SingerDAO {
    List<Singer> getAllSingers();
    Singer saveSinger(Singer singer);
    Singer getSingerById(int id);
    void deleteSinger(int id);
}