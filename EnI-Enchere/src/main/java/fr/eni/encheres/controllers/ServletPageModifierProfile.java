package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletPageModifierProfile", value = "/modifierProfile")
public class ServletPageModifierProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        request.getRequestDispatcher("/PageModifierProfil.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = new Utilisateur();
        String messageMotDePasse = null;
        String messageCompatibiliteMotDePasse = null;

        user.setNoUtlisateur(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getNoUtilisateur());
        user.setPseudo(request.getParameter("pseudo"));
        user.setNom(request.getParameter("nom"));
        user.setPrenom(request.getParameter("prenom"));
        user.setEmail(request.getParameter("email"));
        user.setTelephone(request.getParameter("telephone"));
        user.setRue(request.getParameter("rue"));
        user.setCodePostal(request.getParameter("codePostal"));
        user.setVille(request.getParameter("ville"));

        user.setCredit(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getCredit());
        user.setAdministrateur(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getAdministrateur());

        if (((String) request.getParameter("enregistrement").trim()).equals("Enregistrer")) {


            if (request.getParameter("motDePasse").equals(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getMotDePasse())) {

                if (request.getParameter("nouveauMotDePasse").equals(request.getParameter("confirmation"))) {
                    user.setMotDePasse(request.getParameter("nouveauMotDePasse"));
                    UtilisateurManager.modifierUtilisateur(user);
                    response.sendRedirect(request.getContextPath() + "/");
                    return;
                } else {
                    messageCompatibiliteMotDePasse = "saisie de mot de passe incompatible !";
                    request.setAttribute("user", user);
                    request.setAttribute("messageCompatibiliteMotDePasse", "saisie de mot de passe incompatible !");
                    doGet(request, response);
                    return;
                }
            } else {
                messageMotDePasse = "mot de passe incorrect !";
                request.setAttribute("messageMotDePasse", messageMotDePasse);
                request.setAttribute("user", user);

                doGet(request,response);
                return;
            }
        } else {

            /** supprimer toutes les enchères de l'utilisateur : user qui sont seulement en cours **/
            Enchere enchere = new Enchere();
            List<Article> articles = new ArrayList<Article>();

            articles = ArticleManager.rechercherParAchat(null, 0, user.getNoUtilisateur(), false);

            for (Article art : articles) {
                enchere.setNoEncherisseur(user.getNoUtilisateur());
                enchere.setNoArticle(art.getNoArticle());
                EnchereManager.annuler(enchere);
            }

            /** supprimer tous les articles de l'utilisateur : user dont les enchères ne sont pas encore débutés**/

            articles = ArticleManager.rechercherParVente(null, 0, user.getNoUtilisateur(), EtatVente.NonDebutee);

            for (Article art : articles) {
                ArticleManager.supprimerArticle(art.getNoArticle());
            }

            /** mettre le compte inactif (et ne pas supprimer ) **/
            //uMgr.supprimerUtilisateur(user);
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
    }
}
