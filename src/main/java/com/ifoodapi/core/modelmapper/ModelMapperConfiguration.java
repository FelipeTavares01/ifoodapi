package com.ifoodapi.core.modelmapper;

import com.ifoodapi.api.model.input.ItemPedidoInput;
import com.ifoodapi.api.model.output.EnderecoOutput;
import com.ifoodapi.domain.entity.Endereco;
import com.ifoodapi.domain.entity.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoOutput.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getNome(),
                (enderecoOutput, value) -> enderecoOutput.setCidade(value));

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getEstado().getNome(),
                (enderecoOutput, value) -> enderecoOutput.setEstado(value));

//        var restauranteToModelTypeMap = modelMapper.createTypeMap(Restaurante.class, RestauranteOutput.class);
//
//        restauranteToModelTypeMap.<String>addMapping(
//                restaurante -> restaurante.getEndereco().getCidade().getNome(),
//                (restauranteOutput, value) -> restauranteOutput.getEndereco().setCidade(value)
//        );

        return modelMapper;
    }
}
