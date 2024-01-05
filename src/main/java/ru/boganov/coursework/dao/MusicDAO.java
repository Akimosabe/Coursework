package ru.boganov.coursework.dao;

import ru.boganov.coursework.entity.Music;

import java.util.List;

public interface MusicDAO {
    List<Music> getAllMusic();
    Music saveMusic(Music music);
    Music getMusicById(int id);
    void deleteMusic(int id);
}