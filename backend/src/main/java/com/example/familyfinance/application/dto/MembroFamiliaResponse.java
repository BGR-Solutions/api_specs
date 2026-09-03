package com.example.familyfinance.application.dto;

import java.util.UUID;

public class MembroFamiliaResponse {

    private UUID id;
    private String nome;
    private String apelido;
    private String relacao;
    private boolean ativo;

    public MembroFamiliaResponse() {
    }

    public MembroFamiliaResponse(UUID id, String nome, String apelido, String relacao, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.relacao = relacao;
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
