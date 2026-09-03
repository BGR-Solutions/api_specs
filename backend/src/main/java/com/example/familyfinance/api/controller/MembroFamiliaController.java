package com.example.familyfinance.api.controller;

import com.example.familyfinance.application.dto.MembroFamiliaRequest;
import com.example.familyfinance.application.dto.MembroFamiliaResponse;
import com.example.familyfinance.application.service.MembroFamiliaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MembroFamiliaController {

    private final MembroFamiliaService membroFamiliaService;

    public MembroFamiliaController(MembroFamiliaService membroFamiliaService) {
        this.membroFamiliaService = membroFamiliaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembroFamiliaResponse criar(@Valid @RequestBody MembroFamiliaRequest request) {
        return membroFamiliaService.criar(request);
    }

    @GetMapping
    public List<MembroFamiliaResponse> listar() {
        return membroFamiliaService.listar();
    }
}
