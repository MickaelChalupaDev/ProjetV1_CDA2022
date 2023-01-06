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
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Date date = new Date(System.currentTimeMillis());

        int noArticle = 0;
        try{
            noArticle = Integer.parseInt(request.getParameter("noArticle"));
        }catch (NumberFormatException e){
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        Article article = ArticleManager.lireArticle(noArticle);
        Utilisateur currUser = (Utilisateur) session.getAttribute("utilisateur");
        if(article.getVendeur().getNoUtilisateur() == currUser.getNoUtilisateur()){
            response.sendRedirect(request.getContextPath() + "/EnchereNonCommencee?article="+article.getNoArticle());
            return;
        }


        int meilleureOffre = 0;
        Enchere enchere = EnchereManager.lire(noArticle);

        if (enchere != null) {
            meilleureOffre = enchere.getMontantEnchere();
            if(meilleureOffre == 0){
                meilleureOffre = ArticleManager.lireArticle(article.getNoArticle()).getMiseAPrix();
            }
        } else {
            meilleureOffre = ArticleManager.lireArticle(article.getNoArticle()).getMiseAPrix();
        }

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
            if (EnchereManager.lire(article.getNoArticle()).getNoEncherisseur() == ((Utilisateur) session.getAttribute("utilisateur")).getNoUtilisateur()) {
                request.getRequestDispatcher("PageAcquisition.jsp").forward(request, response);
                return;
            } else {
                Utilisateur userGagnant = UtilisateurManager.lireUtilisateur(EnchereManager.lire(article.getNoArticle()).getNoEncherisseur());

                request.setAttribute("userGagnant", userGagnant);

                request.getRequestDispatcher("PageDetailMaVenteFinEnchere.jsp").forward(request, response);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Enchere enchere = new Enchere();

        enchere.noEnchere = 0;//pas encore de numéro d'enchère
        enchere.setNoArticle(Integer.parseInt(request.getParameter("noArticle")));
        enchere.setNoEncherisseur(((Utilisateur) session.getAttribute("utilisateur")).getNoUtilisateur());
        enchere.setMontantEnchere(Integer.parseInt(request.getParameter("maProposition")));
        enchere.setDateEnchere(new Date(System.currentTimeMillis()));
        EnchereManager.encherir(enchere);
        response.sendRedirect(request.getContextPath() + "/");
        return;
    }

}
