package com.bibliotheque.repository;

import com.bibliotheque.model.TypeAdherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAdherentRepository extends JpaRepository<TypeAdherent, Long> {
}
