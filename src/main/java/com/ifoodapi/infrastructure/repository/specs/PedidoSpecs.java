package com.ifoodapi.infrastructure.repository.specs;

import com.ifoodapi.domain.entity.Pedido;
import com.ifoodapi.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

public class PedidoSpecs {

    public static Specification<Pedido> comFiltros(PedidoFilter pedidoFilter) {
        return ((root, query, builder) -> {

            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            var predicates = new ArrayList<Predicate>();

            if(Objects.nonNull(pedidoFilter.getClienteId())) {
                predicates.add(builder.equal(root.get("cliente"), pedidoFilter.getClienteId()));
            }

            if(Objects.nonNull(pedidoFilter.getRestauranteId())) {
                predicates.add(builder.equal(root.get("restaurante"), pedidoFilter.getRestauranteId()));
            }

            if(Objects.nonNull(pedidoFilter.getDataCriacaoInicio())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoInicio()));
            }

            if(Objects.nonNull(pedidoFilter.getDataCriacaoFim())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
