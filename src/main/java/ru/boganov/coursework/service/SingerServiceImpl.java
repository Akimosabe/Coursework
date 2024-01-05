package ru.boganov.coursework.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boganov.coursework.dao.SingerDAO;
import ru.boganov.coursework.entity.Singer;

import java.util.List;

@Slf4j
@Service
public class SingerServiceImpl implements SingerService {

    private final SingerDAO singerDAO;

    @Autowired
    public SingerServiceImpl(SingerDAO singerDAO) {
        this.singerDAO = singerDAO;
    }

    @Override
    @Transactional
    public List<Singer> getAllSingers() {
        return singerDAO.getAllSingers();
    }

    @Override
    @Transactional
    public Singer getSingerById(int id) {
        return singerDAO.getSingerById(id);
    }

    @Override
    @Transactional
    public Singer addSinger(Singer singer) {
        return singerDAO.saveSinger(singer);
    }

    @Override
    @Transactional
    public Singer updateSinger(Singer singer) {
        return singerDAO.saveSinger(singer);
    }

    @Override
    @Transactional
    public boolean deleteSingerById(int id) {
        singerDAO.deleteSinger(id);
        return true;
    }
}