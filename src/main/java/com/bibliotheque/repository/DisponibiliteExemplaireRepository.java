package com.bibliotheque.repository;

import com.bibliotheque.model.DisponibiliteExemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibiliteExemplaireRepository extends JpaRepository<DisponibiliteExemplaire, Long> {
    @Query("SELECT de FROM DisponibiliteExemplaire de WHERE de.exemplaire.id = :exemplaireId ORDER BY de.dateChangementEtat DESC")
    DisponibiliteExemplaire findLatestByExemplaireId(Long exemplaireId);
}
