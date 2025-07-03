package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @ManyToOne
    private TypeLivre typeLivre;
    private String auteur;
    private int nbExemplaire;
    private LocalDate dateParution;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public TypeLivre getTypeLivre() { return typeLivre; }
    public void setTypeLivre(TypeLivre typeLivre) { this.typeLivre = typeLivre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public int getNbExemplaire() { return nbExemplaire; }
    public void setNbExemplaire(int nbExemplaire) { this.nbExemplaire = nbExemplaire; }
    public LocalDate getDateParution() { return dateParution; }
    public void setDateParution(LocalDate dateParution) { this.dateParution = dateParution; }
}
