package com.example.familyfinance.application.dto;

import jakarta.validation.constraints.NotBlank;

public class MembroFamiliaRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String apelido;

    @NotBlank(message = "Relação é obrigatória")
    private String relacao;

    public MembroFamiliaRequest() {
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
}
