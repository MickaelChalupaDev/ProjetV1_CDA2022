package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

public class EnchereManager {
	
private static EnchereDAO doaEncheres;
	
	public EnchereManager() {
		daoEncheres= DAOFactory.getEnchereDAO();
	}
	
	public List<Article> rechercherAchatEncheres(String nomArticle, Categorie categorie, int noUtilisateurEnchere, String dateEnchere){
		return null;
	}
	public List<Article> rechercherMesVentesEncheres(String nomArticle, Categorie categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere){
		return null;
	}

}
