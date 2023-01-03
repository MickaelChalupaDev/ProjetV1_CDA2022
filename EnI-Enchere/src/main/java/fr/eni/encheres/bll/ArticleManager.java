package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.util.List;

public class ArticleManager {
	private final ArticleDAO daoArticles;
	
	public ArticleManager() {
		this.daoArticles = DAOFactory.getArticleDAO();
	}

	public List<Article> rechercherTout(String nomArticle, String categorie) {
		return this.daoArticles.selectALL(nomArticle, categorie);
	}

	public List<Article> rechercherParAchat(String nomArticle, String categorie, int noEncherisseur, boolean etatEnchere) {
		return this.daoArticles.selectByBuyer(nomArticle, categorie, noEncherisseur, etatEnchere);
	}

	public List<Article> rechercherParVente(String nomArticle, String categorie, int noVendeur, EtatVente etatVente) {
		return this.daoArticles.selectBySeller(nomArticle, categorie, noVendeur, etatVente);
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
