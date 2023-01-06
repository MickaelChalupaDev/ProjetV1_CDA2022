package fr.eni.encheres.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class ServletPageVendreUnArticle
 */
@MultipartConfig
@WebServlet(name = "Vendre", value = "/vendre")
public class ServletPageVendreUnArticle extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        Article article = new Article();
        List<Categorie> categories = CategorieManager.getAllCategories();
        categories.remove(0);
        request.setAttribute("categories",categories);
        request.setAttribute("article", article);
        request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
        return;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        List<Categorie> categories = CategorieManager.getAllCategories();
        categories.remove(0);
        request.setAttribute("categories",categories);
        Article article = new Article();
        article.setNoArticle(Integer.parseInt(request.getParameter("noArticle")));

        if (request.getParameter("creation").equals("Enregistrer")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebutEncheres = null;
            Date dateFinEncheres = null;
            Date date = new Date(System.currentTimeMillis());


            article.setVendeur(user);
            article.setNomArticle(request.getParameter("nomArticle"));
            article.setDescription(request.getParameter("description"));
            article.setCategorie(request.getParameter("categorie"));
            article.setMiseAPrix(Integer.parseInt(request.getParameter("miseAPrix")));
            article.setEtatVente(EtatVente.NonDebutee);

            article.getAdresse().add(request.getParameter("rue"));
            article.getAdresse().add(request.getParameter("codePostal"));
            article.getAdresse().add(request.getParameter("ville"));

            try {
                dateDebutEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateDebutEncheres")).getTime());
                dateFinEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateFinEncheres")).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }/*
		try {
			dateDebutEncheres = sdf.parse(request.getParameter("dateDebutEncheres"));
			dateFinEncheres = sdf.parse(request.getParameter("dateFinEncheres"));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
            article.setDateDebutEncheres(dateDebutEncheres);
            article.setDateFinEncheres(dateFinEncheres);

            // photo :

            if (!request.getPart("fileName").getSubmittedFileName().isEmpty()) {

                Part filePart = request.getPart("fileName");


                if (filePart.getSubmittedFileName().endsWith(".jpg") || filePart.getSubmittedFileName().endsWith(".png")) {

                    InputStream fileInputStream = filePart.getInputStream();

                    if (article.getNoArticle() == 0) {

                        String dateSave = String.valueOf(System.currentTimeMillis());
                        String pathName = getServletContext().getRealPath("/images/")+ user.getNoUtilisateur() + "-" + dateSave + "-";

                        File fileToSave = new File(pathName + filePart.getSubmittedFileName());
                        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        String fileUrl = user.getNoUtilisateur() + "-" + dateSave + "-" + filePart.getSubmittedFileName();
                        article.setNomPhoto(fileUrl);

                        ArticleManager.creationArticle(article);


                    } else {

                        String pathName = getServletContext().getRealPath("/") + ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto();
                        File fileToSave = new File(pathName);
                        fileToSave.delete();

                        String dateSave = String.valueOf(System.currentTimeMillis());
                        pathName =  getServletContext().getRealPath("/images/")+ user.getNoUtilisateur() + "-" + dateSave + "-";

                        fileToSave = new File(pathName + filePart.getSubmittedFileName());
                        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        String fileUrl = user.getNoUtilisateur() + "-" + dateSave + "-" + filePart.getSubmittedFileName();
                        article.setNomPhoto(fileUrl);
                        ArticleManager.modifierArticle(article);
                    }
                } else {
                    if (article.getNoArticle() != 0) {
                        article.setNomPhoto(ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto());
                    }
                    request.setAttribute("article", article);
                    request.setAttribute("messagePhoto", "Formats : jpg, png !");
                    request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
                    return;

                }
            }

            if (article.getNoArticle() != 0) {
                article.setNomPhoto(ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto());
            }
            /*****/
            if (date.compareTo(dateDebutEncheres) >= 0) {
                article.setEtatVente(EtatVente.EnCours);
            } else {
                article.setEtatVente(EtatVente.NonDebutee);
            }
            article.setDateDebutEncheres(dateDebutEncheres);

            if (date.compareTo(dateFinEncheres) >= 0) {

                request.setAttribute("article", article);
                request.setAttribute("messageDateFinEnchere", "Augmenter cette date !");
                request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
                return;
            } else {

                article.setDateFinEncheres(dateFinEncheres);


                if (article.getNoArticle() == 0) {

                    ArticleManager.creationArticle(article);
                } else {
                    article.setNomPhoto(ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto());
                    ArticleManager.modifierArticle(article);
                }
                response.sendRedirect(request.getContextPath() + "/");

            }
        } else {
            if (article.getNoArticle() == 0) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            } else {
                String pathName = getServletContext().getRealPath("/images/") + ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto();
                File fileToSave = new File(pathName);
                fileToSave.delete();
                ArticleManager.supprimerArticle(article.getNoArticle());
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
        }
        return;
    }
}
