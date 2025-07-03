package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Prolongation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pret pret;
    private LocalDateTime date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pret getPret() { return pret; }
    public void setPret(Pret pret) { this.pret = pret; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
