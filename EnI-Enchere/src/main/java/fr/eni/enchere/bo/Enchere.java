package fr.eni.encheres.bo;

import java.util.Date;

public class Enchere {
	private int noEnchere;
	private int noUtilisateurEnchereur;
	private int montantEnchere;
	private Date dateEnchere;
	private Article article;
	
	public Enchere() {
		super();
	}
	
    public Enchere(int noEnchere, int noUtilisateurEnchereur, int montantEnchere,
			Date dateEnchere, Article article) {
		super();
		this.setNoEnchere(noEnchere);
		this.setNoUtilisateurEnchereur(noUtilisateurEnchereur);
		this.setMontantEnchere(montantEnchere);
		this.setDateEnchere(dateEnchere);
		this.setArticle(article);
	}

    public int getNoEnchere() {
		return noEnchere;
	}
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setNoUtilisateurEnchereur(int noUtilisateurEnchereur) {
		this.noUtilisateurEnchereur = noUtilisateurEnchereur;
	}
	public int getNoUtilisateurEnchereur() {
		return noUtilisateurEnchereur;
	}
	

}
