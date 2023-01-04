package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AfficherProfile", value = "/afficherProfile")
public class ServletAfficherPageProfil extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.isNew() || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/");
        }
        Utilisateur user = new Utilisateur();
        Utilisateur userConnected = new Utilisateur();
        user = UtilisateurManager.lireUtilisateur((String) request.getParameter("pseudo"));
        userConnected = UtilisateurManager.lireUtilisateur("Npseudo");


        if (user.getPseudo().equals(userConnected.getPseudo())) {
            request.setAttribute("user", user);
            RequestDispatcher rd = request.getRequestDispatcher("/PageMonProfil.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("user", user);
            RequestDispatcher rd = request.getRequestDispatcher("/PageProfil.jsp");
            rd.forward(request, response);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
