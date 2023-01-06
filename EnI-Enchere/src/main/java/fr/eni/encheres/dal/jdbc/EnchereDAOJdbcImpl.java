package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.dal.EnchereDAO;

import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


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

            Article a = ArticleManager.lireArticle(enchere.getNoArticle());
            System.out.println(a.getEtatVente());
            if(a.getEtatVente() != EtatVente.EnCours){
                System.out.println("Erreur");
                return;
            }

            INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES(?,?,?,?);";

            pstmt = con.prepareStatement(INSERT_ENCHERE);
            pstmt.setInt(1, enchere.getNoEncherisseur());
            pstmt.setInt(2, enchere.getNoArticle());
            pstmt.setTimestamp(3, Timestamp.valueOf((String) sdf.format(enchere.getDateEnchere())));
            pstmt.setInt(4, enchere.getMontantEnchere());
            pstmt.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Enchere selectByID(int noArticle) {

        Enchere enchere = null;
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
                enchere.noEnchere = (res.getInt(1));
                enchere.setNoEncherisseur(res.getInt(2));
                enchere.setNoArticle(res.getInt(3));
                enchere.setDateEnchere(res.getDate(4));
                enchere.setMontantEnchere(res.getInt(5));
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
            DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_utilisateur=? and no_article=?;";
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
