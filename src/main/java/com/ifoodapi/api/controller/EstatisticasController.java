package com.ifoodapi.api.controller;

import com.ifoodapi.domain.filter.VendaDiariaFilter;
import com.ifoodapi.domain.model.VendaDiaria;
import com.ifoodapi.domain.repository.VendaCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaCustomRepository vendaCustomRepository;

    @GetMapping("/vendas-diarias")
    public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        return ResponseEntity.ok(vendaCustomRepository.consultarVendasDiarias(vendaDiariaFilter));
    }
}
