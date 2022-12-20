package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {

	private int noArticle;
	private String nomArticle;
	private String descritpion;
	private Date dateDebutEncheres;
	private Date dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private Utilisateur vendeur;
	private Categorie categorie;  // Il s'agit du libellé de la catégorie
	private String nomPhoto;  // nom du fichier sur un dossier du serveur
	
	private List<String> adresse= new ArrayList<String>();
	public Article() {
		super();
	}
	
	
	public Article(int noArticle, String nomArticle, String descritpion, Date dateDebutEncheres, Date dateFinEncheres,
			int miseAPrix, int prixVente, String etatVente, String categorie, List<String> adresse, String nomPhoto, Utilisateur vendeur)
	{
		super();
		this.setNoArticle(noArticle);
		this.setNomArticle(nomArticle);
		this.setDescritpion(descritpion);
		this.setDateDebutEncheres(dateDebutEncheres);
		this.setDateFinEncheres(dateFinEncheres);
		this.setMiseAPrix(miseAPrix);
		this.setPrixVente(prixVente);
		this.setEtatVente(etatVente);
		this.setCategorie(categorie);
		this.setAdresse(adresse);
		this.setNomPhoto(nomPhoto);
	}


	public Utilisateur getVendeur() {
		return vendeur;
	}


	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
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
	public String getDescritpion() {
		return descritpion;
	}
	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
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
	public String getEtatVente() {
		return etatVente;
	}
	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}
	public String getcategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public List<String> getAdresse() {
		return adresse;
	}
	public void setAdresse(List<String> adresse) {
		this.adresse = adresse;
	}
	
	public String getNomPhoto() {
		return nomPhoto;
	}


	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}


	public String getCategorie() {
		return categorie;
	}
	
}
