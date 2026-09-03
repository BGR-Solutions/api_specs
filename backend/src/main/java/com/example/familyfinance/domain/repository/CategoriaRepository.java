package com.example.familyfinance.domain.repository;

import com.example.familyfinance.domain.entity.Categoria;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
