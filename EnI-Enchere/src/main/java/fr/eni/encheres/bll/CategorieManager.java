package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.util.List;

public class CategorieManager {
    private final CategorieDAO categorieDao;

    public CategorieManager() {
        this.categorieDao = DAOFactory.getCategorieDAO();
    }
    public void addCategorie(String libelle)
    {
        categorieDao.addCategorie(libelle);
    }
    public void addCategorie(Categorie categorie){
        categorieDao.addCategorie(categorie);
    }
    public void updateCategorie(int noCategorie, String libelle){
        categorieDao.updateCategorie(noCategorie, libelle);
    }
    public void updateCategorie(Categorie categorie){
        categorieDao.updateCategorie(categorie);
    }
    public List<Categorie> getAllCategories(){
        return categorieDao.getAllCategories();
    }
    public Categorie getCategorieById(int noCategorie){
        return categorieDao.getCategorieById(noCategorie);
    }
    public Categorie getCategorieByLibelle(String libelle){
        return categorieDao.getCategorieByLibelle(libelle);
    }
}
