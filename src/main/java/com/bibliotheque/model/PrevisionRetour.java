package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PrevisionRetour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pret pret;
    private LocalDateTime dateRetourPrevue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pret getPret() { return pret; }
    public void setPret(Pret pret) { this.pret = pret; }
    public LocalDateTime getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDateTime dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
}
