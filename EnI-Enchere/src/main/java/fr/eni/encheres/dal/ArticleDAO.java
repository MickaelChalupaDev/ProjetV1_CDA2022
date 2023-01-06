package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;

import java.util.List;

public interface ArticleDAO {
	public Article creerArticle(Article article);
	public Article lireArticle(int noArticle);
	public void modifierArticle(Article article);
	public void supprimerArticle(int noArticle);
	public List<Article> selectALL(String nomArticle, int categorie);
	public List<Article> selectByBuyer(String nomArticle, int categorie, int noEncherisseur, boolean etatEnchere);
	public List<Article> selectBySeller(String nomArticle, int categorie, int noVendeur, EtatVente etatVente);
	public List<Article> rechercher(Utilisateur utilisateur, ObjectSentAccueil o);
}
