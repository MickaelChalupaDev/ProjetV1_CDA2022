package fr.eni.encheres.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;

/**
 * Servlet implementation class Deconnexion
 */
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager eMgr = new EnchereManager();
        HttpSession session = request.getSession();
    	List<Article> articles = new ArrayList<Article>();
    	Utilisateur user= new Utilisateur();
       
    	articles = eMgr.rechercherTout(null, null); 
        ObjectSentAccueil o = new ObjectSentAccueil(articles);
		user=null;
		session.setAttribute("utilisateur", user);
   	 	articles = eMgr.rechercherTout(null, null); 
   	    request.setAttribute("obj", o);
        request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
