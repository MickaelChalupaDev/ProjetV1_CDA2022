package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "Accueil", value = "/")
public class ServletPageAccueil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = null;
        utilisateur = new Utilisateur(-1, "Pseudo", "Nom","Prénom", "exemple@test.com", "0600000000",
                "01 rue Test", "01010", "La ville testuaire", "", 9999, true);

        List<Article> articles = new ArrayList<Article>();
        Article articleTest = new Article(0, "Intitulé Vente", "Je suis une vente", new Date(), new Date(), 150, 150, "Etat",
                utilisateur, "Informatique", "pic",new ArrayList<String>());
        articles.add(articleTest);
        articles.add(articleTest);
        articles.add(articleTest);
        articles.add(articleTest);
        ObjectSentAccueil o = new ObjectSentAccueil(articles);
        HttpSession session = request.getSession();
        if(session.isNew() || session.getAttribute("utilisateur") == null){
            session.setAttribute("utilisateur", utilisateur);
        }

        request.setAttribute("obj", o);
        request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}