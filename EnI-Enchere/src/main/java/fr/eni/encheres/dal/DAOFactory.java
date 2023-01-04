package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public abstract class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO() {
			return new UtilisateurDAOJdbcImpl();
		}
	public static ArticleDAO getArticleDAO() {
		return new ArticleDAOJdbcImpl();
	}
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}
	
	public static CategorieDAO getCategorieDAO() {
		return new CategorieDAOJdbcImpl();
	}
}
