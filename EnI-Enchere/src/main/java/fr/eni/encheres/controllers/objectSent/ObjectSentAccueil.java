package fr.eni.encheres.controllers.objectSent;

//import fr.eni.encheres.bo.Article;
//
//import java.util.ArrayList;
//import java.util.List;
import java.util.Objects;

public class ObjectSentAccueil {

    public ObjectSentAccueil(/*List<Article> articles*/){
//        this.articles = articles;
        this.checkedEnchereOuverte = true;
    }

    public ObjectSentAccueil(String searched, String categorieSelected,String filtreVenteAffichees,
                             String checkedEnchereOuverte, String checkedMesEncheres, String checkedMesEncheresRemportees,
                             String checkedMesVentesEnCours, String checkedVentesNonDebutees, String checkedVentesTerminees) {
        this.searched = searched.trim();
        this.categorieSelected = categorieSelected;
        this.filtreVenteAffichees = filtreVenteAffichees;

        if(Objects.equals(filtreVenteAffichees, "achats")){//Note : les checkbox renvoient soit "null" soit "on" sous string
            this.checkedMesEncheres = checkedMesEncheres != null;
            this.checkedEnchereOuverte = checkedEnchereOuverte != null;
            this.checkedMesEncheresRemportees = checkedMesEncheresRemportees != null;

            System.out.println("Trying for achats");
            System.out.print(this.checkedMesEncheres);
            System.out.print(this.checkedEnchereOuverte);
            System.out.print(this.checkedMesEncheresRemportees);
        }else{
            this.checkedMesVentesEnCours = checkedMesVentesEnCours != null;
            this.checkedVentesNonDebutees = checkedVentesNonDebutees != null;
            this.checkedVentesTerminees = checkedVentesTerminees != null;
            System.out.println("Trying mesVentes");
            System.out.print(this.checkedMesVentesEnCours);
            System.out.print(this.checkedVentesNonDebutees);
            System.out.print(this.checkedVentesTerminees);
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
