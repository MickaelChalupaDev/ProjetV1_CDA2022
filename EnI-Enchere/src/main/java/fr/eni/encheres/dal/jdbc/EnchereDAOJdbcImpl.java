package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.dal.EnchereDAO;

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

            SELECT_ENCHERE = "SELECT * FROM ENCHERES WHERE no_utilisateur=? AND no_article=?";
            pstmt = con.prepareStatement(SELECT_ENCHERE);

            pstmt.setInt(1, enchere.getNoEncherisseur());
            pstmt.setInt(2, enchere.getNoArticle());
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                UPDATE_ENCHERE = "UPDATE ENCHERES SET date_enchere= ?, montant_enchere=? WHERE no_utilisateur=? and no_article=?;";
                pstmt = con.prepareStatement(UPDATE_ENCHERE);
                pstmt.setTimestamp(1, Timestamp.valueOf((String) sdf.format(enchere.getDateEnchere())));
                pstmt.setInt(2, enchere.getMontantEnchere());
                pstmt.setInt(3, enchere.getNoEncherisseur());
                pstmt.setInt(4, enchere.getNoArticle());

                pstmt.executeUpdate();

            } else {
                INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES(?,?,?,?);";

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
