package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.input.RestauranteInput;
import com.ifoodapi.domain.entity.Cozinha;
import com.ifoodapi.domain.entity.Endereco;
import com.ifoodapi.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RestauranteEntityAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToEntity(RestauranteInput restauranteInput, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        if(Objects.nonNull(restaurante.getEndereco())) {
            restaurante.setEndereco(new Endereco());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
