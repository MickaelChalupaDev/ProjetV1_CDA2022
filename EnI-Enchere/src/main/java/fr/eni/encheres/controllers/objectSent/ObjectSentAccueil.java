package fr.eni.encheres.controllers.objectSent;

//import fr.eni.encheres.bo.Article;
//
//import java.util.ArrayList;
//import java.util.List;
import fr.eni.encheres.bll.CategorieManager;

import java.util.Objects;

public class ObjectSentAccueil {

    public ObjectSentAccueil(/*List<Article> articles*/){
//        this.articles = articles;
        this.searched = "";
        this.categorieSelected = "0";
        this.checkedEnchereOuverte = true;
        this.filtreVenteAffichees = "achats";
    }

    public ObjectSentAccueil(String searched, String categorieSelected,String filtreVenteAffichees,
                             String checkedEnchereOuverte, String checkedMesEncheres, String checkedMesEncheresRemportees,
                             String checkedMesVentesEnCours, String checkedVentesNonDebutees, String checkedVentesTerminees) {
        this.searched = searched.trim();
        this.categorieSelected = categorieSelected;
        this.filtreVenteAffichees = filtreVenteAffichees;
        if(filtreVenteAffichees != null){
            if(Objects.equals(filtreVenteAffichees, "achats")){//Note : les checkbox renvoient soit "null" soit "on" sous string
                this.checkedMesEncheres = checkedMesEncheres != null;
                this.checkedEnchereOuverte = checkedEnchereOuverte != null;
                this.checkedMesEncheresRemportees = checkedMesEncheresRemportees != null;
            }else{
                this.checkedMesVentesEnCours = checkedMesVentesEnCours != null;
                this.checkedVentesNonDebutees = checkedVentesNonDebutees != null;
                this.checkedVentesTerminees = checkedVentesTerminees != null;
            }
        }else{
            this.filtreVenteAffichees = "achats";
            this.checkedEnchereOuverte = true;
        }

    }

//    public List<Article> articles = new ArrayList<Article>();
    public String searched = "";
    public String categorieSelected = "";
    public boolean checkedEnchereOuverte = false;
    public boolean checkedMesEncheres = false;
    public boolean checkedMesEncheresRemportees = false;
    public boolean checkedMesVentesEnCours = false;
    public boolean checkedVentesNonDebutees = false;
    public boolean checkedVentesTerminees = false;
    public String filtreVenteAffichees = "achats";

//    public List<Article> getArticles() {
//        return articles;
//    }
//    public void addArticle(List<Article> articlesL){
//        this.articles.addAll(articlesL);
//    }
//    public void addArticle(Article article){
//        this.articles.add(article);
//    }

    public String getSearched() {
        if(searched == null)
        {
            return "";
        }
        return searched;
    }

    public String getCategorieSelected() {
        if(Objects.equals(categorieSelected, ""))
        {
            categorieSelected = "0";
        }
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

    @Override
    public String toString() {
        return "Searched : {"+searched +"}; " + "Libelle Categorie : {" + CategorieManager.getCategorieById(Integer.parseInt(categorieSelected)).libelle
                + "}; Filtre Affiches : {" + filtreVenteAffichees + "}; \n Checked EnchereOuverte : {"
                + (checkedEnchereOuverte ? "Oui": "Non") + "}; Checked MesEncheres : {"
                + (checkedMesEncheres ? "Oui" : "Non") + "}; Checked EncheresRemportees : {"
                + (checkedMesEncheresRemportees ? "Oui": "Non") + "}; \n Checked VentesEnCours : {"
                + (checkedMesVentesEnCours ? "Oui":"Non") + "}; Checked VentesNonDebutees : {"
                + (checkedVentesNonDebutees ?"Oui":"Non" ) + "}; Checked VentesTerminees : {"
                + (checkedVentesTerminees ? "Oui":"Non") +"};";
    }
}
