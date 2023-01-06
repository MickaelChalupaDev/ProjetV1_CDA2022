package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.controllers.objectSent.ObjectSentAccueil;
import fr.eni.encheres.dal.ArticleDAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            pstmt.setInt(9, CategorieManager.getCategorieByLibelle(article.getCategorie()).noCategorie);
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
        SetEtatVenteTermine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Article article = null;
        StringBuilder select = new StringBuilder();
        try {

            select.append("SELECT * FROM ARTICLES_VENDUS INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie= CATEGORIES.no_categorie ");
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
                article.setEtatVente(EtatVente.values()[rs.getInt("etat_vente")]);


                int noUtilisateur = rs.getInt("no_utilisateur");
                Utilisateur utilisateur = UtilisateurManager.lireUtilisateur(noUtilisateur);
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
            pstmt.setInt(9, CategorieManager.getCategorieByLibelle(article.getCategorie()).noCategorie);
            pstmt.setString(10, article.getNomPhoto());
            pstmt.setInt(11, article.getNoArticle());

            pstmt.executeUpdate();


            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public List<Article> selectALL(String nomArticle, int categorie) {
        SetEtatVenteTermine();
        System.out.println("Select all");
        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        StringBuilder select = new StringBuilder();
        if(Objects.equals(nomArticle, "")){
            nomArticle = null;
        }
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (nomArticle == null) {
                if (categorie == 0) {

                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE date_debut_encheres <= GETDATE() and date_fin_encheres > GETDATE();");

                    pstmt = con.prepareStatement(select.toString());

                } else {
                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE CATEGORIES.no_categorie = ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");

                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setInt(1, categorie);
                }

            } else {

                if (categorie == 0) {

                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE nom_article like ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");
                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setString(1, "%" + nomArticle + "%");

                } else {
                    select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                    select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ? and date_debut_encheres <= GETDATE()");
                    select.append(" and date_fin_encheres > GETDATE()");

                    pstmt = con.prepareStatement(select.toString());
                    pstmt.setString(1, "%" + nomArticle + "%");
                    pstmt.setInt(2, categorie);
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

                article.setVendeur(UtilisateurManager.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(13));
                article.setNomPhoto(res.getString(11));

                article.setAdresse(ArticleManager.lireArticle(res.getInt(1)).getAdresse());
                articles.add(article);
            }

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return articles;

    }

    @Override
    public List<Article> selectByBuyer(String nomArticle, int categorie, int noEncherisseur, boolean etatEnchere) {
        SetEtatVenteTermine();
        StringBuilder select = new StringBuilder();
        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (!etatEnchere) {
                if (nomArticle == null) {
                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE date_debut_encheres <= GETDATE() and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noEncherisseur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE CATEGORIES.no_categorie = ? and date_debut_encheres <= GETDATE()");
                        select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, categorie);
                        pstmt.setInt(2, noEncherisseur);
                    }

                } else {

                    if (categorie == 0) {

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
                        select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ? and date_debut_encheres <= GETDATE()");
                        select.append(" and date_fin_encheres > GETDATE() and ENCHERES.no_utilisateur =?");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, categorie);
                        pstmt.setInt(3, noEncherisseur);
                    }
                }
            } else {

                if (nomArticle == null) {
                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
                        select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noEncherisseur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" inner join ENCHERES on ENCHERES.no_article=ARTICLES_VENDUS.no_article");
                        select.append(" WHERE CATEGORIES.no_categorie = ?");
                        select.append(" and date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
                        select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, categorie);
                        pstmt.setInt(2, noEncherisseur);
                    }

                } else {

                    if (categorie == 0) {
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
                        select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ? ");
                        select.append(" and date_fin_encheres < GETDATE() and ENCHERES.no_utilisateur =?");
                        select.append(" and ENCHERES.montant_enchere=ARTICLES_VENDUS.prix_vente; ");
                        pstmt = con.prepareStatement(select.toString());

                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, categorie);
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

                article.setVendeur(UtilisateurManager.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(13));
                article.setNomPhoto(res.getString(11));

                article.setAdresse(ArticleManager.lireArticle(res.getInt(1)).getAdresse());

                articles.add(article);
            }

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return articles;

    }

    @Override
    public List<Article> selectBySeller(String nomArticle, int categorie, int noVendeur, EtatVente etatVente) {
        SetEtatVenteTermine();
        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        StringBuilder select = new StringBuilder();

        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt;
            if (etatVente == EtatVente.NonDebutee) {
                if (nomArticle == null) {
                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE CATEGORIES.no_categorie = ? ");
                        select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, categorie);
                        pstmt.setInt(2, noVendeur);
                    }

                } else {

                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? ");
                        select.append(" AND date_debut_encheres > GETDATE()  AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ?");
                        select.append(" AND date_debut_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, categorie);
                        pstmt.setInt(3, noVendeur);
                    }
                }
            } else if (etatVente == EtatVente.EnCours) {
                if (nomArticle == null) {
                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE CATEGORIES.no_categorie = ?");
                        select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() and ARTICLES_VENDUS.no_utilisateur =?;");


                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, categorie);
                        pstmt.setInt(2, noVendeur);
                    }

                } else {

                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ?");
                        select.append(" AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ? ");
                        select.append(" and date_debut_encheres < GETDATE() and date_fin_encheres > GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, categorie);
                        pstmt.setInt(3, noVendeur);
                    }
                }
            } else {


                if (nomArticle == null) {
                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");
                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE CATEGORIES.no_categorie = ?");
                        select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setInt(1, categorie);
                        pstmt.setInt(2, noVendeur);
                    }

                } else {

                    if (categorie == 0) {

                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? ");
                        select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, noVendeur);

                    } else {
                        select.append("SELECT * FROM ARTICLES_VENDUS inner join CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie");
                        select.append(" WHERE nom_article like ? AND CATEGORIES.no_categorie = ? ");
                        select.append(" and date_fin_encheres < GETDATE() AND ARTICLES_VENDUS.no_utilisateur =?;");

                        pstmt = con.prepareStatement(select.toString());
                        pstmt.setString(1, "%" + nomArticle + "%");
                        pstmt.setInt(2, categorie);
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

                article.setVendeur(UtilisateurManager.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(10));
                article.setNomPhoto(res.getString(11));


                article.setAdresse(ArticleManager.lireArticle(res.getInt(1)).getAdresse());

                articles.add(article);
            }

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public List<Article> rechercher(Utilisateur utilisateur, ObjectSentAccueil o) {
        List<Article> articles = new ArrayList<Article>();
        String query = "SELECT a.[no_article]" +
                "      ,[nom_article]" +
                "      ,[description]" +
                "      ,[date_debut_encheres]" +
                "      ,[date_fin_encheres]" +
                "      ,[prix_initial]" +
                "      ,[prix_vente]" +
                "      ,[etat_vente]" +
                "      ,a.[no_utilisateur]" +
                "      ,[no_categorie]" +
                "      ,[lien_photo] FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article WHERE 1=1 ";
        if(utilisateur == null || Objects.equals(o, new ObjectSentAccueil())){ //Je ne m'embête pas à traiter le cas par défaut
            return selectALL(o.getSearched(), Integer.parseInt(o.getCategorieSelected()));//prévention
        }

        if(!o.checkedEnchereOuverte && !o.checkedMesEncheres && !o.checkedMesEncheresRemportees && !o.checkedMesVentesEnCours && !o.checkedVentesNonDebutees && !o.checkedVentesTerminees){
            return articles;
        }

        if(o.checkedMesEncheres || o.checkedMesEncheresRemportees || o.filtreVenteAffichees.equals("mesVentes")){
            query+= " AND "+ (o.filtreVenteAffichees.equals("mesVentes") ? "a" : "e") +".no_utilisateur = "+utilisateur.getNoUtilisateur()+"";
        }
        if(o.checkedEnchereOuverte || o.checkedMesVentesEnCours){
            query+=" AND a.etat_vente = 1 ";
        }
        if(o.checkedVentesNonDebutees){
            query+=" AND a.etat_vente = 0";
        }
        if(o.checkedMesEncheresRemportees || o.checkedVentesTerminees){
            query+= " AND a.etat_vente > 1";
        }
        if(o.searched != null && !o.searched.trim().equals("")){
            o.searched = o.searched.replace("'", "''")
                    .replace("\\", "\\\\")
                    .replace("\0", "\\0")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t")
                    .replace("\b", "\\b")
                    .replace("\u001A", "\\Z")
                    .replace("--", "")
                    .replace("/*", "")
                    .replace("*/", "");//Suppressions de l'injection SQL au cas où
            query+= " AND a.nom_article LIKE '%"+o.searched.trim()+"%'";
        }
        if(!Objects.equals(o.categorieSelected, "0")){
            query += " AND a.no_categorie = "+o.categorieSelected;
        }



        query += "GROUP BY a.[no_article]" +
                "      ,[nom_article]" +
                "      ,[description]" +
                "      ,[date_debut_encheres]" +
                "      ,[date_fin_encheres]" +
                "      ,[prix_initial]" +
                "      ,[prix_vente]" +
                "      ,[etat_vente]" +
                "      ,a.[no_utilisateur]" +
                "      ,[no_categorie]" +
                "      ,[lien_photo]";
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                Article article = new Article();
                article.setNoArticle(res.getInt(1));
                article.setNomArticle(res.getString(2));
                article.setDescription(res.getString(3));
                article.setDateDebutEncheres(res.getDate(4));
                article.setDateFinEncheres(res.getDate(5));
                article.setMiseAPrix(res.getInt(6));
                article.setPrixVente(res.getInt(7));
                article.setEtatVente(EtatVente.values()[res.getInt(8)]);

                article.setVendeur(UtilisateurManager.lireUtilisateur(res.getInt(9)));

                article.setCategorie(res.getString(10));
                article.setNomPhoto(res.getString(11));


                article.setAdresse(ArticleManager.lireArticle(res.getInt(1)).getAdresse());

                articles.add(article);
            }

            con.close();

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }

    private void SetEtatVenteTermine(){//Set ETAT VENTE = 1 / Terminées si la date est dépassée
        String query = "UPDATE ARTICLES_VENDUS" +
                "   SET etat_vente = 2" +
                " WHERE date_fin_encheres < GETDATE()" +
                "  AND etat_vente = 1";
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.executeUpdate();
            con.close();

        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

}
	
