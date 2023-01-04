package fr.eni.encheres.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet(name = "Connexion", value = "/connexion")
public class ServletPageConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("utilisateur") != null){ //On ne peut se connecter que si l'utilisateur est null
			response.sendRedirect("/");
		}else{
			request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
		}
		return;
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
		user=uMgr.connecter(pseudo,motDePasse);
		if (user!=null) {
			if (user.getMotDePasse().equals(motDePasse)) {
				System.out.println("User Not Null, password matches");
				session.setAttribute("utilisateur", user);
				request.getRequestDispatcher("/accueil").forward(request, response);
			} else {
				System.out.println("User Not Null, password error");
				request.setAttribute("messageErreur", " Mot de passe incorrect");
				request.getRequestDispatcher("/connexion").forward(request, response);
			}
		}else {
			System.out.println("User Null");
			request.setAttribute("messageErreur", "Identifiant incorrect");
			request.getRequestDispatcher("/connexion").forward(request, response);
		}
		return;
	}
}
