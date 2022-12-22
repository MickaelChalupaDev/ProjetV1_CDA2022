package fr.eni.encheres.dal;


import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public void save(Utilisateur utilisateur);
	public void update(Utilisateur utilisateur);
	public void delete(Utilisateur utilisateur);
	public Utilisateur find(String pseudo);
	public Utilisateur login(String pseudo, String motDePasse);
}
