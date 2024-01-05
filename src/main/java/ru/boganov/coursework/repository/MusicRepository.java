package ru.boganov.coursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boganov.coursework.entity.Music;

public interface MusicRepository extends JpaRepository<Music, Integer> {
}

