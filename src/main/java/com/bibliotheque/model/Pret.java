package com.bibliotheque.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Bibliothecaire bibliothecaire;

    @ManyToOne
    private Exemplaire exemplaire;

    @ManyToOne
    private Adherent adherent;
    private LocalDateTime datePret;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bibliothecaire getBibliothecaire() { return bibliothecaire; }
    public void setBibliothecaire(Bibliothecaire bibliothecaire) { this.bibliothecaire = bibliothecaire; }
    public Exemplaire getExemplaire() { return exemplaire; }
    public void setExemplaire(Exemplaire exemplaire) { this.exemplaire = exemplaire; }
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public LocalDateTime getDatePret() { return datePret; }
    public void setDatePret(LocalDateTime datePret) { this.datePret = datePret; }
}
