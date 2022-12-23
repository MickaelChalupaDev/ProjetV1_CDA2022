package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;


public interface EnchereDAO {

	public List<Article> selectALL(String nomArticle, String categorie); 
	public List<Article> selectByBuyer(String nomArticle, String categorie, int noEncherisseur, boolean etatEnchere);
	public List<Article> selectBySeller(String nomArticle, String categorie, int noVendeur, String etatVente);
	public Enchere selectByID(int noEnchere);
	
	public void insert(Enchere enchere);
	public void delete(Enchere enchere);
	
	
}
