package com.ifoodapi.api.assembler;

import com.ifoodapi.api.model.output.RestauranteOutput;
import com.ifoodapi.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {


    @Autowired
    private ModelMapper modelMapper;

    public RestauranteOutput toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteOutput.class);
    }

    public List<RestauranteOutput> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
