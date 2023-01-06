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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "CreerCompte", value = "/creerCompte")
public class ServletPageCreerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("utilisateur") != null){ //On ne peut se connecter que si l'utilisateur est null
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		request.getRequestDispatcher("PageCreerCompte.jsp").forward(request, response);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("utilisateur") != null){ //On ne peut se connecter que si l'utilisateur est null
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		Utilisateur user = new Utilisateur();

		Pattern patternEmail = Pattern.compile("^([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)$");
		Matcher matcherEmail = patternEmail.matcher(request.getParameter("email"));
		boolean matchesEmail = matcherEmail.matches(); //true/false
		Pattern patternMotDePasse = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,30}$");
		Matcher matcherMotDePasse = patternEmail.matcher(request.getParameter("motDePasse"));
		boolean matchesMotDePasse = matcherEmail.matches(); //true/false
		Pattern patternPseudo = Pattern.compile("^([a-zA-Z0-9-_]){3,30}$");
		Matcher matcherPseudo = patternEmail.matcher(request.getParameter("pseudo"));
		boolean matchesPseudo = matcherEmail.matches(); //true/false

		/*Validation des règles*/
		System.out.println(request.getParameter("email") + (matchesEmail ? "yes" :"no" ) + " "
		+ request.getParameter("motDePasse") + (matchesMotDePasse ? "yes" : "no") + " "
		+ request.getParameter("pseudo") + (matchesPseudo ? "yes" : "no"));

		if(!(matchesEmail && matchesPseudo && matchesMotDePasse)) request.getRequestDispatcher("/creerCompte").forward(request, response);
		/*Validation des règles*/

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
		user = UtilisateurManager.creerUtilisateur(user);
		if (user!=null) {
			System.out.println(user.toString());
			session.setAttribute("utilisateur", user);
			response.sendRedirect(request.getContextPath() + "/");
			return;
		} else
		{
			System.out.println("No User, try again");
			request.getRequestDispatcher(request.getContextPath() + "/creerCompte").forward(request, response);
			return;
		}
		
	}

}
