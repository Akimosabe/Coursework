package ru.boganov.coursework.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boganov.coursework.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}