package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Utilisateur;

public interface EnchereDAO {
	public List<Article> rechercherAchatEncheres(String nomArticle, String categorie, int noUtilisateurEnchere, String dateEnchere);
	public List<Article> rechercherMesVentesEncheres(String nomArticle, String categorie,int noUtilisateurArticle, String dateDebut, String dateEnchere);
}
