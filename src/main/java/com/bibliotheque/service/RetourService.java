package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class RetourService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private RetourRepository retourRepository;
    @Autowired
    private DisponibiliteExemplaireRepository disponibiliteExemplaireRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public String retournerLivre(Long idBibliothecaire, Long idAdherent, Long idPret) {
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("L'adherent n'existe pas"));

        Pret pret = pretRepository.findById(idPret)
                .orElseThrow(() -> new RuntimeException("Le pret n'existe pas"));

        Retour retour = new Retour();
        retour.setBibliothecaire(new Bibliothecaire() {{ setId(idBibliothecaire); }});
        retour.setPret(pret);
        retour.setDateRetour(LocalDateTime.now());
        retourRepository.save(retour);

        Long idExemplaire = pret.getExemplaire().getId();
        if (!hasActiveReservation(idExemplaire)) {
            rendreDisponible(idExemplaire);
        }

        int quota = getQuota(idAdherent);
        setQuota(idAdherent, quota + 1);

        return "Retour effectue avec succes";
    }

    private boolean hasActiveReservation(Long idExemplaire) {
        return reservationRepository.findAll().stream()
                .anyMatch(r -> r.getExemplaire().getId().equals(idExemplaire) &&
                        r.getDateReservation().isAfter(LocalDateTime.now()));
    }

    private void rendreDisponible(Long idExemplaire) {
        DisponibiliteExemplaire disponibilite = new DisponibiliteExemplaire();
        disponibilite.setExemplaire(new Exemplaire() {{ setId(idExemplaire); }});
        disponibilite.setEstDisponible(1);
        disponibilite.setDateChangementEtat(LocalDateTime.now());
        disponibiliteExemplaireRepository.save(disponibilite);
    }

    private int getQuota(Long idAdherent) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElseThrow();
        return adherent.getTypeAdherent().getQuota();
    }

    private void setQuota(Long idAdherent, int newQuota) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElseThrow();
        adherent.getTypeAdherent().setQuota(newQuota);
        adherentRepository.save(adherent);
    }
}
