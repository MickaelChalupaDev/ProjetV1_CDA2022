package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.dal.ArticleDAO;
import fr.eni.enchere.dal.DAOFactory;

public class ArticleManager {
	private final ArticleDAO daoArticles;
	
	public ArticleManager() {
		this.daoArticles = DAOFactory.getArticleDAO();
	}
	
	public void creationArticle(Article article) {
		daoArticles.creerArticle(article);
	}
	public Article lireArticle(int noArticle) {
		return daoArticles.lireArticle(noArticle);
	}
	public void supprimerArticle(int noArticle) {
		daoArticles.supprimerArticle(noArticle);
	}
	public void Annuler() {
		daoArticles.Annuler();
	}
}
