package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

public class ArticleManager {
	private final ArticleDAO daoArticles;
	
	public ArticleManager() {
		this.daoArticles = DAOFactory.getArticleDAO();
	}
	
	public Article creationArticle(Article article) {
		return daoArticles.creerArticle(article);
	}
	public void modifierArticle(Article article) {
		daoArticles.modifierArticle(article);
	}
	
	public Article lireArticle(int noArticle) {
		return daoArticles.lireArticle(noArticle);
	}
	public void supprimerArticle(int noArticle) {
		daoArticles.supprimerArticle(noArticle);
	}
	
}
