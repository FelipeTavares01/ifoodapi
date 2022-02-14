package com.ifoodapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Grupo implements Comparable<Grupo> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public void linkPermissao(Permissao permissao) {
        this.getPermissoes().add(permissao);
    }

    public void unlinkPermissao(Permissao permissao) {
        this.getPermissoes().remove(permissao);
    }

    @Override
    public int compareTo(Grupo o) {
        return (int) (this.getId() - o.getId());
    }
}
