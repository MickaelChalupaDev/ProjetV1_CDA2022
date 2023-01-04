package fr.eni.encheres.bll;


import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	static final UtilisateurDAO daoUtilisateurs = DAOFactory.getUtilisateurDAO();

	public static Utilisateur creerUtilisateur(Utilisateur utilisateur) {
		return daoUtilisateurs.save(utilisateur);
	}
	public static void modifierUtilisateur(Utilisateur utilisateur) {
		daoUtilisateurs.update(utilisateur);
	}
	public static void supprimerUtilisateur(Utilisateur utilisateur) {
		daoUtilisateurs.delete(utilisateur);
	}
	public static Utilisateur lireUtilisateur(String pseudoEmail) {
		return daoUtilisateurs.find(pseudoEmail);
	}
	public static Utilisateur lireUtilisateur(int id) {
		return daoUtilisateurs.find(id);
	}
	
	public static Utilisateur connecter(String pseudo, String motDePasse) {
		return daoUtilisateurs.login(pseudo, motDePasse);
	}
}
