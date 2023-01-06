package fr.eni.encheres.bo;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;

import java.util.Date;

public class Enchere {
	public int noEnchere;
	private int noEncherisseur;
	private int noArticle;
	private Date dateEnchere;
	private int montantEnchere;

	public Enchere() {}
	
    public Enchere(int noEnchere,int noEncherisseur,int noArticle, Date dateEnchere, int montantEnchere) {
		this.noEnchere = noEnchere;
		this.setNoArticle(noArticle);
		this.setNoEncherisseur(noEncherisseur);
		this.setMontantEnchere(montantEnchere);
		this.setDateEnchere(dateEnchere);
	}
    public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}
	public Date getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public void setNoEncherisseur(int noEncherisseur) {
		this.noEncherisseur = noEncherisseur;
	}
	public int getNoEncherisseur() {
		return noEncherisseur;
	}
	public Article getArticle(){
		return ArticleManager.lireArticle(noArticle);
	}
	public Utilisateur getUtilisateur(){
		return UtilisateurManager.lireUtilisateur(noEncherisseur);
	}
	public String toString() {
		return this.getNoEncherisseur()+" " + this.getNoArticle() + " " + this.getDateEnchere() + " " +this.getMontantEnchere(); 
	}
}
