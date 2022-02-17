package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.filter.VendaDiariaFilter;
import com.ifoodapi.domain.model.VendaDiariaModel;

import java.util.List;

public interface VendaCustomRepository {

    List<VendaDiariaModel> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet);
}
