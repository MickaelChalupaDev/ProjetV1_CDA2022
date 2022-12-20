package fr.eni.enchere.bll;

import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.dal.EnchereDAO;

public class EnchereManager {
	private final EnchereDAO daoEncheres;
	
	public EnchereManager(EnchereDAO doaEncheres) {
		this.daoEncheres = DAOFactory.getEnchereDAO();
	}
	
	public List<Article> rechercherAchatEncheres(String nomArticle, Categorie categorie, int noUtilisateurEnchere, String dateEnchere){
		return daoEncheres.rechercherAchatEncheres(nomArticle, categorie, noUtilisateurEnchere, dateEnchere);
	}
	public List<Article> rechercherMesVentesEncheres(String nomArticle, Categorie categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere){
		return daoEncheres.rechercherMesVentesEncheres(nomArticle, categorie, noUtilisateurArticle, dateDebut, dateEnchere);
	}

}
