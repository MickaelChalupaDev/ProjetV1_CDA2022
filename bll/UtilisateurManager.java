package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	private static UtilisateurDAO doaUtilisateurs;
	
	public UtilisateurManager() {
		daoUtilisateurs= DAOFactory.getUtilisateurDAO();
	}
	public void creerUtilisateur(Utilisateur utilisateur) {
		
	}
	public void supprimerUtilisateur(int noUtilisateur) {
		
	}
	public Utilisateur lireUtilisateur(String pseudo) {
		return null;
		
	}
	public List<Utilisateur> lireTousUtilisateur(){
		return null;
		
	}


}
