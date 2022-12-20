package fr.eni.encheres.bo;

public class Utilisateur {

		private int noUtilisateur;
		private String pseudo;
		private String prenom;
		private String email;
		private String telephone;
		private String rue;
		private String codePostal;
		private String ville;
		private String motDePasse;
		private String credit;
		private int administrateur;
		
		public Utilisateur() {
			super();
		}

		
		public Utilisateur(int noUtilisateur, String pseudo, String prenom, String email, String telephone, String rue,
				String codePostal, String ville, String motDePasse, String credit, int administrateur) {
			super();
			this.setNoUtlisateur(noUtilisateur);
			this.setPseudo(pseudo);
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

		public String getCredit() {
			return credit;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public int getAdministrateur() {
			return administrateur;
		}

		public void setAdministrateur(int administrateur) {
			this.administrateur = administrateur;
		}
		 
		
}
