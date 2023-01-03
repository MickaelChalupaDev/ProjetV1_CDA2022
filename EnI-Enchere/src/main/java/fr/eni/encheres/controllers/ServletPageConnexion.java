package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ServletPageConnexion", value = "/ServletPageConnexion")
public class ServletPageConnexion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pseudo= null;
        String motDePasse=null;
        UtilisateurManager uMgr= new UtilisateurManager();
        Utilisateur user = new Utilisateur();
        pseudo= request.getParameter("pseudo");
        motDePasse= request.getParameter("motDePasse");

        user=uMgr.lireUtilisateur(pseudo);

        if (user.getMotDePasse().equals(motDePasse)) {
            session.setAttribute("utilisateur", user);
            request.getRequestDispatcher("Accueil").forward(request, response);
        } else {
            user=null;
            session.setAttribute("utilisateur", user);
            request.getRequestDispatcher("Accueil").forward(request, response);
        }
    }
}
