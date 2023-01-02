package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ModifierProfile", value = "/profile/modifier")
public class ServletPageModifierProfil extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = new Utilisateur();
        UtilisateurManager uMgr = new UtilisateurManager();
        RequestDispatcher rd = null;
        user = uMgr.lireUtilisateur(request.getParameter("pseudo"));
        request.setAttribute("user", user);
        rd = request.getRequestDispatcher("/PageModifierMonProfil.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur user = new Utilisateur();
        UtilisateurManager uMgr = new UtilisateurManager();
        RequestDispatcher rd = null;
        String messageMotDePasse = null;
        String messageCompatibiliteMotDePasse = null;

        user.setNoUtlisateur(uMgr.lireUtilisateur(request.getParameter("pseudo")).getNoUtilisateur());
        user.setPseudo(request.getParameter("pseudo"));
        user.setNom(request.getParameter("nom"));
        user.setPrenom(request.getParameter("prenom"));
        user.setEmail(request.getParameter("email"));
        user.setTelephone(request.getParameter("telephone"));
        user.setRue(request.getParameter("rue"));
        user.setCodePostal(request.getParameter("codePostal"));
        user.setVille(request.getParameter("ville"));

        user.setCredit(uMgr.lireUtilisateur(request.getParameter("pseudo")).getCredit());
        user.setAdministrateur(uMgr.lireUtilisateur(request.getParameter("pseudo")).getAdministrateur());

        if (((String) request.getParameter("enregistrement").trim()).equals("Enregistrer")) {


            if (request.getParameter("motDePasse").equals(uMgr.lireUtilisateur(request.getParameter("pseudo")).getMotDePasse())) {

                if (request.getParameter("nouveauMotDePasse").equals(request.getParameter("confirmation"))) {
                    user.setMotDePasse(request.getParameter("nouveauMotDePasse"));
                    uMgr.modifierUtilisateur(user);
                    rd = request.getRequestDispatcher("/PageListeEncheresConnecte.jsp");
                    rd.forward(request, response);
                } else {
                    messageCompatibiliteMotDePasse = "saisie de mot de passe incompatible !";
                    request.setAttribute("user", user);
                    request.setAttribute("messageCompatibiliteMotDePasse", "saisie de mot de passe incompatible !");
                    rd = request.getRequestDispatcher("/PageModifierMonProfil.jsp");
                    rd.forward(request, response);
                }
            } else {
                messageMotDePasse = "mot de passe incorrect !";
                request.setAttribute("messageMotDePasse", messageMotDePasse);
                request.setAttribute("user", user);

                rd = request.getRequestDispatcher("/PageModifierMonProfil.jsp");
                rd.forward(request, response);
            }
        } else {

            /** supprimer toutes les enchères de l'utilisateur : user qui sont seulement en cours **/
            EnchereManager eMgr = new EnchereManager();
            ArticleManager aMgr = new ArticleManager();
            Enchere enchere = new Enchere();
            List<Article> articles = new ArrayList<Article>();

            articles = eMgr.rechercherParAchat(null, null, user.getNoUtilisateur(), false);

            for (Article art : articles) {
                enchere.setNoEncherisseur(user.getNoUtilisateur());
                enchere.setNoArticle(art.getNoArticle());
                eMgr.annuler(enchere);
            }

            /** supprimer tous les articles de l'utilisateur : user dont les enchères ne sont pas encore débutés**/

            articles = eMgr.rechercherParVente(null, null, user.getNoUtilisateur(), EtatVente.NonDebutee);

            for (Article art : articles) {
                aMgr.supprimerArticle(art.getNoArticle());
            }

            /** mettre le compte inactif (et ne pas supprimer ) **/


            //uMgr.supprimerUtilisateur(user);
            rd = request.getRequestDispatcher("/PageAccueil.jsp");
            rd.forward(request, response);


        }
    }

}
