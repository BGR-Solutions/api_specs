package com.example.familyfinance.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DespesaResponse {

    private UUID id;
    private UUID categoriaId;
    private UUID membroFamiliaId;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataOcorrencia;
    private String observacoes;
    private boolean recorrente;
    private String formaPagamento;

    public DespesaResponse() {
    }

    public DespesaResponse(UUID id, UUID categoriaId, UUID membroFamiliaId, String descricao,
                          BigDecimal valor, LocalDate dataOcorrencia, String observacoes,
                          boolean recorrente, String formaPagamento) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.membroFamiliaId = membroFamiliaId;
        this.descricao = descricao;
        this.valor = valor;
        this.dataOcorrencia = dataOcorrencia;
        this.observacoes = observacoes;
        this.recorrente = recorrente;
        this.formaPagamento = formaPagamento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(UUID categoriaId) {
        this.categoriaId = categoriaId;
    }

    public UUID getMembroFamiliaId() {
        return membroFamiliaId;
    }

    public void setMembroFamiliaId(UUID membroFamiliaId) {
        this.membroFamiliaId = membroFamiliaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public void setRecorrente(boolean recorrente) {
        this.recorrente = recorrente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
