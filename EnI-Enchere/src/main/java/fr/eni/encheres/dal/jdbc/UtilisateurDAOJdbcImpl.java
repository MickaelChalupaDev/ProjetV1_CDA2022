package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur ) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET pseudo=?, nom=?,prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?;";
    private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";
    private static final String FIND_UTILISATEUR = "SELECT * FROM UTILISATEURS WHERE pseudo=? OR email=?;";
    private static final String LOGIN_UTILISATEUR = "SELECT * FROM UTILISATEURS WHERE (pseudo=? OR email=?) and mot_de_passe=?;";

    private static final String FIND_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";


    public UtilisateurDAOJdbcImpl() {
        super();
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {

        try {

            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, utilisateur.getPseudo());
            pstmt.setString(2, utilisateur.getNom());
            pstmt.setString(3, utilisateur.getPrenom());
            pstmt.setString(4, utilisateur.getEmail());
            pstmt.setString(5, utilisateur.getTelephone());
            pstmt.setString(6, utilisateur.getRue());
            pstmt.setString(7, utilisateur.getCodePostal());
            pstmt.setString(8, utilisateur.getVille());
            pstmt.setString(9, Utilisateur.hashPwd(utilisateur.getMotDePasse()));
            pstmt.setInt(10, utilisateur.getCredit());
            pstmt.setBoolean(11, utilisateur.getAdministrateur());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setNoUtlisateur(rs.getInt(1));
            }


            con.close();

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        if (utilisateur.getNoUtilisateur() != 0)
            return utilisateur;
        else
            return null;


    }

    @Override
    public void update(Utilisateur utilisateur) {
        try {
            Connection con = ConnectionDAOBdd.getConnection();
            PreparedStatement pstmt = con.prepareStatement(UPDATE_UTILISATEUR);

            pstmt.setString(1, utilisateur.getPseudo());
            pstmt.setString(2, utilisateur.getNom());
            pstmt.setString(3, utilisateur.getPrenom());
            pstmt.setString(4, utilisateur.getEmail());
            pstmt.setString(5, utilisateur.getTelephone());
            pstmt.setString(6, utilisateur.getRue());
            pstmt.setString(7, utilisateur.getCodePostal());
            pstmt.setString(8, utilisateur.getVille());
            pstmt.setString(9, Utilisateur.hashPwd(utilisateur.getMotDePasse()));
            pstmt.setInt(10, utilisateur.getCredit());
            pstmt.setBoolean(11, utilisateur.getAdministrateur());
            pstmt.setInt(12, utilisateur.getNoUtilisateur());

            pstmt.executeUpdate();

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Utilisateur utilisateur) {


        try {

            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(DELETE_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            pstmt.executeUpdate();

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur find(String pseudoEmail) {
        Utilisateur utilisateur = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(FIND_UTILISATEUR);
            pstmt.setString(1, pseudoEmail.trim());
            pstmt.setString(2, pseudoEmail.trim());
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setNoUtlisateur(res.getInt("no_utilisateur"));
                utilisateur.setPseudo(res.getString("pseudo"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));
                utilisateur.setEmail(res.getString("email"));
                utilisateur.setTelephone(res.getString("telephone"));
                utilisateur.setRue(res.getString("rue"));
                utilisateur.setCodePostal(res.getString("code_postal"));
                utilisateur.setVille(res.getString("ville"));
                utilisateur.setMotDePasse(res.getString("mot_de_passe"));
                utilisateur.setCredit(res.getInt("credit"));
                utilisateur.setAdministrateur(res.getBoolean("administrateur"));
            }

            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
       
    }

    @Override
    public Utilisateur find(int id) {
        Utilisateur utilisateur = null;
        try {
            Connection con = ConnectionDAOBdd.getConnection();

            final PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setNoUtlisateur(res.getInt("no_utilisateur"));
                utilisateur.setPseudo(res.getString("pseudo"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));
                utilisateur.setEmail(res.getString("email"));
                utilisateur.setTelephone(res.getString("telephone"));
                utilisateur.setRue(res.getString("rue"));
                utilisateur.setCodePostal(res.getString("code_postal"));
                utilisateur.setVille(res.getString("ville"));
                utilisateur.setMotDePasse(res.getString("mot_de_passe"));
                utilisateur.setCredit(res.getInt("credit"));
                utilisateur.setAdministrateur(res.getBoolean("administrateur"));
            }
            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    @Override
    public Utilisateur login(String pseudo, String motDePasse) {

        Utilisateur utilisateur = null;
        try {
            System.out.println("Trying logging with credentials : " + pseudo + " " + motDePasse);
            Connection con = ConnectionDAOBdd.getConnection();

            PreparedStatement pstmt = con.prepareStatement(LOGIN_UTILISATEUR);

            pstmt.setString(1, pseudo.trim());
            pstmt.setString(2, pseudo.trim());
            pstmt.setString(3, Utilisateur.hashPwd(motDePasse));

            final ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setNoUtlisateur(res.getInt("no_utilisateur"));
                utilisateur.setPseudo(res.getString("pseudo"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));
                utilisateur.setEmail(res.getString("email"));
                utilisateur.setTelephone(res.getString("telephone"));
                utilisateur.setRue(res.getString("rue"));
                utilisateur.setCodePostal(res.getString("code_postal"));
                utilisateur.setVille(res.getString("ville"));
                utilisateur.setMotDePasse(res.getString("mot_de_passe"));
                utilisateur.setCredit(res.getInt("credit"));
                utilisateur.setAdministrateur(res.getBoolean("administrateur"));
            }
            con.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }


}
