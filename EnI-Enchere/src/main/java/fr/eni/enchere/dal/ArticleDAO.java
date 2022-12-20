package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Article;

public interface ArticleDAO {

	public void creationArticle(Article article);
	public Article lireArticle(int noArticle);
	public void supprimerArticle(int noArticle);
	public void Annuler();
}
