package com.ifoodapi.domain.repository.impl;

import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.repository.RestauranteCustomRepository;
import com.ifoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.ifoodapi.infrastructure.repository.specs.RestauranteSpecs.*;

@Repository
public class RestauranteCustomRepositoryImpl implements RestauranteCustomRepository {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> findAllByFilter(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFrenteFinal) {

        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurante.class);
        var root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(valueNotNull(taxaFreteInicial)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(valueNotNull(taxaFrenteFinal)) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFrenteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findAllByFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    private boolean valueNotNull(BigDecimal value) {
        return value != null;
    }
}
