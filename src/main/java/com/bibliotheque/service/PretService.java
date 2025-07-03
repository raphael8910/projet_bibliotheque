package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PretService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private DisponibiliteExemplaireRepository disponibiliteExemplaireRepository;
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private PrevisionRetourRepository previsionRetourRepository;
    @Autowired
    private HistoriquePenaliteRepository historiquePenaliteRepository;
    @Autowired
    private HistoriqueActiviteAdherentRepository historiqueActiviteAdherentRepository;

    public String creerPret(Long idBibliothecaire, Long idAdherent, Long idExemplaire) {
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
            throw new RuntimeException("Le livre en question ne peut etre emprunter par ce type d'adherent");
        }

        Pret pret = new Pret();
        pret.setBibliothecaire(new Bibliothecaire() {{ setId(idBibliothecaire); }});
        pret.setAdherent(adherent);
        pret.setExemplaire(exemplaire);
        pret.setDatePret(LocalDateTime.now());

        pretRepository.save(pret);
        faireIndisponible(idExemplaire);
        setQuota(idAdherent, quota - 1);
        ajouterPrevisionRetour(pret, LocalDateTime.now().plusDays(adherent.getTypeAdherent().getNbJours()));

        return "Pret effectue avec succes";
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

    private void ajouterPrevisionRetour(Pret pret, LocalDateTime dateRetourPrevue) {
        PrevisionRetour prevision = new PrevisionRetour();
        prevision.setPret(pret);
        prevision.setDateRetourPrevue(dateRetourPrevue);
        previsionRetourRepository.save(prevision);
    }
}
