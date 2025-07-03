package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livre livre;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
}
