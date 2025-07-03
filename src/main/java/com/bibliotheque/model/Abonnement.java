package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Adherent adherent;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
