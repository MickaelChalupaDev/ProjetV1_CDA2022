package fr.eni.enchere.dal.jdbc;

import java.util.List;

import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.EnchereDAO;


public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String INSERT_UTILISATEUR=	"INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur ) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
	private static final String UPDATE_UTILISATEUR=	"UPDATE UTILISATEURS SET pseudo=?, set nom=?,prenom=?, email=?, set telephone=?, set rue=?, set  code_postal=?, set vile=?, set mot_de_passe=? WHERE no_utilisateur=?;";
	private static final String DELETE_UTILISATEUR="DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";
	private static final String FIND_UTILISATEUR="SELECT * FROM UTILISATEURS WHERE pseudo=?;";
	private static final String LOGIN_UTILISATEUR="SELECT * FROM UTILISATEURS WHERE email=? and mot_de_passe=?;";
	
	public EnchereDAOJdbcImpl() {
		super();
	}

	@Override
	public List<Enchere> selectAll(String nomArticle, String categorie, int noUtilisateurEnchere, String dateEnchere) {
		return null;
	}

	@Override
	public List<Enchere> selectByUser(String nomArticle, String categorie, int noUtilisateurArticle, String dateDebut, String dateEnchere) {
		return null;
	}

	@Override
	public void insert(Enchere enchere) {

	}

	@Override
	public void update(Enchere enchere) {

	}
}
