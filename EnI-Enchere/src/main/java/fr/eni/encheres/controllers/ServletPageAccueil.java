package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;
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

@WebServlet(name = "Accueil", value = "/")
public class ServletPageAccueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticleManager aMgr = new ArticleManager();
        HttpSession session = request.getSession();
        List<Article> articles = new ArrayList<Article>();
        Utilisateur user= new Utilisateur();

        articles = aMgr.rechercherTout(null, null);


        ObjectSentAccueil o = new ObjectSentAccueil(articles);

        /*   if (session.getAttribute("utilisateur")== null) {
        	user=null;
        	session.setAttribute("utilisateur", user);

        } */
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            session.setAttribute("utilisateur", null);
        }


        request.setAttribute("obj", o);
        request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}