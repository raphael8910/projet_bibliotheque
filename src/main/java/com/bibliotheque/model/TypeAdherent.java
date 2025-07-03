package com.bibliotheque.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class TypeAdherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int quota;
    private int nbJours;

    @ManyToMany
    private Set<TypeLivre> typesLivreAutorises;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getQuota() { return quota; }
    public void setQuota(int quota) { this.quota = quota; }
    public int getNbJours() { return nbJours; }
    public void setNbJours(int nbJours) { this.nbJours = nbJours; }
    public Set<TypeLivre> getTypesLivreAutorises() { return typesLivreAutorises; }
    public void setTypesLivreAutorises(Set<TypeLivre> typesLivreAutorises) { this.typesLivreAutorises = typesLivreAutorises; }
}
