package fr.eni.encheres.dal;


import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	public void creationArticle(Article article);
	public Article lireArticle(int noArticle);
	public void supperimerArticle(int noArticle);
	public void Annuler();
	
	
	
}
