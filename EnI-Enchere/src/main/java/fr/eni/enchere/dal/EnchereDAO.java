package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;


public interface EnchereDAO {

	public List<Article> selectAll(String nomArticle, String categorie, int noUtilisateurEnchere, String dateEnchere);
	public List<Article> selectByUser(String nomArticle, String categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere);
	public void insert(Enchere enchere);
	public void update(Enchere enchere);
}
