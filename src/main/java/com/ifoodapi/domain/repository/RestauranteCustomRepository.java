package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.entity.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteCustomRepository {

    List<Restaurante> findAllByFilter(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFrenteFinal);

    List<Restaurante> findAllByFreteGratis(String nome);
}
