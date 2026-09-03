package com.example.familyfinance.application.service;

import com.example.familyfinance.application.dto.DespesaRequest;
import com.example.familyfinance.application.dto.DespesaResponse;
import com.example.familyfinance.domain.entity.Categoria;
import com.example.familyfinance.domain.entity.Despesa;
import com.example.familyfinance.domain.entity.MembroFamilia;
import com.example.familyfinance.domain.repository.DespesaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final CategoriaService categoriaService;
    private final MembroFamiliaService membroFamiliaService;

    public DespesaService(
            DespesaRepository despesaRepository,
            CategoriaService categoriaService,
            MembroFamiliaService membroFamiliaService) {
        this.despesaRepository = despesaRepository;
        this.categoriaService = categoriaService;
        this.membroFamiliaService = membroFamiliaService;
    }

    public DespesaResponse criar(DespesaRequest request) {
        if (request.getValor() == null || request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da despesa deve ser maior que zero");
        }

        Categoria categoria = categoriaService.buscarOuLancar(request.getCategoriaId());
        MembroFamilia membro = membroFamiliaService.buscarOuLancar(request.getMembroFamiliaId());

        Despesa despesa = new Despesa();
        despesa.setCategoria(categoria);
        despesa.setMembroFamilia(membro);
        despesa.setDescricao(request.getDescricao());
        despesa.setValor(request.getValor());
        despesa.setDataOcorrencia(request.getDataOcorrencia());
        despesa.setObservacoes(request.getObservacoes());
        despesa.setRecorrente(request.isRecorrente());
        despesa.setFormaPagamento(request.getFormaPagamento());

        Despesa salva = despesaRepository.save(despesa);
        return new DespesaResponse(
                salva.getId(),
                salva.getCategoria().getId(),
                salva.getMembroFamilia().getId(),
                salva.getDescricao(),
                salva.getValor(),
                salva.getDataOcorrencia(),
                salva.getObservacoes(),
                salva.isRecorrente(),
                salva.getFormaPagamento()
        );
    }

    public List<DespesaResponse> listar() {
        return despesaRepository.findAll().stream()
                .map(d -> new DespesaResponse(
                        d.getId(),
                        d.getCategoria().getId(),
                        d.getMembroFamilia().getId(),
                        d.getDescricao(),
                        d.getValor(),
                        d.getDataOcorrencia(),
                        d.getObservacoes(),
                        d.isRecorrente(),
                        d.getFormaPagamento()
                ))
                .toList();
    }
}
