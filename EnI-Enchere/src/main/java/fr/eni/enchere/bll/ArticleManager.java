package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

public class ArticleManager {
	
private static ArticleDAO doaArticles;
	
	public ArticleManager() {
		daoArticles= DAOFactory.getArticleDAO();
	}
	
	public void creationArticle(Article article) {
		
	}
	public Article lireArticle(int noArticle) {
		return null;
	}
	public void supperimerArticle(int noArticle) {
		
	}
	public void Annuler() {
		
	}
	

}
