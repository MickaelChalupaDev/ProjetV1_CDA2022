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
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		request.getRequestDispatcher("PageConnexion.jsp").forward(request, response);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("utilisateur") != null){ //On ne peut se connecter que si l'utilisateur est null
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		String pseudo= null;
		String motDePasse=null;
		Utilisateur user = new Utilisateur();
		pseudo= request.getParameter("pseudo");
		if(pseudo == null){//Fix des erreurs de requêtes post/get
			doGet(request, response);
			return;
		}
		motDePasse= request.getParameter("motDePasse");
		user=UtilisateurManager.connecter(pseudo,motDePasse);//hash dans le login
		if (user != null) { //Le password est hash dans le login, la vérification se fait en base directement
			System.out.println("User Not Null, password matches");
			session.setAttribute("utilisateur", user);
			response.sendRedirect(request.getContextPath() + "/");//Redirige sur l'accueil
			return;
		}else { //S'il s'est trompé, on lui renvoie l'erreur d'identifiants incorrecte sans + de précisions
			System.out.println("User Null");
			request.setAttribute("messageErreur", "Identifiants incorrect");
			doGet(request, response);//Renvoie la page du get
			return;
		}
	}
}
