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

@WebServlet(name = "ServletPageProfile", value = "/profile")
public class ServletPageProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        String pseudo = request.getParameter("pseudo");
        if(pseudo == null){//Afficher son propre profile
            RequestDispatcher rd = null;
            //Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            request.getRequestDispatcher("PageMonProfil.jsp").forward(request, response);
        }
        else{//Afficher le profile d'un autre
            Utilisateur user = UtilisateurManager.lireUtilisateur(pseudo);
            request.setAttribute("user", user);
            request.getRequestDispatcher("PageProfil.jsp").forward(request, response);
        }
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//modifier profile
        doGet(request, response);
        return;
    }
}
