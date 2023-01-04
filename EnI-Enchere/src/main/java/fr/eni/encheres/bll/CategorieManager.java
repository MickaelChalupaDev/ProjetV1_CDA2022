package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

import java.util.List;

public class CategorieManager {
    private static final CategorieDAO categorieDao = DAOFactory.getCategorieDAO();

    public static void addCategorie(String libelle)
    {
        categorieDao.addCategorie(libelle);
    }
    public static void addCategorie(Categorie categorie){
        categorieDao.addCategorie(categorie);
    }
    public static void updateCategorie(int noCategorie, String libelle){
        categorieDao.updateCategorie(noCategorie, libelle);
    }
    public static void updateCategorie(Categorie categorie){
        categorieDao.updateCategorie(categorie);
    }
    public static List<Categorie> getAllCategories(){
        return categorieDao.getAllCategories();
    }
    public static Categorie getCategorieById(int noCategorie){
        Categorie c = categorieDao.getCategorieById(noCategorie);
        return c == null ? new Categorie(0, "Toutes") : c;
    }
    public static Categorie getCategorieByLibelle(String libelle){
        Categorie c = categorieDao.getCategorieByLibelle(libelle);
        return c == null ? new Categorie(0, "Toutes") : c;
    }
}
