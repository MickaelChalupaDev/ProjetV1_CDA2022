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
            response.sendRedirect("/");
        }

        UtilisateurManager uMgr = new UtilisateurManager();
        Utilisateur user = new Utilisateur();
        Utilisateur userGagnant = new Utilisateur();
        Date date = new Date(System.currentTimeMillis());

        user = uMgr.lireUtilisateur(2);

        session.setAttribute("user", user);

        Article article = new Article();

        ArticleManager aMgr = new ArticleManager();
        article = aMgr.lireArticle(1133);
        int meilleureOffre = 0;
        Enchere enchere = new Enchere();
        EnchereManager eMgr = new EnchereManager();
        //meilleureOffre= eMgr.lire(1133).getMontantEnchere();
        enchere = eMgr.lire(1133);

        if (enchere != null) {
            meilleureOffre = eMgr.lire(article.getNoArticle()).getMontantEnchere();

        } else
            meilleureOffre = aMgr.lireArticle(article.getNoArticle()).getMiseAPrix();


        request.setAttribute("meilleureOffre", meilleureOffre);

        request.setAttribute("article", article);

        java.sql.Date dateSql = new java.sql.Date(date.getTime());
        System.out.println(dateSql.compareTo(article.getDateFinEncheres()));

        if (dateSql.compareTo(article.getDateFinEncheres()) < 0) {
            request.getRequestDispatcher("PageEncherir.jsp").forward(request, response);
            return;
        } else {
            article.setPrixVente(eMgr.lire(article.getNoArticle()).getMontantEnchere());
            article.setEtatVente(EtatVente.Terminee);
            aMgr.modifierArticle(article);
            if (eMgr.lire(article.getNoArticle()).getNoEncherisseur() == ((Utilisateur) session.getAttribute("user")).getNoUtilisateur()) {
                request.getRequestDispatcher("PageAcquisition.jsp").forward(request, response);
                return;
            } else {
                userGagnant = uMgr.lireUtilisateur(eMgr.lire(article.getNoArticle()).getNoEncherisseur());

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
        EnchereManager eMgr = new EnchereManager();

        enchere.setNoArticle(Integer.parseInt(request.getParameter("noArticle")));
        enchere.setNoEncherisseur(((Utilisateur) session.getAttribute("user")).getNoUtilisateur());
        enchere.setMontantEnchere(Integer.parseInt(request.getParameter("maProposition")));
        enchere.setDateEnchere(new Date(System.currentTimeMillis()));

        eMgr.encherir(enchere);
        request.getRequestDispatcher("PageListeEncheresConnecte.jsp").forward(request, response);
    }

}
