package com.ifoodapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiariaModel {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
