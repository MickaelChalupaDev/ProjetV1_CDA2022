package fr.eni.encheres.controllers;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "CreerCompte", value = "/creerCompte")
public class ServletPageCreerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur user = new Utilisateur();
		UtilisateurManager uMgr= new UtilisateurManager();
		RequestDispatcher rd = null;

		Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)$");
		Matcher matcherEmail = patternEmail.matcher(request.getParameter("email"));
		boolean matchesEmail = matcherEmail.matches(); //true/false
		Pattern patternMotDePasse = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,30}$");
		Matcher matcherMotDePasse = patternEmail.matcher(request.getParameter("motDePasse"));
		boolean matchesMotDePasse = matcherEmail.matches(); //true/false
		Pattern patternPseudo = Pattern.compile("^([a-zA-Z0-9-_]){3,30}$");
		Matcher matcherPseudo = patternEmail.matcher(request.getParameter("pseudo"));
		boolean matchesPseudo = matcherEmail.matches(); //true/false

		if(!(matchesEmail && matchesPseudo && matchesMotDePasse)) return;

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
