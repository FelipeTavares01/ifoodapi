package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.filter.VendaDiariaFilter;
import com.ifoodapi.domain.model.VendaDiaria;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VendaCustomRepository {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet);
}
