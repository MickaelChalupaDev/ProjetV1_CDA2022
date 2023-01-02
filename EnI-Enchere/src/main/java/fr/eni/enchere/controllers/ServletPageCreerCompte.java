package fr.eni.encheres.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletPageCreerCompte
 */
public class ServletPageCreerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user = new Utilisateur();
		UtilisateurManager uMgr= new UtilisateurManager();
		RequestDispatcher rd = null; 
		
		user.setPseudo(request.getParameter("pseudo"));
		user.setNom(request.getParameter("nom"));
		user.setPrenom(request.getParameter("prenom"));
		user.setEmail(request.getParameter("email"));
		user.setTelephone(request.getParameter("telephone"));
		user.setRue(request.getParameter("rue"));
		user.setCodePostal(request.getParameter("codePostal"));
		user.setVille(request.getParameter("ville"));
		user.setMotDePasse(request.getParameter("motDePasse"));
		user.setCredit(0);
		user.setAdministrateur(false);
		
		
		/** Partie vérification des données : si fautes rédirection vers la page : PageCreerCompte avec messages d'erreurs correspondants **/
		/*
		 request.setAttribute("user", user);
		 RequestDispatcher rd= request.getRequestDispatcher("/PageCreerCompte.jsp");
		 rd.forward(request, response);
		 
		 */
		user = uMgr.creerUtilisateur(user); 
		if (user!=null) {
		
			rd= request.getRequestDispatcher("/PageListeEncheresConnecte.jsp");
			rd.forward(request, response);
			
		} else
		{
			 rd= request.getRequestDispatcher("/PageCreerCompte.jsp");
			 rd.forward(request, response);
			
		}
		
	}

}
