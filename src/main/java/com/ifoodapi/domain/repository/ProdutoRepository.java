package com.ifoodapi.domain.repository;

import com.ifoodapi.domain.entity.FotoProduto;
import com.ifoodapi.domain.entity.Produto;
import com.ifoodapi.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoCustomRepository {


    @Query("from Produto where restaurante.id = :restaurante and id = :produto" )
    Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

    List<Produto> findAllByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(Restaurante restaurante);

    @Query("select fp from FotoProduto fp join fp.produto p " +
            "where p.restaurante.id = :restauranteId and fp.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
