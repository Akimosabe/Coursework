package ru.boganov.coursework.service;

import org.springframework.stereotype.Service;
import ru.boganov.coursework.entity.Music;

import java.util.List;

@Service
public interface MusicService {
    List<Music> getAllMusic();
    Music getMusicById(int id);
    Music addMusic(Music music);
    Music updateMusic(Music music);
    boolean deleteMusicById(int id);
}