package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;


public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String INSERT_UTILISATEUR=	"INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur ) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_UTILISATEUR=	"UPDATE UTILISATEURS SET pseudo=?, set nom=?,prenom=?, email=?, set telephone=?, set rue=?, set  code_postal=?, set vile=?, set mot_de_passe=? WHERE no_utilisateur=?;";
	private static final String DELETE_UTILISATEUR="DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";
	private static final String FIND_UTILISATEUR="SELECT * FROM UTILISATEURS WHERE pseudo=?;";
	private static final String LOGIN_UTILISATEUR="SELECT * FROM UTILISATEURS WHERE email=? and mot_de_passe=?;";
	
	public EnchereDAOJdbcImpl() {
		super();
	}
	
	

}
