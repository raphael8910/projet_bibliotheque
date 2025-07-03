package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private DisponibiliteExemplaireRepository disponibiliteExemplaireRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private HistoriquePenaliteRepository historiquePenaliteRepository;
    @Autowired
    private HistoriqueActiviteAdherentRepository historiqueActiviteAdherentRepository;

    public String creerReservation(Long idBibliothecaire, Long idAdherent, Long idExemplaire) {
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("L'adherent n'existe pas"));

        if (!estActif(idAdherent)) {
            throw new RuntimeException("L'adherent est inactif");
        }

        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
                .orElseThrow(() -> new RuntimeException("L'exemplaire n'existe pas"));

        if (!estDisponible(idExemplaire)) {
            throw new RuntimeException("L'exemplaire n'est pas disponible");
        }

        if (possedePenalite(idAdherent)) {
            throw new RuntimeException("L'Adherent est encore en penalite");
        }

        int quota = getQuota(idAdherent);
        if (quota <= 0) {
            throw new RuntimeException("Le quota de l'adherent est depasse");
        }

        if (!compatible(idAdherent, idExemplaire)) {
            throw new RuntimeException("Le livre en question ne peut etre reserve par ce type d'adherent");
        }

        Reservation reservation = new Reservation();
        reservation.setBibliothecaire(new Bibliothecaire() {{ setId(idBibliothecaire); }});
        reservation.setAdherent(adherent);
        reservation.setExemplaire(exemplaire);
        reservation.setDateReservation(LocalDateTime.now());

        reservationRepository.save(reservation);
        faireIndisponible(idExemplaire);
        setQuota(idAdherent, quota - 1);

        return "Reservation effectuee avec succes";
    }

    private boolean estActif(Long idAdherent) {
        HistoriqueActiviteAdherent activite = historiqueActiviteAdherentRepository.findLatestByAdherentId(idAdherent);
        return activite != null && activite.getEstActif() == 1;
    }

    private boolean estDisponible(Long idExemplaire) {
        DisponibiliteExemplaire disponibilite = disponibiliteExemplaireRepository.findLatestByExemplaireId(idExemplaire);
        return disponibilite != null && disponibilite.getEstDisponible() == 1;
    }

    private boolean possedePenalite(Long idAdherent) {
        return historiquePenaliteRepository.findAll().stream()
                .anyMatch(hp -> hp.getAdherent().getId().equals(idAdherent) &&
                        hp.getDateChangementEtat().plusDays(hp.getPenalite().getNbJours()).isAfter(LocalDateTime.now()));
    }

    private int getQuota(Long idAdherent) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElseThrow();
        return adherent.getTypeAdherent().getQuota();
    }

    private boolean compatible(Long idAdherent, Long idExemplaire) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElseThrow();
        Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire).orElseThrow();
        return adherent.getTypeAdherent().getTypesLivreAutorises().contains(exemplaire.getLivre().getTypeLivre());
    }

    private void faireIndisponible(Long idExemplaire) {
        DisponibiliteExemplaire disponibilite = new DisponibiliteExemplaire();
        disponibilite.setExemplaire(new Exemplaire() {{ setId(idExemplaire); }});
        disponibilite.setEstDisponible(0);
        disponibilite.setDateChangementEtat(LocalDateTime.now());
        disponibiliteExemplaireRepository.save(disponibilite);
    }

    private void setQuota(Long idAdherent, int newQuota) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElseThrow();
        adherent.getTypeAdherent().setQuota(newQuota);
        adherentRepository.save(adherent);
    }
}
