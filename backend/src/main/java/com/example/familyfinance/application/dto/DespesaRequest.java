package com.example.familyfinance.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DespesaRequest {

    @NotNull(message = "Categoria da despesa é obrigatória")
    private UUID categoriaId;

    @NotNull(message = "Membro da família é obrigatório")
    private UUID membroFamiliaId;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Data de ocorrência é obrigatória")
    private LocalDate dataOcorrencia;

    private String observacoes;
    private boolean recorrente;
    private String formaPagamento;

    public DespesaRequest() {
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
