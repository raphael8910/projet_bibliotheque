package com.bibliotheque.repository;


import com.bibliotheque.model.Bibliothecaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BibliothecaireRepository extends JpaRepository<Bibliothecaire, Long>{
    
}
