package fr.eni.enchere.dal;

import fr.eni.enchere.dal.jdbc.UtilisateurDAOJdbcImpl;

public class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO() {
			
			return new UtilisateurDAOJdbcImpl();
		}
	public static ArticleDAO getArticleDAO() {
		
		return new ArticleDAOJdbcImpl();
	}
	public static EnchereDAO getEnchereDAO() {
		
		return new EnchereDAOJdbcImpl();
	}

}
