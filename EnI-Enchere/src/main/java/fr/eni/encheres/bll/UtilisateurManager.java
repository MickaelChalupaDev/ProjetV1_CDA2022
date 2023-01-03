package fr.eni.encheres.bll;


import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	private UtilisateurDAO daoUtilisateurs;
	
	public UtilisateurManager() {
		daoUtilisateurs= DAOFactory.getUtilisateurDAO();
	}
	public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
		return this.daoUtilisateurs.save(utilisateur);
	}
	public void modifierUtilisateur(Utilisateur utilisateur) {
		this.daoUtilisateurs.update(utilisateur);
	}
	public void supprimerUtilisateur(Utilisateur utilisateur) {
		this.daoUtilisateurs.delete(utilisateur);
	}
	public Utilisateur lireUtilisateur(String pseudoEmail) {
		return this.daoUtilisateurs.find(pseudoEmail);
	}
	public Utilisateur lireUtilisateur(int id) {
		return this.daoUtilisateurs.find(id);
	}
	
	public Utilisateur connecter(String pseudo, String motDePasse) {
		return this.daoUtilisateurs.login(pseudo, motDePasse);
	}
}
