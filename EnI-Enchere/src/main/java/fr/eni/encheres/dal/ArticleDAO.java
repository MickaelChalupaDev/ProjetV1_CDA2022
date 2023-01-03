package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {
	public Article creerArticle(Article article);
	public Article lireArticle(int noArticle);
	public void modifierArticle(Article article);
	public void supprimerArticle(int noArticle);
	
}
