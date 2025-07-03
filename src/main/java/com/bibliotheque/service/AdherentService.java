package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class AdherentService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private TypeAdherentRepository typeAdherentRepository;
    @Autowired
    private HistoriqueActiviteAdherentRepository historiqueActiviteAdherentRepository;

    public String inscrireAdherent(String nom, String prenom, String telephone, Long typeAdherentId, LocalDate dateNaissance) {
        if (nom == null || prenom == null || telephone == null || typeAdherentId == null || dateNaissance == null) {
            throw new RuntimeException("Tous les champs sont obligatoires");
        }

        TypeAdherent typeAdherent = typeAdherentRepository.findById(typeAdherentId)
                .orElseThrow(() -> new RuntimeException("Type d'adherent invalide"));

        Adherent adherent = new Adherent();
        adherent.setNom(nom);
        adherent.setPrenom(prenom);
        adherent.setTelephone(telephone);
        adherent.setTypeAdherent(typeAdherent);
        adherent.setDateNaissance(dateNaissance);
        adherentRepository.save(adherent);

        HistoriqueActiviteAdherent activite = new HistoriqueActiviteAdherent();
        activite.setAdherent(adherent);
        activite.setEstActif(1);
        historiqueActiviteAdherentRepository.save(activite);

        return "Inscription effectuee avec succes";
    }
}
