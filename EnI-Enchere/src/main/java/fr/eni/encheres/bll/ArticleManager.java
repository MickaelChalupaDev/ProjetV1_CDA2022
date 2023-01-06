package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.util.List;

public class ArticleManager {
	private static ArticleDAO daoArticles = DAOFactory.getArticleDAO();

	public static List<Article> rechercherTout(String nomArticle, int categorie) {
		return daoArticles.selectALL(nomArticle, categorie);
	}
	public static List<Article> rechercherTout(String nomArticle, String categorie) {
		return daoArticles.selectALL(nomArticle, CategorieManager.getCategorieByLibelle(categorie).noCategorie);
	}
	public static List<Article> rechercherTout(String nomArticle, Categorie categorie) {
		return daoArticles.selectALL(nomArticle, categorie.noCategorie);
	}

	public static List<Article> rechercherParAchat(String nomArticle, int categorie, int noEncherisseur, boolean etatEnchere) {
		return daoArticles.selectByBuyer(nomArticle, categorie, noEncherisseur, etatEnchere);
	}
	public static List<Article> rechercherParAchat(String nomArticle, String categorie, int noEncherisseur, boolean etatEnchere) {
		return daoArticles.selectByBuyer(nomArticle, CategorieManager.getCategorieByLibelle(categorie).noCategorie, noEncherisseur, etatEnchere);
	}
	public static List<Article> rechercherParAchat(String nomArticle, Categorie categorie, int noEncherisseur, boolean etatEnchere) {
		return daoArticles.selectByBuyer(nomArticle, categorie.noCategorie, noEncherisseur, etatEnchere);
	}

	public static List<Article> rechercherParVente(String nomArticle, int categorie, int noVendeur, EtatVente etatVente) {
		return daoArticles.selectBySeller(nomArticle, categorie, noVendeur, etatVente);
	}
	public static List<Article> rechercherParVente(String nomArticle, String categorie, int noVendeur, EtatVente etatVente) {
		return daoArticles.selectBySeller(nomArticle, CategorieManager.getCategorieByLibelle(categorie).noCategorie, noVendeur, etatVente);
	}
	public static List<Article> rechercherParVente(String nomArticle, Categorie categorie, int noVendeur, EtatVente etatVente) {
		return daoArticles.selectBySeller(nomArticle, categorie.noCategorie, noVendeur, etatVente);
	}

	public static List<Article> rechercher(Utilisateur utilisateur, ObjectSentAccueil o){
		return daoArticles.rechercher(utilisateur, o);
	}
	
	public static Article creationArticle(Article article) {
		return daoArticles.creerArticle(article);
	}
	public static void modifierArticle(Article article) {
		daoArticles.modifierArticle(article);
	}
	
	public static Article lireArticle(int noArticle) {
		return daoArticles.lireArticle(noArticle);
	}
	public static void supprimerArticle(int noArticle) {
		daoArticles.supprimerArticle(noArticle);
	}
}
