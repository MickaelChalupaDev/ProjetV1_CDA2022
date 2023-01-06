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
import java.util.Objects;

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
        HttpSession session = request.getSession();
        if(session.getAttribute("utilisateur") == null){ //On ne peut se connecter que si l'utilisateur est null
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        String messageMotDePasse = null;
        String messageCompatibiliteMotDePasse = null;
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        if(request.getParameter("pseudo") != null && !Objects.equals(request.getParameter("pseudo"), "")){
            user.setPseudo(request.getParameter("pseudo"));
        }
        if(request.getParameter("nom") != null && !Objects.equals(request.getParameter("nom"), "")){
            user.setNom(request.getParameter("nom"));
        }
        if(request.getParameter("prenom") != null && !Objects.equals(request.getParameter("prenom"), ""))
        {
            user.setPrenom(request.getParameter("prenom"));
        }
        if(request.getParameter("email") != null && !Objects.equals(request.getParameter("email"), "")){
            user.setEmail(request.getParameter("email"));
        }
        if(request.getParameter("telephone") != null && !Objects.equals(request.getParameter("telephone"), "")){
            user.setTelephone(request.getParameter("telephone"));
        }
        if(request.getParameter("rue") != null && !Objects.equals(request.getParameter("rue"), ""))
        {
            user.setRue(request.getParameter("rue"));
        }
        if(request.getParameter("codePostal") != null && !Objects.equals(request.getParameter("codePostal"), "")){
            user.setCodePostal(request.getParameter("codePostal"));
        }
        if(request.getParameter("ville") != null && request.getParameter("ville") != "")
        {
            user.setVille(request.getParameter("ville"));
        }

        user.setCredit(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getCredit());
        user.setAdministrateur(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getAdministrateur());

        System.out.println(((String) request.getParameter("enregistrement").trim()).equals("Enregistrer"));
        
        if (((String) request.getParameter("enregistrement").trim()).equals("Enregistrer")) {

System.out.println(request.getParameter("motDePasse").equals(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getMotDePasse()));
System.out.println(request.getParameter("motDePasse"));
            if (request.getParameter("motDePasse").equals(UtilisateurManager.lireUtilisateur(request.getParameter("pseudo")).getMotDePasse())) {

                if (request.getParameter("nouveauMotDePasse").equals(request.getParameter("confirmation"))) {
                    String regexPassword = "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$";
                    user.setMotDePasse(request.getParameter("nouveauMotDePasse"));
                    UtilisateurManager.modifierUtilisateur(user);
                    session.setAttribute("utilisateur",user);
                    response.sendRedirect(request.getContextPath() + "/");
                    return;
                } else {
                    messageCompatibiliteMotDePasse = "saisie de mot de passe incompatible !";
                    request.setAttribute("user", user);
                    request.setAttribute("messageCompatibiliteMotDePasse", "saisie de mot de passe incompatible !");
                    request.getRequestDispatcher("/PageModifierProfil.jsp").forward(request, response);
                    return;
                }
            } else {
                messageMotDePasse = "mot de passe incorrect !";
                request.setAttribute("messageMotDePasse", messageMotDePasse);
                request.setAttribute("user", user);

                request.getRequestDispatcher("/PageModifierProfil.jsp").forward(request, response);
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


            UtilisateurManager.supprimerUtilisateur(user);
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
    }
}
