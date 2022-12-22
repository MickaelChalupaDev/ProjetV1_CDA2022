package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;


public class EnchereManager {
	
private EnchereDAO daoEncheres;
	
	public EnchereManager() {
		daoEncheres= DAOFactory.getEnchereDAO();
	}
	
	/**
	 * Recherche dans la table ARTCILES dont l'enchere ouverte (en cours) :
	 * @param nomArticle
	 * @param categorie
	 * @param noUtilisateurEnchere
	 * @param dateEnchere
	 * @return
	 */
	public List<Article> rechercherToutesEnchres(String nomArticle, Categorie categorie, int noUtilisateurEnchere, String dateEnchere){

		return this.daoEncheres.selectAll(nomArticle, categorie, noUtilisateurEnchere, dateEnchere);
				
	}
	public List<Enchere> rechercherMesEncheres(String nomArticle, Categorie categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere){
		
		return this.daoEncheres.selectByUser(nomArticle, categorie, noUtilisateurArticle, dateDebut, dateEnchere);
	
	}

}
