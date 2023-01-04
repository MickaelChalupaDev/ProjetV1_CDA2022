package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
    //public Article creerArticle(Article article);
    public void addCategorie(String libelle);
    public void addCategorie(Categorie categorie);
    public void updateCategorie(int noCategorie, String libelle);
    public void updateCategorie(Categorie categorie);
    public List<Categorie> getAllCategories();
    public Categorie getCategorieById(int noCategorie);
    public Categorie getCategorieByLibelle(String libelle);
}
