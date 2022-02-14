package com.ifoodapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*@ValorZeroIncluiDescricaoObrigatoria(valorField = "taxaFrete",
                                     descricaoField = "nome",
                                     descricaoObrigatoria = "Frete grátis")*/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    private Boolean aberto;

    private Boolean ativo = Boolean.TRUE;

    @ManyToMany
    @JoinTable(name = "restaurante_usuario",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> usuarios = new HashSet<>();

    public void active() {
        setAtivo(true);
    }

    public void inactive() {
        setAtivo(false);
    }

    public void linkFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().add(formaPagamento);
    }

    public void unlinkFormaPagamento(FormaPagamento formaPagamento) {
        this.getFormasPagamento().remove(formaPagamento);
    }

    public void linkUsuarioResponsavel(Usuario usuario) {
        this.getUsuarios().add(usuario);
    }

    public void unlinkUsuarioResponsavel(Usuario usuario) {
        this.getUsuarios().remove(usuario);
    }

    public void opening() {
        this.setAberto(true);
    }

    public void closure() {
        this.setAberto(false);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return this.getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !this.aceitaFormaPagamento(formaPagamento);
    }
}
