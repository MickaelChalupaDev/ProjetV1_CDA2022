package fr.eni.encheres.dal;

public class DAOFactory {
	
	

	public static ArticleDAO getArticleDAO()  {
		ArticleDAO articleDAO=null;
		try {
			articleDAO=(ArticleDAO ) Class.forName("fr.eni.enchere.dal.ArticleDAO").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return articleDAO; 
	}
	
	
	public static EnchereDAO getEnchereDAO() {
		EnchereDAO enchereDAO=null;
		try {
			enchereDAO=(EnchereDAO ) Class.forName("fr.eni.enchere.dal.EnchereDAO").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return enchereDAO; 
		
	}
	
	public static UtilisateurDAO getUtilisateurDAO() {
		UtilisateurDAO utilisateurDAO=null;
		try {
			utilisateurDAO=(UtilisateurDAO ) Class.forName("fr.eni.enchere.dal.UtilisateurDAO").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return utilisateurDAO; 
}
}
