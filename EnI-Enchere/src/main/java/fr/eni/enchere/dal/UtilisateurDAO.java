package fr.eni.enchere.dal;


import fr.eni.enchere.bo.Utilisateur;

public interface UtilisateurDAO {
	public void save(Utilisateur utilisateur);
	public void update(Utilisateur utilisateur);
	public void delete(Utilisateur utilisateur);
	public Utilisateur find(String pseudo);
	public Utilisateur login(String pseudo, String motDePasse);
	public Utilisateur find(int id);
}
