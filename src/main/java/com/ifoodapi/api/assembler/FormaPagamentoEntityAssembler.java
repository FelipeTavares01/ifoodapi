package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.FormaPagamentoInput;
import com.ifoodapi.domain.entity.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toEntity(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToEntity(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}
