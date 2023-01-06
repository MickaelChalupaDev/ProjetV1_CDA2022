package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "Accueil", value = "/")
public class ServletPageAccueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Categorie> categories = CategorieManager.getAllCategories();

        List<Article> articles = new ArrayList<Article>();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            if(session.getAttribute("utilisateur") == null){
                session.setAttribute("utilisateur", null);
            }
        }
        if(session.isNew() || session.getAttribute("filtresRecherches") == null){
            ObjectSentAccueil o = new ObjectSentAccueil();
            session.setAttribute("filtresRecherches", o);
            articles = ArticleManager.rechercherTout(null, 0);
        }
        else{
            ObjectSentAccueil o = (ObjectSentAccueil) session.getAttribute("filtresRecherches");
            System.out.println(o.toString());
            articles = ArticleManager.rechercher((Utilisateur) session.getAttribute("utilisateur"), o);
        }
        request.setAttribute("articles",articles);
        request.setAttribute("categories",categories);
        request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String searchBox = request.getParameter("search");
        String categorie = request.getParameter("categorie");
        String filtreVenteAffichee = request.getParameter("filtreVentesAffichees");
        String encheresOuvertes = request.getParameter("encheresOuvertes");
        String mesEncheres = request.getParameter("mesEncheres");
        String encheresRemportees = request.getParameter("encheresRemportees");
        String ventesEnCours = request.getParameter("ventesEnCours");
        String ventesNonDebutees = request.getParameter("ventesNonDebutees");
        String ventesTerminees = request.getParameter("ventesTerminees");

        if(searchBox == null || session.getAttribute("utilisateur") == null){
            doGet(request,response);//Lorsqu'on vient de connexion, c'est un post malheureusement, donc je teste au cas où
            //Idem si on passe par du post quand il n'y a pas d'utilisateur set, je retourne le get
            return;
        }

        ObjectSentAccueil o = new ObjectSentAccueil(searchBox != null ? searchBox : "", categorie, filtreVenteAffichee, encheresOuvertes, mesEncheres, encheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);

        session.setAttribute("filtresRecherches", o);
        doGet(request, response);
        return;
    }
}