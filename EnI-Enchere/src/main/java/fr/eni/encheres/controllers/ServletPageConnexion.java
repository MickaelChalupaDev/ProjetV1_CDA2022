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

/**
 * Servlet implementation class ServletPageConnexion
 */
public class ServletPageConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String pseudo= null;
		String motDePasse=null;
		UtilisateurManager uMgr= new UtilisateurManager();
		Utilisateur user = new Utilisateur();
		pseudo= request.getParameter("pseudo");
		motDePasse= request.getParameter("motDePasse");
		 
		user=uMgr.lireUtilisateur(pseudo);
		if (user!=null) {
		if (user.getMotDePasse().equals(motDePasse)) {
		
			session.setAttribute("utilisateur", user);
			request.getRequestDispatcher("Accueil").forward(request, response);
			return;
		} else {
			
			request.setAttribute("messageErreur", " Mot de passe incorrect");
			request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
			return;
		} }else {
			request.setAttribute("messageErreur", "Identifiant incorrect");
			request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
		}
		
		
		
	}

}
