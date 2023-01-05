package fr.eni.encheres.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class ServletPageVendreUnArticle
 */
@MultipartConfig
public class ServletPageVendreUnArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager uMgr = new UtilisateurManager();
		Utilisateur user = new Utilisateur();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("utilisateur")==null) {
			request.getRequestDispatcher("Accueil").forward(request, response);
			return;
		} else {
			Article article = new Article();
			request.setAttribute("article", article);
			request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticleManager aMgr = new ArticleManager();
		Article article = new Article();
		Utilisateur user= new Utilisateur();
		HttpSession session = request.getSession();
		user = (Utilisateur) session.getAttribute("utilisateur");
		article.setNoArticle(Integer.valueOf(request.getParameter("noArticle")));
		
		
		
	  if (request.getParameter("creation").equals("Enregistrer"))
		{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDebutEncheres=null;
		Date dateFinEncheres=null;
		Date date= new Date(System.currentTimeMillis());
	
		
		article.setVendeur(user);
		article.setNomArticle(request.getParameter("nomArticle"));
		article.setDescription(request.getParameter("description"));
		article.setCategorie(request.getParameter("categorie"));
		article.setMiseAPrix(Integer.valueOf(request.getParameter("miseAPrix")));
		article.setEtatVente(EtatVente.NonDebutee);
		
		article.getAdresse().add(request.getParameter("rue"));
		article.getAdresse().add(request.getParameter("codePostal"));
		article.getAdresse().add(request.getParameter("ville"));
		
		try {
			dateDebutEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateDebutEncheres")).getTime());
			dateFinEncheres = new java.sql.Date(sdf.parse(request.getParameter("dateFinEncheres")).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}/*
		try {
			dateDebutEncheres = sdf.parse(request.getParameter("dateDebutEncheres"));
			dateFinEncheres = sdf.parse(request.getParameter("dateFinEncheres"));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		article.setDateDebutEncheres(dateDebutEncheres);
		article.setDateFinEncheres(dateFinEncheres);
		
		// photo :
						
		if (!request.getPart("fileName").getSubmittedFileName().isEmpty()) {
			
			Part filePart = request.getPart("fileName");
			
			
			if(filePart.getSubmittedFileName().endsWith(".jpg") || filePart.getSubmittedFileName().endsWith(".png")){
		
				InputStream fileInputStream = filePart.getInputStream();
							
				if (article.getNoArticle()==0) {
					
					String dateSave = String.valueOf(System.currentTimeMillis());
					String pathName = getServletContext().getRealPath("/")+"/photos/"+user.getNoUtilisateur()+"-"+dateSave+"-";
													
					File fileToSave = new File(pathName+filePart.getSubmittedFileName());
					Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
										
					String fileUrl = "/photos/" + user.getNoUtilisateur() + "-" + dateSave + "-" + filePart.getSubmittedFileName();
					article.setNomPhoto(fileUrl);
					
					aMgr.creationArticle(article);
					
					
				}else {
					
					String pathName = getServletContext().getRealPath("/")+aMgr.lireArticle(article.getNoArticle()).getNomPhoto();
					File fileToSave = new File(pathName);
					fileToSave.delete();
					
					String dateSave = String.valueOf(System.currentTimeMillis());
					pathName = getServletContext().getRealPath("/")+"/photos/"+user.getNoUtilisateur()+"-"+dateSave+"-";
													
					fileToSave = new File(pathName+filePart.getSubmittedFileName());
					Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
										
					String fileUrl = "/photos/" + user.getNoUtilisateur() + "-" + dateSave + "-" + filePart.getSubmittedFileName();
					article.setNomPhoto(fileUrl);
					aMgr.modifierArticle(article);
				}
				}
				 else {
					 if (article.getNoArticle()!=0) {
							article.setNomPhoto(aMgr.lireArticle(article.getNoArticle()).getNomPhoto());
						}
				request.setAttribute("article", article);
				request.setAttribute("messagePhoto", "Formats : jpg, png !");
				request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
				return;
				
			}}
		
		if (article.getNoArticle()!=0) {
			article.setNomPhoto(aMgr.lireArticle(article.getNoArticle()).getNomPhoto());
		}
		/*****/
		if (date.compareTo(dateDebutEncheres)>=0) {
					article.setEtatVente(EtatVente.EnCours);
			} else {
				article.setEtatVente(EtatVente.NonDebutee);
			}
		article.setDateDebutEncheres(dateDebutEncheres);
		
		if ( date.compareTo(dateFinEncheres)>=0) {
			
			request.setAttribute("article", article);
			request.setAttribute("messageDateFinEnchere", "Augmenter cette date !");
			request.getRequestDispatcher("PageVendreUnArticle.jsp").forward(request, response);
			return;
		} else {
			
		article.setDateFinEncheres(dateFinEncheres);
		
		
		if (article.getNoArticle()==0) {
			
			aMgr.creationArticle(article);
		}else {
			article.setNomPhoto(aMgr.lireArticle(article.getNoArticle()).getNomPhoto());
			aMgr.modifierArticle(article);
		}
	   request.getRequestDispatcher("Accueil").forward(request, response);
		
		}
	}
	else {
		if (article.getNoArticle()==0) {
			request.getRequestDispatcher("Accueil").forward(request, response);
			return;
		} else {
			String pathName = getServletContext().getRealPath("/")+aMgr.lireArticle(article.getNoArticle()).getNomPhoto();
			File fileToSave = new File(pathName);
			fileToSave.delete();
			aMgr.supprimerArticle(article.getNoArticle());
			request.getRequestDispatcher("Accueil").forward(request, response);
			return;
		}
	}
}
}
