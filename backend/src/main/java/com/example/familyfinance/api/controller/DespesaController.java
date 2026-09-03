package com.example.familyfinance.api.controller;

import com.example.familyfinance.application.dto.DespesaRequest;
import com.example.familyfinance.application.dto.DespesaResponse;
import com.example.familyfinance.application.service.DespesaService;
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
@RequestMapping("/expenses")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DespesaResponse criar(@Valid @RequestBody DespesaRequest request) {
        return despesaService.criar(request);
    }

    @GetMapping
    public List<DespesaResponse> listar() {
        return despesaService.listar();
    }
}
