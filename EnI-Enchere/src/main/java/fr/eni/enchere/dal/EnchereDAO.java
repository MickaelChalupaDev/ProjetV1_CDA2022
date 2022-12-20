package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

public interface EnchereDAO {

	public List<Article> rechercherAchatEncheres(String nomArticle, Categorie categorie, int noUtilisateurEnchere, String dateEnchere);
	public List<Article> rechercherMesVentesEncheres(String nomArticle, Categorie categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere);

}
