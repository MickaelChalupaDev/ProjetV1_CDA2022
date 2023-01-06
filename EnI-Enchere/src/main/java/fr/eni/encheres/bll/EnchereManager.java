package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.EtatVente;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;

import java.util.List;

public class EnchereManager {
    private static final EnchereDAO daoEncheres = DAOFactory.getEnchereDAO();
    public static Enchere lire(int noEnchere) {
        return daoEncheres.selectByID(noEnchere);
    }
    public static void encherir(Enchere enchere) {
        daoEncheres.insert(enchere);
    }

    public static void annuler(Enchere enchere) {
        daoEncheres.delete(enchere);
    }

}
