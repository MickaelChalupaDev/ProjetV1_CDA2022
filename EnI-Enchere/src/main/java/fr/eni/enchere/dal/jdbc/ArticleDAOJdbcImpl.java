package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;

import java.sql.*;
import java.text.SimpleDateFormat;

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


}
	
