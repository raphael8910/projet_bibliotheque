package com.bibliotheque.model;

import jakarta.persistence.*;

@Entity
public class HistoriqueActiviteAdherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Adherent adherent;
    private int estActif;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public int getEstActif() { return estActif; }
    public void setEstActif(int estActif) { this.estActif = estActif; }
}
