package fr.eni.enchere.bo;

import java.util.Date;

public class Enchere {
	private int noEnchere;
	private int noArticle;
	private int noUtilisateur;
	private String nomArticle;
	private int montantEnchere;
	private Date dateEnchere;

	public Enchere(int noEnchere, int noArticle, int noUtilisateur, String nomArticle, int montantEnchere,
			Date dateEnchere) {
		this.setNoEnchere(noEnchere);
		this.setNoArticle(noArticle);
		this.setNoUtilisateur(noUtilisateur);
		this.setNomArticle(nomArticle);
		this.setMontantEnchere(montantEnchere);
		this.setDateEnchere(dateEnchere);
	}

    public int getNoEnchere() {
		return noEnchere;
	}
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
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
}
