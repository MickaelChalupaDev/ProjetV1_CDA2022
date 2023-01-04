package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "Encherir", value = "/encherir")
public class ServletPageEncherir extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/");
        }

        Utilisateur user = new Utilisateur();
        Utilisateur userGagnant = new Utilisateur();
        Date date = new Date(System.currentTimeMillis());

        user = UtilisateurManager.lireUtilisateur(2);

        session.setAttribute("user", user);

        Article article = new Article();

        article = ArticleManager.lireArticle(1133);
        int meilleureOffre = 0;
        Enchere enchere = new Enchere();

        //meilleureOffre= eMgr.lire(1133).getMontantEnchere();
        enchere = EnchereManager.lire(1133);

        if (enchere != null) {
            meilleureOffre = EnchereManager.lire(article.getNoArticle()).getMontantEnchere();

        } else
            meilleureOffre = ArticleManager.lireArticle(article.getNoArticle()).getMiseAPrix();


        request.setAttribute("meilleureOffre", meilleureOffre);

        request.setAttribute("article", article);

        java.sql.Date dateSql = new java.sql.Date(date.getTime());
        System.out.println(dateSql.compareTo(article.getDateFinEncheres()));

        if (dateSql.compareTo(article.getDateFinEncheres()) < 0) {
            request.getRequestDispatcher("PageEncherir.jsp").forward(request, response);
            return;
        } else {
            article.setPrixVente(EnchereManager.lire(article.getNoArticle()).getMontantEnchere());
            article.setEtatVente(EtatVente.Terminee);
            ArticleManager.modifierArticle(article);
            if (EnchereManager.lire(article.getNoArticle()).getNoEncherisseur() == ((Utilisateur) session.getAttribute("user")).getNoUtilisateur()) {
                request.getRequestDispatcher("PageAcquisition.jsp").forward(request, response);
                return;
            } else {
                userGagnant = UtilisateurManager.lireUtilisateur(EnchereManager.lire(article.getNoArticle()).getNoEncherisseur());

                request.setAttribute("userGagnant", userGagnant);

                request.getRequestDispatcher("PageDetailMaVenteFinEnchere.jsp").forward(request, response);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Enchere enchere = new Enchere();

        enchere.setNoArticle(Integer.parseInt(request.getParameter("noArticle")));
        enchere.setNoEncherisseur(((Utilisateur) session.getAttribute("user")).getNoUtilisateur());
        enchere.setMontantEnchere(Integer.parseInt(request.getParameter("maProposition")));
        enchere.setDateEnchere(new Date(System.currentTimeMillis()));

        EnchereManager.encherir(enchere);
        request.getRequestDispatcher("PageListeEncheresConnecte.jsp").forward(request, response);
    }

}
