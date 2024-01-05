package ru.boganov.coursework.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boganov.coursework.dao.MusicDAO;
import ru.boganov.coursework.entity.Music;

import java.util.List;

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {

    private final MusicDAO musicDAO;

    @Autowired
    public MusicServiceImpl(MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
    }

    @Override
    @Transactional
    public List<Music> getAllMusic() {
        return musicDAO.getAllMusic();
    }

    @Override
    @Transactional
    public Music getMusicById(int id) {
        return musicDAO.getMusicById(id);
    }

    @Override
    @Transactional
    public Music addMusic(Music music) {
        return musicDAO.saveMusic(music);
    }

    @Override
    @Transactional
    public Music updateMusic(Music music) {
        return musicDAO.saveMusic(music);
    }

    public boolean deleteMusicById(int id) {
        musicDAO.deleteMusic(id);
        return true;
    }
}