package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoriquePenalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Adherent adherent;

    @ManyToOne
    private Penalite penalite;
    private LocalDateTime dateChangementEtat;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public Penalite getPenalite() { return penalite; }
    public void setPenalite(Penalite penalite) { this.penalite = penalite; }
    public LocalDateTime getDateChangementEtat() { return dateChangementEtat; }
    public void setDateChangementEtat(LocalDateTime dateChangementEtat) { this.dateChangementEtat = dateChangementEtat; }
}
