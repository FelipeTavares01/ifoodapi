package com.ifoodapi.domain.repository.impl;

import com.ifoodapi.domain.entity.Pedido;
import com.ifoodapi.domain.entity.StatusPedido;
import com.ifoodapi.domain.filter.VendaDiariaFilter;
import com.ifoodapi.domain.model.VendaDiariaModel;
import com.ifoodapi.domain.repository.VendaCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class VendaCustomRepositoryImpl implements VendaCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiariaModel> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffSet) {

        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiariaModel.class);
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();

        var functionConvertTzDataCriacao = builder.function(
                "convert_tz", Date.class, root.get("dataCriacao"),
                builder.literal("+00:00"), builder.literal(timeOffSet));

        var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);

        var selection = builder.construct(VendaDiariaModel.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));


        if (Objects.nonNull(vendaDiariaFilter.getRestauranteId())) {
            predicates.add(builder.equal(root.get("restaurante"), vendaDiariaFilter.getRestauranteId()));
        }

        if (Objects.nonNull(vendaDiariaFilter.getDataCriacaoInicio())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoInicio()));
        }

        if (Objects.nonNull(vendaDiariaFilter.getDataCriacaoFim())) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }

}
