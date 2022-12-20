package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DAOFactory;
import fr.eni.enchere.dal.UtilisateurDAO;

import java.util.List;

public class UtilisateurManager {
	
	private static UtilisateurDAO daoUtilisateurs;
	
	public UtilisateurManager() {
		daoUtilisateurs= DAOFactory.getUtilisateurDAO();
	}
	public void creerUtilisateur(Utilisateur utilisateur) {
		daoUtilisateurs.creerUtilisateur(utilisateur);
	}
	public void supprimerUtilisateur(int noUtilisateur) {
		daoUtilisateurs.supprimerUtilisateur(noUtilisateur);
	}
	public Utilisateur lireUtilisateur(String pseudo) {
		return daoUtilisateurs.lireUtilisateur(pseudo);
		
	}
	public List<Utilisateur> lireTousUtilisateur(){
		return daoUtilisateurs.lireTousUtilisateur();
	}
}
