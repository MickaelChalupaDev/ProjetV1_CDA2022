package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    @Override
    public Article creerArticle(Article article) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            final Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,"
                    + "date_fin_encheres,prix_initial,prix_vente,etat_vente, no_utilisateur,no_categorie, lien_photo) VALUES (?,?,?,?,?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, article.getNomArticle());
            pstmt.setString(2, article.getDescription());

            pstmt.setDate(3, Date.valueOf((String) sdf.format(article.getDateDebutEncheres())));

            pstmt.setDate(4, Date.valueOf((String) sdf.format(article.getDateFinEncheres())));
            pstmt.setInt(5, article.getMiseAPrix());
            pstmt.setInt(6, article.getPrixVente());
            pstmt.setInt(7, article.getEtatVente().ordinal());
            pstmt.setInt(8, article.getVendeur().getNoUtilisateur());
            int cat;
            switch (article.getCategorie()) {
                case "Ameublement":
                    cat = 2;
                    break;
                case "Vêtement":
                    cat = 3;
                    break;
                case "Sport&Loisirs":
                    cat = 4;
                    break;
                default:
                    cat = 1;//Informatique
            }
            pstmt.setInt(9, cat);
            pstmt.setString(10, article.getNomPhoto());

            pstmt.executeUpdate();//
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                article.setNoArticle(rs.getInt(1));

            }

            pstmt = con.prepareStatement("INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?)");

            pstmt.setInt(1, article.getNoArticle());
            pstmt.setString(2, article.getAdresse().get(0));
            pstmt.setString(3, article.getAdresse().get(1));
            pstmt.setString(4, article.getAdresse().get(2));
            pstmt.executeUpdate();


            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;

    }


    @Override
    public Article lireArticle(int noArticle) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Article article = null;
        StringBuilder select = new StringBuilder();
        try {

            select.append("SELECT * FROM ARTICLES_VENDUS INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie= CATEGORIES.no_CATEGORIE ");
            select.append(" INNER JOIN RETRAITS ON RETRAITS.no_article=ARTICLES_VENDUS.no_article ");
            select.append(" WHERE ARTICLES_VENDUS.no_article=?;");
            Connection con = ConnectionDAOBdd.getConnection();

            final PreparedStatement pstmt = con.prepareStatement(select.toString());
            pstmt.setInt(1, noArticle);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {


                article = new Article();
                article.setNoArticle(Integer.parseInt(rs.getString("no_article")));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));


                article.setDateDebutEncheres(new Date(rs.getDate("date_debut_encheres").getTime()));
                article.setDateFinEncheres(new Date(rs.getDate("date_fin_encheres").getTime()));
                article.setMiseAPrix(rs.getInt("prix_initial"));
                article.setPrixVente(rs.getInt("prix_vente"));


                int noUtilisateur = rs.getInt("no_utilisateur");
                Utilisateur utilisateur = new UtilisateurManager().lireUtilisateur(noUtilisateur);
                article.setVendeur(utilisateur);
                article.setCategorie(rs.getString("libelle"));
                article.setNomPhoto(rs.getString("lien_photo"));

                article.getAdresse().add(rs.getString("rue"));
                article.getAdresse().add(rs.getString("code_postal"));
                article.getAdresse().add(rs.getString("ville"));

            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return article;
    }


    @Override
    public void supprimerArticle(int noArticle) {

        try {
            final Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM ARTICLES_VENDUS WHERE no_article=?");
            pstmt.setInt(1, noArticle);
            pstmt.executeUpdate();


            con.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void modifierArticle(Article article) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE ARTICLES_VENDUS SET nom_article=?,description=?,date_debut_encheres=?,"
                    + "date_fin_encheres=?,prix_initial=?,prix_vente=?,etat_vente=?,no_utilisateur=?,no_categorie=?, lien_photo=? WHERE no_article = ?");


            pstmt.setString(1, article.getNomArticle());
            pstmt.setString(2, article.getDescription());

            pstmt.setDate(3, Date.valueOf((String) sdf.format(article.getDateDebutEncheres())));

            pstmt.setDate(4, Date.valueOf((String) sdf.format(article.getDateFinEncheres())));

            pstmt.setInt(5, article.getMiseAPrix());
            pstmt.setInt(6, article.getPrixVente());
            pstmt.setInt(7, article.getEtatVente().ordinal());
            pstmt.setInt(8, article.getVendeur().getNoUtilisateur());

            int cat;
            switch (article.getCategorie()) {
                case "Ameublement":
                    cat = 2;
                    break;
                case "Vêtement":
                    cat = 3;
                    break;
                case "Sport&Loisirs":
                    cat = 4;
                    break;
                default:
                    cat = 1;//Informatique
            }
            pstmt.setInt(9, cat);
            pstmt.setString(10, article.getNomPhoto());
            pstmt.setInt(11, article.getNoArticle());

            pstmt.executeUpdate();


            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Article> selectALL(String nomArticle, String categorie) {

        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        StringBuilder select = new StringBuilder();
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (nomArticle == null) {
                if (categorie == null) {

                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE date_debut_encheres <= GETDATE() and date_fin_encheres > GETDATE();");

                    pstmt = con.prepareStatement(select.toString());

                } else {
                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE libelle like ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");

                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setString(1, "%" + categorie + "%");
                }

            } else {

                if (categorie == null) {

                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE nom_article like ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");
                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setString(1, "%" + nomArticle + "%");

                } else {
                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE nom_article like ? AND libelle like ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");

                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setString(1, "%" + nomArticle + "%");
                    pstmt.setString(2, "%" + categorie + "%");
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
                article.setEtatVente(EtatVente.values()[res.getInt(8)]);

                UtilisateurManager uMgr = new UtilisateurManager();
                article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(13));
                article.setNomPhoto(res.getString(11));

                ArticleManager aMgr = new ArticleManager();
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

        StringBuilder select = new StringBuilder();
        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (!etatEnchere) {
                if (nomArticle == null) {
                    if (categorie == null) {

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
                        pstmt.setString(1, "%" + categorie + "%");
                        pstmt.setInt(2, noEncherisseur);
                    }

                } else {

                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE nom_article like ? and date_debut_encheres <= GETDATE() ");
                        select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noEncherisseur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE nom_article like ? AND libelle like ? and date_debut_encheres <= GETDATE()");
                        select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setString(2, "%" + categorie + "%");
                        pstmt.setInt(3, noEncherisseur);
                    }
                }
            } else {

                if (nomArticle == null) {
                    if (categorie == null) {

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
                        pstmt.setString(1, "%" + categorie + "%");
                        pstmt.setInt(2, noEncherisseur);
                    }

                } else {

                    if (categorie == null) {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE nom_article like ? and ENCHERES.no_utilisateur =?");
                        select.append(" and date_fin_encheres < GETDATE()");
                        select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noEncherisseur);
                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE nom_article like ? AND libelle like ? ");
                        select.append(" and date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
                        select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
                        pstmt = con.prepareStatement(select.toString());

                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setString(2, "%" + categorie + "%");
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
                article.setEtatVente(EtatVente.values()[res.getInt(8)]);

                UtilisateurManager uMgr = new UtilisateurManager();
                article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(13));
                article.setNomPhoto(res.getString(11));

                ArticleManager aMgr = new ArticleManager();
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
    public List<Article> selectBySeller(String nomArticle, String categorie, int noVendeur, EtatVente etatVente) {

        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        StringBuilder select = new StringBuilder();

        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (etatVente == EtatVente.NonDebutee) {
                if (nomArticle == null) {
                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE libelle like ? ");
                        select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + categorie + "%");
                        pstmt.setInt(2, noVendeur);
                    }

                } else {

                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? ");
                        select.append(" AND date_debut_encheres > GETDATE()  AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND libelle like ?");
                        select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setString(2, "%" + categorie + "%");
                        pstmt.setInt(3, noVendeur);
                    }
                }
            } else if (etatVente == EtatVente.EnCours) {
                if (nomArticle == null) {
                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE libelle like ?");
                        select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() and ARTICLES_VENDUS.no_utilisateur =?;");


                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + categorie + "%");
                        pstmt.setInt(2, noVendeur);
                    }

                } else {

                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ?");
                        select.append(" AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND libelle like ? ");
                        select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setString(2, "%" + categorie + "%");
                        pstmt.setInt(3, noVendeur);
                    }
                }
            } else {


                if (nomArticle == null) {
                    if (categorie == null) {

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

                    if (categorie == null) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? ");
                        select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND libelle like ? ");
                        select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setString(2, "%" + categorie + "%");
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
                article.setEtatVente(EtatVente.values()[res.getInt(8)]);

                UtilisateurManager uMgr = new UtilisateurManager();
                article.setVendeur(uMgr.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(10));
                article.setNomPhoto(res.getString(11));

                ArticleManager aMgr = new ArticleManager();

                article.setAdresse(aMgr.lireArticle(res.getInt(1)).getAdresse());

                articles.add(article);
            }

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return articles;


    }


}
	
