package fr.eni.encheres.bo;

public class Utilisateur {

		private int noUtilisateur;
		private String pseudo;
		private String nom;
		private String prenom;
		private String email;
		private String telephone;
		private String rue;
		private String codePostal;
		private String ville;
		private String motDePasse;
		private int credit;
		private Boolean administrateur;
		
		public Utilisateur() {}

	
		public Utilisateur(int noUtilisateur, String pseudo,String nom, String prenom, String email, String telephone, String rue,
				String codePostal, String ville, String motDePasse, int credit, Boolean administrateur) {
			super();
			this.setNoUtlisateur(noUtilisateur);
			this.setPseudo(pseudo);
			this.setNom(nom);
			this.setPrenom(prenom);
			this.setEmail(email);
			this.setTelephone(telephone);
			this.setRue(rue);
			this.setCodePostal(codePostal);
			this.setVille(ville);
			this.setMotDePasse(motDePasse);
			this.setCredit(credit);
			this.setAdministrateur(administrateur);
		}


		public String getNom() {
			return nom;
		}


		public void setNom(String nom) {
			this.nom = nom;
		}


		public int getNoUtilisateur() {
			return noUtilisateur;
		}

		public void setNoUtlisateur(int noUtlisateur) {
			this.noUtilisateur = noUtlisateur;
		}

		public String getPseudo() {
			return pseudo;
		}

		public void setPseudo(String pseudo) {
			this.pseudo = pseudo;
		}

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getRue() {
			return rue;
		}

		public void setRue(String rue) {
			this.rue = rue;
		}

		public String getCodePostal() {
			return codePostal;
		}

		public void setCodePostal(String codePostal) {
			this.codePostal = codePostal;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public String getMotDePasse() {
			return motDePasse;
		}

		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}

		public int getCredit() {
			return credit;
		}

		public void setCredit(int credit) {
			this.credit = credit;
		}

		public Boolean getAdministrateur() {
			return administrateur;
		}

		public void setAdministrateur(Boolean administrateur) {
			this.administrateur = administrateur;
		}
		
		public static String hashPwd(String MotDePasse) {
			System.out.println("méthode hashPwd à définir par Mickael");
			return MotDePasse;
		}
		public boolean getIsValid(){
			return this.getNoUtilisateur() >= 0;
		}
		
		public String toString() {
			return this.getPseudo()+ " " + this.getNom() ;
		}
}
