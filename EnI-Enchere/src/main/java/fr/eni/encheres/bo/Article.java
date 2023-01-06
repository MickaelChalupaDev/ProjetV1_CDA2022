package fr.eni.encheres.bo;

import fr.eni.encheres.bll.EnchereManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
	private int noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebutEncheres;
	private Date dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private EtatVente etatVente;
	private Utilisateur vendeur;
	private String categorie;  // Il s'agit du libellé de la catégorie
	private String nomPhoto;  // nom du fichier sur un dossier du serveur
	private List<String> adresse= new ArrayList<String>();

	public Article(){}
	public Article(int noArticle, String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres,
				   int miseAPrix, int prixVente, EtatVente etatVente, Utilisateur vendeur, String categorie,
				   String nomPhoto, List<String> adresse) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.vendeur = vendeur;
		this.categorie = categorie;
		this.nomPhoto = nomPhoto;
		this.adresse = adresse;
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public EtatVente getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(EtatVente etatVente) {
		this.etatVente = etatVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getNomPhoto() {
		return nomPhoto;
	}

	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}

	public List<String> getAdresse() {
		return adresse;
	}

	public void setAdresse(List<String> adresse) {
		this.adresse = adresse;
	}
	
	public String toString() {
		return this.getNoArticle() + " " +this.getNomArticle() + " " + this.categorie; 
	}
	
	public void copy(Article articleACopier) {
		this.setNomArticle(articleACopier.getNomArticle());
		this.setDescription(articleACopier.getDescription());
		this.setDateDebutEncheres(articleACopier.getDateDebutEncheres());
		this.setDateFinEncheres(articleACopier.getDateFinEncheres());
		this.setMiseAPrix(articleACopier.getMiseAPrix());
		this.setPrixVente(articleACopier.getPrixVente());
		this.setEtatVente(articleACopier.getEtatVente());
		this.setVendeur(articleACopier.getVendeur());
		this.setCategorie(articleACopier.getCategorie());
		if (articleACopier.getNomPhoto()!=null) {
		this.setNomPhoto(articleACopier.getNomPhoto());}
		this.setAdresse(articleACopier.getAdresse());
	}

	public int getUpdatedPrix(){
		Enchere currEnchere = EnchereManager.lire(noArticle);
		if(currEnchere != null){
			if(currEnchere.getMontantEnchere() != 0){
				return currEnchere.getMontantEnchere();
			}
		}
		return prixVente;
	}
}
