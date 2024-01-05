package ru.boganov.coursework.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.boganov.coursework.entity.Music;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
public class MusicDAOImpl implements MusicDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Music> getAllMusic() {
        try {
            Query query = entityManager.createQuery("from Music");
            List<Music> allMusic = query.getResultList();
            log.info("getAllMusic: Получено " + allMusic.size() + " записей о музыке");
            return allMusic;
        } catch (Exception e) {
            log.error("Ошибка при получении списка музыки: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Music saveMusic(Music music) {
        try {
            Music savedMusic = entityManager.merge(music);
            log.info("Сохранена запись о музыке: ID " + savedMusic.getId());
            return savedMusic;
        } catch (Exception e) {
            log.error("Ошибка при сохранении записи о музыке: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Music getMusicById(int id) {
        try {
            Music music = entityManager.find(Music.class, id);
            if (music != null) {
                log.info("Запись о музыке сохранена: ID " + id);
            } else {
                log.info("Запись о музыке: ID " + id + " не найдена");
            }
            return music;
        } catch (Exception e) {
            log.error("Ошибка при получении записи о музыке: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteMusic(int id) {
        try {
            Query query = entityManager.createQuery("delete from Music where id = :musicId");
            query.setParameter("musicId", id);
            int deletedCount = query.executeUpdate();
            if (deletedCount > 0) {
                log.info("Запись о музыке удалена: ID " + id);
            } else {
                log.info("Запись о музыке: ID " + id + " не найдена и не была удалена");
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении записи о музыке: " + e.getMessage(), e);
            throw e;
        }
    }
}