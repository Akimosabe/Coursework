package ru.boganov.coursework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.boganov.coursework.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}