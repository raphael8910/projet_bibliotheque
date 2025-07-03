package com.bibliotheque.repository;

import com.bibliotheque.model.HistoriquePenalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriquePenaliteRepository extends JpaRepository<HistoriquePenalite, Long> {
}
