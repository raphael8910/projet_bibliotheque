package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ProlongationService {

    @Autowired
    private AdherentRepository adherentRepository;
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private PrevisionRetourRepository previsionRetourRepository;
    @Autowired
    private ProlongationRepository prolongationRepository;
    @Autowired
    private HistoriqueActiviteAdherentRepository historiqueActiviteAdherentRepository;

    public String prolongerPret(Long idPret, Long idAdherent) {
        Adherent adherent = adherentRepository.findById(idAdherent)
                .orElseThrow(() -> new RuntimeException("L'adherent n'existe pas"));

        if (!estActif(idAdherent)) {
            throw new RuntimeException("L'adherent est inactif");
        }

        Pret pret = pretRepository.findById(idPret)
                .orElseThrow(() -> new RuntimeException("Le pret n'existe pas"));

        int quota = getQuota(idAdherent);
        if (quota <= 0) {
            throw new RuntimeException("Le quota de prolongement est depasse");
        }

        Prolongation prolongation = new Prolongation();
        prolongation.setPret(pret);
        prolongation.setDate(LocalDateTime.now());
        prolongationRepository.save(prolongation);

        PrevisionRetour prevision = previsionRetourRepository.findByPretId(idPret)
                .orElseThrow(() -> new RuntimeException("Prevision de retour introuvable"));
        prevision.setDateRetourPrevue(LocalDateTime.now().plusDays(adherent.getTypeAdherent().getNbJours()));
        previsionRetourRepository.save(prevision);

        setQuota(idAdherent, quota - 1);

        return "Prolongation effectuee avec succes";
    }

    private boolean estActif(Long idAdherent) {
        HistoriqueActiviteAdherent activite = historiqueActiviteAdherentRepository.findLatestByAdherentId(idAdherent);
        return activite != null && activite.getEstActif() == 1;
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
