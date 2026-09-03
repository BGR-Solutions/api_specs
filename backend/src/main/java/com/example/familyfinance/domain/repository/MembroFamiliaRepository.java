package com.example.familyfinance.domain.repository;

import com.example.familyfinance.domain.entity.MembroFamilia;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroFamiliaRepository extends JpaRepository<MembroFamilia, UUID> {
}
