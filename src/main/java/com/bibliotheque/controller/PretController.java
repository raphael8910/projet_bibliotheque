package com.bibliotheque.controller;

import com.bibliotheque.service.PretService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/bibliothecaire/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    public static class PretForm {
        private Long idAdherent;
        private Long idExemplaire;

        public Long getIdAdherent() { return idAdherent; }
        public void setIdAdherent(Long idAdherent) { this.idAdherent = idAdherent; }
        public Long getIdExemplaire() { return idExemplaire; }
        public void setIdExemplaire(Long idExemplaire) { this.idExemplaire = idExemplaire; }
    }

    @GetMapping
    public String showPretForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter en tant que bibliothécaire");
            return "redirect:/login";
        }
        model.addAttribute("pretForm", new PretForm());
        return "pret";
    }

    @PostMapping
    public String createPret(@ModelAttribute PretForm pretForm, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter en tant que bibliothécaire");
            return "redirect:/login";
        }

        Long idBibliothecaire = (Long) session.getAttribute("bibliothecaireId");
        try {
            String message = pretService.creerPret(idBibliothecaire, pretForm.getIdAdherent(), pretForm.getIdExemplaire());
            redirectAttributes.addFlashAttribute("message", message);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/bibliothecaire/pret";
    }
}