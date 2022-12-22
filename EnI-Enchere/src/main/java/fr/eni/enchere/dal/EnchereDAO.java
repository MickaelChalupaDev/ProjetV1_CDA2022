package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;


public interface EnchereDAO {

	public List<Article> selectAll(String nomArticle, Categorie categorie, int noUtilisateurEnchere, String dateEnchere);
	public List<Article> selectByUser(String nomArticle, Categorie categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere);
	public void insert(Enchere enchere);
	public void update(Enchere enchere);
	

}
