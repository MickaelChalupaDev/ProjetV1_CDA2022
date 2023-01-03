package fr.eni.encheres.dal;


import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	public Utilisateur save(Utilisateur utilisateur);
	public void update(Utilisateur utilisateur);
	public void delete(Utilisateur utilisateur);
	public Utilisateur find(String pseudoEmail);
	public Utilisateur login(String pseudo, String motDePasse);
	public Utilisateur find(int id);
}
