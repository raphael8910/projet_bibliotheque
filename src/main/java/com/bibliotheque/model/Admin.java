package com.bibliotheque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pseudo;
    private String motdepasse;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getMotdepasse() { return motdepasse; }
    public void setMotdepasse(String motdepasse) { this.motdepasse = motdepasse; }
}
