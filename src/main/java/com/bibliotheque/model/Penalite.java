package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
public class Penalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nbJours;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getNbJours() { return nbJours; }
    public void setNbJours(int nbJours) { this.nbJours = nbJours; }
}
