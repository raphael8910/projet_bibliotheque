package com.bibliotheque.repository;

import com.bibliotheque.model.TypeLivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeLivreRepository extends JpaRepository<TypeLivre, Long> {
}
