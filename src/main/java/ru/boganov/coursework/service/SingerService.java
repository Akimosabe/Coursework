package ru.boganov.coursework.service;

import org.springframework.stereotype.Service;
import ru.boganov.coursework.entity.Singer;

import java.util.List;

@Service
public interface SingerService {
    List<Singer> getAllSingers();
    Singer getSingerById(int id);
    Singer addSinger(Singer singer);
    Singer updateSinger(Singer singer);
    boolean deleteSingerById(int id);
}