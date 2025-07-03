package com.bibliotheque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TypeAdherent typeAdherent;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TypeAdherent getTypeAdherent() { return typeAdherent; }
    public void setTypeAdherent(TypeAdherent typeAdherent) { this.typeAdherent = typeAdherent; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
