package com.example.familyfinance.application.dto;

import com.example.familyfinance.domain.enums.TipoCategoria;
import java.util.UUID;

public class CategoriaResponse {

    private UUID id;
    private String nome;
    private TipoCategoria tipo;
    private String descricao;
    private boolean ativo;

    public CategoriaResponse() {
    }

    public CategoriaResponse(UUID id, String nome, TipoCategoria tipo, String descricao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
