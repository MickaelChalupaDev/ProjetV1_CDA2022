package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "helloServlet", value = "/Search")
public class HelloWorld extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Utilisateur utilisateur = null;
		List<Article> articles = new ArrayList<Article>();
		if(session.getAttribute("utilisateur") != null){
			utilisateur = (Utilisateur)session.getAttribute("utilisateur");
		}
		ObjectSentAccueil o = new ObjectSentAccueil(articles);

		request.setAttribute("obj", o);//Toujours à ignorer, j'en avais besoin pour build ma page
		request.getRequestDispatcher("PageAccueil.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
