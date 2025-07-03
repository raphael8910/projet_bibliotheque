package com.bibliotheque.repository;

import com.bibliotheque.model.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Long> {
}
