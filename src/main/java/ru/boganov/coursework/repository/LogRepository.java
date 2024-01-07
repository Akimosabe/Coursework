package ru.boganov.coursework.repository;

import ru.boganov.coursework.entity.UserLogs;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogRepository extends JpaRepository<UserLogs, Long> {
}
