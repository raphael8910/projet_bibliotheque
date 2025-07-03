package com.bibliotheque.repository;

import com.bibliotheque.model.PrevisionRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PrevisionRetourRepository extends JpaRepository<PrevisionRetour, Long> {
    Optional<PrevisionRetour> findByPretId(Long pretId);
}