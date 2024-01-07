package ru.boganov.coursework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boganov.coursework.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}