package com.bibliotheque.repository;

import com.bibliotheque.model.Retour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetourRepository extends JpaRepository<Retour, Long> {
}
