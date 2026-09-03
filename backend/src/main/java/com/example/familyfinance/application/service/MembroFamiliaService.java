package com.example.familyfinance.application.service;

import com.example.familyfinance.application.dto.MembroFamiliaRequest;
import com.example.familyfinance.application.dto.MembroFamiliaResponse;
import com.example.familyfinance.domain.entity.MembroFamilia;
import com.example.familyfinance.domain.repository.MembroFamiliaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MembroFamiliaService {

    private final MembroFamiliaRepository membroFamiliaRepository;

    public MembroFamiliaService(MembroFamiliaRepository membroFamiliaRepository) {
        this.membroFamiliaRepository = membroFamiliaRepository;
    }

    public MembroFamiliaResponse criar(MembroFamiliaRequest request) {
        MembroFamilia membro = new MembroFamilia();
        membro.setNome(request.getNome());
        membro.setApelido(request.getApelido());
        membro.setRelacao(request.getRelacao());
        membro.setAtivo(true);

        MembroFamilia salvo = membroFamiliaRepository.save(membro);
        return new MembroFamiliaResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getApelido(),
                salvo.getRelacao(),
                salvo.isAtivo()
        );
    }

    public List<MembroFamiliaResponse> listar() {
        return membroFamiliaRepository.findAll().stream()
                .map(m -> new MembroFamiliaResponse(
                        m.getId(),
                        m.getNome(),
                        m.getApelido(),
                        m.getRelacao(),
                        m.isAtivo()
                ))
                .toList();
    }

    public MembroFamilia buscarOuLancar(UUID id) {
        return membroFamiliaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membro da família não encontrado"));
    }
}
