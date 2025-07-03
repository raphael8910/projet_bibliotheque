package com.bibliotheque.controller;

import com.bibliotheque.model.Bibliothecaire;
import com.bibliotheque.repository.BibliothecaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class BibliothecaireController {

    @Autowired
    private BibliothecaireRepository bibliothecaireRepository;

    // Redirect root URL to login page
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    // Display login page
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("bibliothecaire", new Bibliothecaire());
        return "login-bibliothecaire";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@ModelAttribute Bibliothecaire bibliothecaire, HttpSession session, RedirectAttributes redirectAttributes) {
        Bibliothecaire foundBibliothecaire = bibliothecaireRepository.findByPseudo(bibliothecaire.getPseudo());
        if (foundBibliothecaire != null && foundBibliothecaire.getMotdepasse().equals(bibliothecaire.getMotdepasse())) {
            // Store Bibliothecaire ID in session
            session.setAttribute("bibliothecaireId", foundBibliothecaire.getId());
            return "redirect:/admin/bibliothecaire/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Pseudo ou mot de passe incorrect");
            return "redirect:/login";
        }
    }

    // Logout and clear session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Accueil page, accessible only if logged in
    @GetMapping("/admin/bibliothecaire/")
    public String accueil(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        return "accueil";
    }

    @GetMapping("/admin/bibliothecaire/list")
    public String listBibliothecaires(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        model.addAttribute("bibliothecaires", bibliothecaireRepository.findAll());
        return "list-bibliothecaire";
    }

    @GetMapping("/admin/bibliothecaire/insert")
    public String showInsertForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        model.addAttribute("bibliothecaire", new Bibliothecaire());
        return "insertion-bibliothecaire";
    }

    @PostMapping("/admin/bibliothecaire/insert")
    public String insertBibliothecaire(@ModelAttribute Bibliothecaire bibliothecaire, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        bibliothecaireRepository.save(bibliothecaire);
        redirectAttributes.addFlashAttribute("message", "Bibliothecaire ajouté avec succès");
        return "redirect:/admin/bibliothecaire/list";
    }

    @GetMapping("/admin/bibliothecaire/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        Bibliothecaire bibliothecaire = bibliothecaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bibliothecaire introuvable"));
        model.addAttribute("bibliothecaire", bibliothecaire);
        return "modif-bibliothecaire";
    }

    @PostMapping("/admin/bibliothecaire/edit/{id}")
    public String updateBibliothecaire(@PathVariable Long id, @ModelAttribute Bibliothecaire bibliothecaire, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        bibliothecaire.setId(id);
        bibliothecaireRepository.save(bibliothecaire);
        redirectAttributes.addFlashAttribute("message", "Bibliothecaire modifié avec succès");
        return "redirect:/admin/bibliothecaire/list";
    }

    @GetMapping("/admin/bibliothecaire/delete/{id}")
    public String deleteBibliothecaire(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaireId") == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
            return "redirect:/login";
        }
        bibliothecaireRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Bibliothecaire supprimé avec succès");
        return "redirect:/admin/bibliothecaire/list";
    }
}