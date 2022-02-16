package com.ifoodapi.infrastructure.service;

import com.ifoodapi.domain.filter.VendaDiariaFilter;
import com.ifoodapi.domain.repository.VendaCustomRepository;
import com.ifoodapi.domain.service.VendaReportService;
import com.ifoodapi.infrastructure.service.exception.ReportException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class ReportPdfVendaService implements VendaReportService {

    @Autowired
    private VendaCustomRepository vendaCustomRepository;

    @Override
    public byte[] gerarRelatorioVendaDiaria(VendaDiariaFilter vendaDiariaFilter, String timeOffSet) {

        try {
            var inputStream = this.getClass().getResourceAsStream(
                    "/relatorios/vendas-diarias.jasper");

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaCustomRepository.consultarVendasDiarias(vendaDiariaFilter, timeOffSet);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
