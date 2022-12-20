package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public void creerUtilisateur(Utilisateur utilisateur);
	public void supprimerUtilisateur(int noUtilisateur);
	public Utilisateur lireUtilisateur(String pseudo);
	public List<Utilisateur> lireTousUtilisateur();

}
