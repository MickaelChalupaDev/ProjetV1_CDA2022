package fr.eni.enchere.controllers.objectSent;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class ObjectSentAccueil {
    public ObjectSentAccueil() {}


    public ObjectSentAccueil(List<Article> articles){
        this.articles = articles;
    }

    public ObjectSentAccueil(List<Article> articles, String searched, String categorieSelected,
                             boolean checkedEnchereOuverte, boolean checkedMesEncheres, boolean checkedMesEncheresRemportees,
                             boolean checkedMesVentesEnCours, boolean checkedVentesNonDebutees, boolean checkedVentesTerminees,
                             String filtreVenteAffichees) {
        this.articles = articles;
        this.searched = searched;
        this.categorieSelected = categorieSelected;
        this.checkedEnchereOuverte = checkedEnchereOuverte;
        this.checkedMesEncheres = checkedMesEncheres;
        this.checkedMesEncheresRemportees = checkedMesEncheresRemportees;
        this.checkedMesVentesEnCours = checkedMesVentesEnCours;
        this.checkedVentesNonDebutees = checkedVentesNonDebutees;
        this.checkedVentesTerminees = checkedVentesTerminees;
        this.filtreVenteAffichees = filtreVenteAffichees;
    }

    Utilisateur utilisateur = null;
    List<Article> articles = new ArrayList<Article>();
    public String searched = "";
    public String categorieSelected = "";
    public boolean checkedEnchereOuverte = true;
    public boolean checkedMesEncheres = false;
    public boolean checkedMesEncheresRemportees = false;
    public boolean checkedMesVentesEnCours = false;
    public boolean checkedVentesNonDebutees = false;
    public boolean checkedVentesTerminees = false;
    public String filtreVenteAffichees = "achats";

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public String getSearched() {
        return searched;
    }

    public String getCategorieSelected() {
        return categorieSelected;
    }

    public boolean isCheckedEnchereOuverte() {
        return checkedEnchereOuverte;
    }

    public boolean isCheckedMesEncheres() {
        return checkedMesEncheres;
    }

    public boolean isCheckedMesEncheresRemportees() {
        return checkedMesEncheresRemportees;
    }

    public boolean isCheckedMesVentesEnCours() {
        return checkedMesVentesEnCours;
    }

    public boolean isCheckedVentesNonDebutees() {
        return checkedVentesNonDebutees;
    }

    public boolean isCheckedVentesTerminees() {
        return checkedVentesTerminees;
    }

    public String getFiltreVenteAffichees() {
        return filtreVenteAffichees;
    }
}
