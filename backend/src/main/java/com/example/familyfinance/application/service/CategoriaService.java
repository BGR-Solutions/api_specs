package com.example.familyfinance.application.service;

import com.example.familyfinance.application.dto.CategoriaRequest;
import com.example.familyfinance.application.dto.CategoriaResponse;
import com.example.familyfinance.domain.entity.Categoria;
import com.example.familyfinance.domain.repository.CategoriaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setTipo(request.getTipo());
        categoria.setDescricao(request.getDescricao());
        categoria.setAtivo(true);

        Categoria salva = categoriaRepository.save(categoria);
        return new CategoriaResponse(
                salva.getId(),
                salva.getNome(),
                salva.getTipo(),
                salva.getDescricao(),
                salva.isAtivo()
        );
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaResponse(
                        c.getId(),
                        c.getNome(),
                        c.getTipo(),
                        c.getDescricao(),
                        c.isAtivo()
                ))
                .toList();
    }

    public Categoria buscarOuLancar(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }
}
