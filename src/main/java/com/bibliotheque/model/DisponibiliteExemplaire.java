package com.bibliotheque.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DisponibiliteExemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exemplaire exemplaire;
    private int estDisponible;
    private LocalDateTime dateChangementEtat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Exemplaire getExemplaire() { return exemplaire; }
    public void setExemplaire(Exemplaire exemplaire) { this.exemplaire = exemplaire; }
    public int getEstDisponible() { return estDisponible; }
    public void setEstDisponible(int estDisponible) { this.estDisponible = estDisponible; }
    public LocalDateTime getDateChangementEtat() { return dateChangementEtat; }
    public void setDateChangementEtat(LocalDateTime dateChangementEtat) { this.dateChangementEtat = dateChangementEtat; }
}
