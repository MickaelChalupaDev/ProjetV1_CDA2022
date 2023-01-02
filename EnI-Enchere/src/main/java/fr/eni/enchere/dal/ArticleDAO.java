package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	public void creerArticle(Article article);
	public Article lireArticle(int noArticle);
	public void supprimerArticle(int noArticle);
	public void Annuler();
}
