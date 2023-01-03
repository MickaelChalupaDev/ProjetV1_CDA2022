package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;

import java.util.List;

public class EnchereManager {
    private final EnchereDAO daoEncheres;

    public EnchereManager() {
        this.daoEncheres = DAOFactory.getEnchereDAO();
    }

    public Enchere lire(int noArticle) {
        return this.daoEncheres.selectByID(noArticle);
    }


    public void encherir(Enchere enchere) {
        this.daoEncheres.insert(enchere);
    }

    public void annuler(Enchere enchere) {
        this.daoEncheres.delete(enchere);
    }

}
