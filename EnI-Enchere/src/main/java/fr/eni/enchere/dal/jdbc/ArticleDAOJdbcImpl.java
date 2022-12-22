package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.LesDAO.ArticleDAO;

public class ArticleDAOJdbcImpl implements ArticleDAO {

	@Override
	public void creationArticle(Article article) {

		try {
			final Connection con = ConnectionDAOBdd.getConnection();
			final PreparedStatement pstmt = con.prepareStatement("INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,\r\n"
					+ "date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie) VALUES (?,?,?,?,?,?,?,?,?,?)");
			
			
			
			pstmt.setString(1, article.getNomArticle());
			pstmt.setString(2, article.getDescription());
			pstmt.setDate(3, (Date) article.getDateDebutEncheres());
			pstmt.setDate(4, (Date) article.getDateFinEncheres());
			pstmt.setInt(5, article.getMiseAPrix());
			pstmt.setInt(6, article.getPrixVente());
			pstmt.setString(7, article.getEtatVente());
			pstmt.setInt(8, article.getVendeur().getNoUtlisateur());
			pstmt.setString(9, article.getcategorie().toString());
			pstmt.setString(10, article.getNomPhoto());

			pstmt.executeQuery();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
		
	

	@Override
	public Article lireArticle(int noArticle)  {
		Article article = null;
		
		try {
			Connection con = ConnectionDAOBdd.getConnection();
			final PreparedStatement pstmt = con.prepareStatement("SELECT nom_article, description,categorie,prix_vente,"
					+ "prix_initial, date_fin_encheres, vendeur"
					+ "FROM ARTICLES_VENDUS WHERE no_article=?");
			pstmt.setInt(1, noArticle);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				
				article = new Article();
				
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				article.setCategorie( rs.getString("categorie"));
				article.setVendeur(rs.getInt("no_utilisateur"));
				article.setAdresse((List<String>) rs.getArray("adresse"));
						
				}
		
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return article;
	}
		
		
	

	
	
	
	
	
	
	
	
	@Override
	public void supprimerArticle(int noArticle)   {
	
		try {
			final Connection con = ConnectionDAOBdd.getConnection();
			final PreparedStatement pstmt = con.prepareStatement("DELETE FROM ARTICLES_VENDUS WHERE no_article=?");
			pstmt.setInt(1, noArticle);
			pstmt.executeQuery();
			

			con.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

	@Override
	public void Annuler() {
		
		
	}

	}
	
