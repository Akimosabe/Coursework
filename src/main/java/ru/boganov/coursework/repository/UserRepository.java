package ru.boganov.coursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boganov.coursework.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}