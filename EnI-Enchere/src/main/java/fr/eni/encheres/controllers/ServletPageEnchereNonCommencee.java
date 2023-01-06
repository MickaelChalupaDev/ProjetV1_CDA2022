package fr.eni.encheres.controllers;

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
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@MultipartConfig
@WebServlet(name = "EnchereNonCommencee", value = "/EnchereNonCommencee")
public class ServletPageEnchereNonCommencee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }
        List<Categorie> categories = CategorieManager.getAllCategories();
        categories.remove(0);
        request.setAttribute("categories",categories);

        int noArticle = 0;
        try{
            noArticle = Integer.parseInt(request.getParameter("article"));
        }catch (NumberFormatException e){
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        Article article = ArticleManager.lireArticle(noArticle);

        if(article.getVendeur() == (Utilisateur) session.getAttribute("utilisateur")){
            response.sendRedirect(request.getContextPath() + "/");
        }


        Article articleInit = new Article();

        articleInit.copy(article);

        if (article.getNomPhoto() != null) {

            String pathNameOrigin = getServletContext().getRealPath("/images/") + article.getNomPhoto();
            String pathName = String.copyValueOf(pathNameOrigin.toCharArray(), 0, pathNameOrigin.length() - 4);
            String extension = String.copyValueOf(pathNameOrigin.toCharArray(), pathNameOrigin.length() - 4, 4);
            String pathNameDestination = pathName + "-copie" + extension;
            File fileToSave = new File(pathNameOrigin);

            Files.copy(fileToSave.toPath(), Paths.get(pathNameDestination), StandardCopyOption.REPLACE_EXISTING);

            pathName = String.copyValueOf(article.getNomPhoto().toCharArray(), 0, article.getNomPhoto().length() - 4);
            extension = String.copyValueOf(article.getNomPhoto().toCharArray(), article.getNomPhoto().length() - 4, 4);
            pathNameDestination = pathName + "-copie" + extension;

            article.setNomPhoto(pathNameDestination);

//            ArticleManager.modifierArticle(article);
        }
        request.setAttribute("article", article);
        request.setAttribute("articleInit", articleInit);
        request.getRequestDispatcher("PageEnchereNonCommencee.jsp").forward(request, response);
        return;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }
        List<Categorie> categories = CategorieManager.getAllCategories();
        categories.remove(0);
        request.setAttribute("categories",categories);
        Article article = new Article();
        Article articleInit = new Article();

        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");

        article = ArticleManager.lireArticle(Integer.parseInt(request.getParameter("noArticle")));
        articleInit = ArticleManager.lireArticle(Integer.parseInt(request.getParameter("noArticleInit")));


        if (request.getParameter("creation").equals("Enregistrer")) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebutEncheres = null;
            Date dateFinEncheres = null;
            Long dateLong = System.currentTimeMillis();
            Date date = new Date();
            Date dateSql = null;
            try {
                dateSql = new java.sql.Date(sdf.parse(sdf.format(date)).getTime());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            article.setVendeur((Utilisateur) session.getAttribute("user"));
            article.setNomArticle(request.getParameter("nomArticle"));
            article.setDescription(request.getParameter("description"));
            article.setCategorie(request.getParameter("categorie"));
            article.setMiseAPrix(Integer.parseInt(request.getParameter("miseAPrix")));
            article.getAdresse().add(request.getParameter("rue"));
            article.getAdresse().add(request.getParameter("codePostal"));
            article.getAdresse().add(request.getParameter("ville"));

            try {
                dateDebutEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateDebutEncheres")).getTime());
                dateFinEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateFinEncheres")).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            article.setDateDebutEncheres(dateDebutEncheres);
            article.setDateFinEncheres(dateFinEncheres);


            // photo :

            if (!request.getPart("fileName").getSubmittedFileName().isEmpty()) {

                Part filePart = request.getPart("fileName");


                if (filePart.getSubmittedFileName().endsWith(".jpg") || filePart.getSubmittedFileName().endsWith(".png")) {

                    String pathName = null;
                    File fileToSave = null;

                    InputStream fileInputStream = filePart.getInputStream();

                    if (ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto() != null) {
                        pathName = getServletContext().getRealPath("/") + ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto();
                        fileToSave = new File(pathName);
                        fileToSave.delete();
                    }

                    String dateSave = String.valueOf(System.currentTimeMillis());
                    pathName = getServletContext().getRealPath("/images/")  + user.getNoUtilisateur() + "-" + dateSave + "-";

                    fileToSave = new File(pathName + filePart.getSubmittedFileName());
                    Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    String fileUrl = user.getNoUtilisateur() + "-" + dateSave + "-" + filePart.getSubmittedFileName();
                    article.setNomPhoto(fileUrl);
                    ArticleManager.modifierArticle(article);

                } else {

                    article.setNomPhoto(ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto());

                    request.setAttribute("article", article);
                    request.setAttribute("articleInit", articleInit);
                    request.setAttribute("messagePhoto", "Formats : jpg, png !");
                    request.getRequestDispatcher("PageEnchereNonCommencee.jsp").forward(request, response);
                    return;
                }
            }


            article.setNomPhoto(ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto());

            /*****/

            assert dateSql != null;
            if (dateSql.compareTo(dateDebutEncheres) <= 0) {
                article.setEtatVente(EtatVente.EnCours);
            } else {
                article.setEtatVente(EtatVente.NonDebutee);
                request.setAttribute("article", article);
                request.setAttribute("articleInit", articleInit);
                request.setAttribute("messageDateDebutEnchere", "Augmenter cette date !");
                request.getRequestDispatcher("PageEnchereNonCommencee.jsp").forward(request, response);
                return;

            }
            article.setDateDebutEncheres(dateDebutEncheres);

            if (date.compareTo(dateFinEncheres) >= 0 || dateDebutEncheres.compareTo(dateFinEncheres) >= 0) {

                request.setAttribute("article", article);
                request.setAttribute("articleInit", articleInit);
                request.setAttribute("messageDateFinEnchere", "Augmenter cette date !");
                request.getRequestDispatcher("PageEnchereNonCommencee.jsp").forward(request, response);
                return;
            } else {

                article.setDateFinEncheres(dateFinEncheres);


                ArticleManager.modifierArticle(article);

                if (article.getNomPhoto() != null) {


                    String pathName = getServletContext().getRealPath("/images/") + ArticleManager.lireArticle(articleInit.getNoArticle()).getNomPhoto();
                    File fileToSave = new File(pathName);

                    fileToSave.delete();
                    articleInit.copy(article);
                    ArticleManager.modifierArticle(articleInit);
                    ArticleManager.supprimerArticle(article.getNoArticle());
                    response.sendRedirect(request.getContextPath() + "/");
                    return;
                } else {
                    article.setNomPhoto(articleInit.getNomPhoto());
                    articleInit.copy(article);
                    ArticleManager.modifierArticle(articleInit);

                    ArticleManager.supprimerArticle(article.getNoArticle());
                    response.sendRedirect(request.getContextPath() + "/");
                    return;
                }
            }
        } else if (request.getParameter("creation").equals("Annuler la vente")) {

            String pathName = null;
            File fileToSave = null;

            if (ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto() != null) {

                pathName = getServletContext().getRealPath("/images/") + ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto();
                fileToSave = new File(pathName);
                fileToSave.delete();
            }

            ArticleManager.supprimerArticle(article.getNoArticle());

            if (ArticleManager.lireArticle(articleInit.getNoArticle()).getNomPhoto() != null) {
                pathName = getServletContext().getRealPath("/images/") + ArticleManager.lireArticle(articleInit.getNoArticle()).getNomPhoto();
                fileToSave = new File(pathName);
                fileToSave.delete();
            }

            ArticleManager.supprimerArticle(articleInit.getNoArticle());

            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else {
            if (ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto() != null) {
                String pathName = getServletContext().getRealPath("/images/") + ArticleManager.lireArticle(article.getNoArticle()).getNomPhoto();
                File fileToSave = new File(pathName);
                fileToSave.delete();
            }
            ArticleManager.supprimerArticle(article.getNoArticle());
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
    }
}

