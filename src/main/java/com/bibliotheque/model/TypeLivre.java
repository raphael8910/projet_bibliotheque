package com.bibliotheque.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class TypeLivre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToMany(mappedBy = "typesLivreAutorises")
    private Set<TypeAdherent> typeAdherentsAutorises;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Set<TypeAdherent> getTypeAdherentsAutorises() { return typeAdherentsAutorises; }
    public void setTypeAdherentsAutorises(Set<TypeAdherent> typeAdherentsAutorises) { this.typeAdherentsAutorises = typeAdherentsAutorises; }
}
