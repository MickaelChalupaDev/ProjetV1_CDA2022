package fr.eni.encheres.bo;

import java.util.Date;

public class Enchere {
	private int noEncherisseur;
	private int noArticle;
	private Date dateEnchere;
	private int montantEnchere;
	
	
	
	public Enchere() {
		super();
	}
	
    public Enchere(int noEncherisseur,int noArticle, Date dateEnchere, int montantEnchere) {
		super();
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
	public String toString() {
		return this.getNoEncherisseur()+" " + this.getNoArticle() + " " + this.getDateEnchere() + " " +this.getMontantEnchere(); 
	}
	

}
