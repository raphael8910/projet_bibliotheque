package com.bibliotheque.repository;

import com.bibliotheque.model.HistoriqueActiviteAdherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueActiviteAdherentRepository extends JpaRepository<HistoriqueActiviteAdherent, Long> {
    @Query("SELECT ha FROM HistoriqueActiviteAdherent ha WHERE ha.adherent.id = :adherentId ORDER BY ha.id DESC")
    HistoriqueActiviteAdherent findLatestByAdherentId(Long adherentId);
}
