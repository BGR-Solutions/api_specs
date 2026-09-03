package com.example.familyfinance.domain.repository;

import com.example.familyfinance.domain.entity.Despesa;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, UUID> {
}
