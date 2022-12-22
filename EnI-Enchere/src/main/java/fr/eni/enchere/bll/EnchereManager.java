package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.dal.EnchereDAO;
import fr.eni.enchere.bo.Enchere;

import java.util.List;

public class EnchereManager {
	private final EnchereDAO daoEncheres;
	
	public EnchereManager(EnchereDAO doaEncheres) {
		this.daoEncheres = DAOFactory.getEnchereDAO();
	}

	//Recherche dans la table ARTCILES dont l'enchere ouverte (en cours) :
	public List<Enchere> rechercherToutesEncheres(String nomArticle, String categorie, int noUtilisateurEnchere, String dateEnchere){

		return this.daoEncheres.selectAll(nomArticle, categorie, noUtilisateurEnchere, dateEnchere);

	}
	public List<Enchere> rechercherMesEncheres(String nomArticle, String categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere){
		return this.daoEncheres.selectByUser(nomArticle, categorie, noUtilisateurArticle, dateDebut, dateEnchere);
	}

}
