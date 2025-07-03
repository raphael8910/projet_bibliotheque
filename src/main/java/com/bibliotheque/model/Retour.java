package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Retour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Bibliothecaire bibliothecaire;

    @ManyToOne
    private Pret pret;
    private LocalDateTime dateRetour;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bibliothecaire getBibliothecaire() { return bibliothecaire; }
    public void setBibliothecaire(Bibliothecaire bibliothecaire) { this.bibliothecaire = bibliothecaire; }
    public Pret getPret() { return pret; }
    public void setPret(Pret pret) { this.pret = pret; }
    public LocalDateTime getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDateTime dateRetour) { this.dateRetour = dateRetour; }
}
