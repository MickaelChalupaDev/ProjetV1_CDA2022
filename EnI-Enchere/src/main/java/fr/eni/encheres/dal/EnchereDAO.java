package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;

import java.util.List;


public interface EnchereDAO {
	public Enchere selectByID(int noEnchere);
	public void insert(Enchere enchere);
	public void delete(Enchere enchere);
}
