package fr.eni.encheres.dal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import fr.eni.encheres.bll.ArticleManager;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;




public class EnchereDAOJdbcImpl implements EnchereDAO {

	private String SELECT_ENCHERE; 
	private String INSERT_ENCHERE;
	private String UPDATE_ENCHERE;
	private String DELETE_ENCHERE;
	
	public EnchereDAOJdbcImpl() {
		super();
	}

	
	@Override
	public void insert(Enchere enchere) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
			try {
			Connection con = ConnectionDAOBdd.getConnection();
			PreparedStatement pstmt;
						
			SELECT_ENCHERE=	"SELECT * FROM ENCHERES WHERE no_utilisateur=? AND no_article=?";
			pstmt = con.prepareStatement(SELECT_ENCHERE); 
			
			pstmt.setInt(1, enchere.getNoEncherisseur());
			pstmt.setInt(2, enchere.getNoArticle());
			ResultSet res = pstmt.executeQuery();
			if (res.next())
			{ 
				UPDATE_ENCHERE=	"UPDATE ENCHERES SET date_enchere= ?, montant_enchere=? WHERE no_utilisateur=? and no_article=?;";
				pstmt = con.prepareStatement(UPDATE_ENCHERE);
				pstmt.setTimestamp(1, Timestamp.valueOf((String) sdf.format(enchere.getDateEnchere()))); 
				pstmt.setInt(2, enchere.getMontantEnchere());
				pstmt.setInt(3, enchere.getNoEncherisseur());
				pstmt.setInt(4, enchere.getNoArticle());  
										
				pstmt.executeUpdate();
				
			} else { 
				INSERT_ENCHERE=	"INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES(?,?,?,?);";
							
				pstmt = con.prepareStatement(INSERT_ENCHERE);
				pstmt.setInt(1, enchere.getNoEncherisseur());
				pstmt.setInt(2, enchere.getNoArticle());				
				pstmt.setTimestamp(3, Timestamp.valueOf((String) sdf.format(enchere.getDateEnchere())));
				pstmt.setInt(4, enchere.getMontantEnchere());
				pstmt.executeUpdate();
				
			}
			con.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Article> selectALL(String nomArticle, String categorie) {
		
			List<Article> articles =new ArrayList<Article>(); 
			Article article =null; 
			StringBuilder select= new StringBuilder();
			try {
				Connection con = ConnectionDAOBdd.getConnection();
				PreparedStatement pstmt;
				if (nomArticle== null) {
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE date_debut_encheres <= GETDATE() and date_fin_encheres > GETDATE();");
						
						pstmt = con.prepareStatement(select.toString());
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE libelle like ? and date_debut_encheres <= GETDATE()");
						select.append(" and date_fin_encheres > GETDATE()");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+categorie+"%");
					}
					
				} else {
				
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ? and date_debut_encheres <= GETDATE()");
						select.append(" and date_fin_encheres > GETDATE()");
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ? AND libelle like ? and date_debut_encheres <= GETDATE()");
						select.append(" and date_fin_encheres > GETDATE()");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setString(2, "%"+categorie+"%");
					}
					
				}
				ResultSet res = pstmt.executeQuery();
				while (res.next()) {
					article = new Article();  
					article.setNoArticle(res.getInt(1));
					article.setNomArticle(res.getString(2));
					article.setDescription(res.getString(3));
					article.setDateDebutEncheres(res.getDate(4));
					article.setDateFinEncheres(res.getDate(5));
					article.setMiseAPrix(res.getInt(6));
					article.setPrixVente(res.getInt(7));
					article.setEtatVente(res.getString(8));
				    
					UtilisateurManager uMgr = new UtilisateurManager();
				    article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));
				    
					article.setCategorie(res.getString(13));
					article.setNomPhoto(res.getString(11));
					
					ArticleManager aMgr= new ArticleManager();
					article.setAdresse(aMgr.lireArticle(res.getInt(1)).getAdresse());
					articles.add(article);
				}
					
				con.close();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			return articles;
						
}
	
	@Override
	public List<Article> selectByBuyer(String nomArticle, String categorie, int noEncherisseur, boolean etatEnchere) {

		StringBuilder select= new StringBuilder();
		List<Article> articles =new ArrayList<Article>(); 
		Article article =null; 
		try {
			Connection con = ConnectionDAOBdd.getConnection();
			PreparedStatement pstmt;
			if (etatEnchere == false) {
			if (nomArticle== null) {
				if (categorie== null) {
					
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
					select.append(" WHERE date_debut_encheres <= GETDATE() and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?;");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setInt(1, noEncherisseur);
										
				} else {
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
					select.append(" WHERE libelle like ? and date_debut_encheres <= GETDATE()");
					select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+categorie+"%");
					pstmt.setInt(2, noEncherisseur);
				}
				
			} else {
			
				if (categorie== null) {
					
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
					select.append(" WHERE nom_article like ? and date_debut_encheres <= GETDATE() ");
					select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?;");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+nomArticle+"%");
					pstmt.setInt(2, noEncherisseur);
										
				} else {
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
					select.append(" WHERE nom_article like ? AND libelle like ? and date_debut_encheres <= GETDATE()");
					select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?");
					
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+nomArticle+"%");
					pstmt.setString(2, "%"+categorie+"%");
					pstmt.setInt(3, noEncherisseur);
				}
			}
			}else {
			
				if (nomArticle== null) {
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
						select.append(" WHERE date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
						select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
						pstmt = con.prepareStatement(select.toString());
						pstmt.setInt(1, noEncherisseur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
						select.append(" WHERE libelle like ?");
						select.append(" and date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
						select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+categorie+"%");
						pstmt.setInt(2, noEncherisseur);
					}
					
				} else {
				
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
						select.append(" WHERE nom_article like ? and ENCHERES.no_utilisateur =?");
						select.append(" and date_fin_encheres < GETDATE()");
						select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setInt(2, noEncherisseur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
						select.append(" WHERE nom_article like ? AND libelle like ? ");
						select.append(" and date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
						select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
						pstmt = con.prepareStatement(select.toString());
						
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setString(2, "%"+categorie+"%");
						pstmt.setInt(3, noEncherisseur);
					}
				}
			}
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				article = new Article();  
				article.setNoArticle(res.getInt(1));
				article.setNomArticle(res.getString(2));
				article.setDescription(res.getString(3));
				article.setDateDebutEncheres(res.getDate(4));
				article.setDateFinEncheres(res.getDate(5));
				article.setMiseAPrix(res.getInt(6));
				article.setPrixVente(res.getInt(7));
				article.setEtatVente(res.getString(8));
			    
				UtilisateurManager uMgr = new UtilisateurManager();
			    article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));
			    
				article.setCategorie(res.getString(13));
				article.setNomPhoto(res.getString(11));
				
				ArticleManager aMgr= new ArticleManager();
				article.setAdresse(aMgr.lireArticle(res.getInt(1)).getAdresse());
				
				articles.add(article);
			}
				
			con.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return articles;
					
	}

	@Override
	public List<Article> selectBySeller(String nomArticle, String categorie, int noVendeur, String etatVente) {

		List<Article> articles =new ArrayList<Article>(); 
		Article article =null; 
		StringBuilder select= new StringBuilder();
		
		try {
			Connection con = ConnectionDAOBdd.getConnection();
			PreparedStatement pstmt;
			if (etatVente == "nonDebutes") {
			if (nomArticle== null) {
				if (categorie== null) {
					
					select.append("SELECT * FROM ARTICLES_VENDUS INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" WHERE date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setInt(1, noVendeur);
										
				} else {
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" WHERE libelle like ? ");
					select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+categorie+"%");
					pstmt.setInt(2, noVendeur);
				}
				
			} else {
			
				if (categorie== null) {
					
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" WHERE nom_article like ? ");
					select.append(" AND date_debut_encheres > GETDATE()  AND ARTICLES_VENDUS.no_utilisateur =?;");
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+nomArticle+"%");
					pstmt.setInt(2, noVendeur);
										
				} else {
					select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
					select.append(" WHERE nom_article like ? AND libelle like ?");
					select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
					
					pstmt = con.prepareStatement(select.toString());
					pstmt.setString(1, "%"+nomArticle+"%");
					pstmt.setString(2, "%"+categorie+"%");
					pstmt.setInt(3, noVendeur);
				}
			}
			}else if (etatVente == "enCours")  {
			
				if (nomArticle== null) {
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setInt(1, noVendeur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE libelle like ?");
						select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() and ARTICLES_VENDUS.no_utilisateur =?;");
						
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+categorie+"%");
						pstmt.setInt(2, noVendeur);
					}
					
				} else {
				
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ?");
						select.append(" AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setInt(2, noVendeur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ? AND libelle like ? ");
						select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setString(2, "%"+categorie+"%");
						pstmt.setInt(3, noVendeur);
					}
				}
			} else {

				
				if (nomArticle== null) {
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						pstmt = con.prepareStatement(select.toString());
						pstmt.setInt(1, noVendeur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE libelle like ?");
						select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
												
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, categorie);
						pstmt.setInt(2, noVendeur);
					}
					
				} else {
				
					if (categorie== null) {
						
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ? ");
						select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setInt(2, noVendeur);
											
					} else {
						select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
						select.append(" WHERE nom_article like ? AND libelle like ? ");
						select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
						
						pstmt = con.prepareStatement(select.toString());
						pstmt.setString(1, "%"+nomArticle+"%");
						pstmt.setString(2, "%"+categorie+"%");
						pstmt.setInt(3, noVendeur);
					}
				}
				
			}
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				article = new Article();  
				article.setNoArticle(res.getInt(1));
				article.setNomArticle(res.getString(2));
				article.setDescription(res.getString(3));
				article.setDateDebutEncheres(res.getDate(4));
				article.setDateFinEncheres(res.getDate(5));
				article.setMiseAPrix(res.getInt(6));
				article.setPrixVente(res.getInt(7));
				article.setEtatVente(res.getString(8));
			    
				UtilisateurManager uMgr = new UtilisateurManager();
			    article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));
			    
				article.setCategorie(res.getString(10));
				article.setNomPhoto(res.getString(11));
				
				ArticleManager aMgr= new ArticleManager();
				article.setAdresse(aMgr.lireArticle(res.getInt(1)).getAdresse());
				
				articles.add(article);
			}
				
			con.close();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return articles;

	
	}


	@Override
	public Enchere selectByID(int noArticle) {
		
		Enchere enchere =null; 
		StringBuilder select = new StringBuilder();
			try {
				Connection con = ConnectionDAOBdd.getConnection();
				
				PreparedStatement pstmt;
						
				select.append("SELECT * FROM ENCHERES WHERE no_article=? ORDER BY montant_enchere DESC;");
	
				pstmt = con.prepareStatement(select.toString());
				pstmt.setInt(1, noArticle);
				
				ResultSet res = pstmt.executeQuery();
				if (res.next()) {
					enchere = new Enchere();
					enchere.setNoEncherisseur(res.getInt(1));
					enchere.setNoArticle(res.getInt(2));
					enchere.setDateEnchere(res.getDate(3));
					enchere.setMontantEnchere(res.getInt(4));
									
				}
				con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return enchere;
			
	}


	@Override
	public void delete(Enchere enchere) {
		
			Connection con;
			try {
				con = ConnectionDAOBdd.getConnection();
				DELETE_ENCHERE=	"DELETE FROM ENCHERES WHERE no_utilisateur=? and no_article=?;";
				PreparedStatement pstmt;
				pstmt = con.prepareStatement(DELETE_ENCHERE);
								
				pstmt.setInt(1, enchere.getNoEncherisseur());
				pstmt.setInt(2, enchere.getNoArticle());  
										
				pstmt.executeUpdate();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
