package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Deconnexion", value = "/Deconnexion")
public class ServletPageDeconnexion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EnchereManager eMgr = new EnchereManager();
        HttpSession session = request.getSession();
        List<Article> articles = new ArrayList<Article>();
        Utilisateur user= new Utilisateur();

        articles = eMgr.rechercherTout(null, null);
        ObjectSentAccueil o = new ObjectSentAccueil(articles);
        user=null;
        session.setAttribute("utilisateur", user);
        request.setAttribute("obj", o);
        request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
