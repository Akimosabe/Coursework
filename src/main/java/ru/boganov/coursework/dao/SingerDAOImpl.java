package ru.boganov.coursework.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.boganov.coursework.entity.Singer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
public class SingerDAOImpl implements SingerDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Singer> getAllSingers() {
        try {
            Query query = entityManager.createQuery("from Singer");
            List<Singer> allSingers = query.getResultList();
            log.info("getAllSingers: Получено " + allSingers.size() + " записей о певцах");
            return allSingers;
        } catch (Exception e) {
            log.error("Ошибка при получении списка певцов: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Singer saveSinger(Singer singer) {
        try {
            Singer savedSinger = entityManager.merge(singer);
            log.info("Сохранена запись о певце: ID " + savedSinger.getId());
            return savedSinger;
        } catch (Exception e) {
            log.error("Ошибка при сохранении записи о певце: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Singer getSingerById(int id) {
        try {
            Singer singer = entityManager.find(Singer.class, id);
            if (singer != null) {
                log.info("Запись о певце сохранена: ID " + id);
            } else {
                log.info("Запись о певце: ID " + id + " не найдена");
            }
            return singer;
        } catch (Exception e) {
            log.error("Ошибка при получении записи о певце: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteSinger(int id) {
        try {
            Query query = entityManager.createQuery("delete from Singer where id = :singerId");
            query.setParameter("singerId", id);
            int deletedCount = query.executeUpdate();
            if (deletedCount > 0) {
                log.info("Запись о певце удалена: ID " + id);
            } else {
                log.info("Запись о певце: ID " + id + " не найдена и не была удалена");
            }
        } catch (Exception e) {
            log.error("Ошибка при удалении записи о певце: " + e.getMessage(), e);
            throw e;
        }
    }
}