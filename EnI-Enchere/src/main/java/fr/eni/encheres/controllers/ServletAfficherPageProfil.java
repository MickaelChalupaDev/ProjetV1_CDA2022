package fr.eni.encheres.controllers;

import java.io.IOException;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletAfficherPageProfil
 */
public class ServletAfficherPageProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur user = new Utilisateur();
		Utilisateur userConnected = new Utilisateur();
		UtilisateurManager uMgr= new UtilisateurManager();
		System.out.println((String) request.getParameter("pseudo"));
		user = uMgr.lireUtilisateur((String) request.getParameter("pseudo"));
		
		userConnected=(Utilisateur) request.getSession().getAttribute("utilisateur");
		
	
		if (userConnected !=null) {
		if (user.getPseudo().equals(userConnected.getPseudo())) 
		{
			request.getRequestDispatcher("/PageMonProfil.jsp").forward(request, response);
			return; 
		}} 
			request.setAttribute("user", user);
		    request.getRequestDispatcher("/PageProfil.jsp").forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
