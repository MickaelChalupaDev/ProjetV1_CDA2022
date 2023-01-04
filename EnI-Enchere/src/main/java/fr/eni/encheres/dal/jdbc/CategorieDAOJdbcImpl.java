package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOJdbcImpl implements CategorieDAO {
    public String INSERT = "INSERT INTO CATEGORIES ([libelle]) VALUES (?)";
    public String UPDATE = "UPDATE CATEGORIES SET [libelle] = ? WHERE no_categorie = ?";
    public String SELECT = "SELECT TOP(1) * FROM CATEGORIES WHERE no_categorie = ?";
    public String SELECTALL = "SELECT * FROM CATEGORIES";

    public String SELECTBYLIBELLE = "SELECT * FROM CATEGORIES WHERE libelle = ?";
    @Override
    public void addCategorie(String libelle) {
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, libelle);

            ResultSet rs = pstmt.executeQuery();
            con.close();

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategorie(Categorie categorie) {
        addCategorie(categorie.libelle);
    }

    @Override
    public void updateCategorie(int noCategorie, String libelle) {
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, libelle);
            pstmt.setInt(2, noCategorie);


            ResultSet rs = pstmt.executeQuery();
            con.close();

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategorie(Categorie categorie) {
        updateCategorie(categorie.noCategorie, categorie.libelle);
    }

    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        categories.add(new Categorie(0, "Toutes"));
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(SELECTALL, PreparedStatement.RETURN_GENERATED_KEYS);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                categories.add(new Categorie(rs.getInt(1),rs.getString(2)));
            }
            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Categorie getCategorieById(int noCategorie) {
        Categorie categorie = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(SELECT, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, noCategorie);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                categorie = new Categorie(rs.getInt(1),rs.getString(2));
            }
            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }

    @Override
    public Categorie getCategorieByLibelle(String libelle){
        Categorie categorie = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(SELECTBYLIBELLE, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,libelle);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                categorie = new Categorie(rs.getInt(1),rs.getString(2));
            }
            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return categorie;
    }
}
