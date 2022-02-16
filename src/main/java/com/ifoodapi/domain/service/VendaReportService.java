package com.ifoodapi.domain.service;

import com.ifoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] gerarRelatorioVendaDiaria(VendaDiariaFilter vendaDiariaFilter, String timeOffSet);
}
