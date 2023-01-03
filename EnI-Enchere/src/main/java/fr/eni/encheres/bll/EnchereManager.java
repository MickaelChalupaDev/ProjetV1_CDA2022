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


    public List<Article> rechercherTout(String nomArticle, String categorie) {
        return this.daoEncheres.selectALL(nomArticle, categorie);
    }

    public List<Article> rechercherParAchat(String nomArticle, String categorie, int noEncherisseur, boolean etatEnchere) {

        return this.daoEncheres.selectByBuyer(nomArticle, categorie, noEncherisseur, etatEnchere);

    }

    public List<Article> rechercherParVente(String nomArticle, String categorie, int noVendeur, EtatVente etatVente) {

        return this.daoEncheres.selectBySeller(nomArticle, categorie, noVendeur, etatVente);
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
