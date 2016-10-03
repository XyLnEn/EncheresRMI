package implementation;

import interfaces.IAcheteur;

public class Participant {
	private String pseudo;
	private IAcheteur acheteur;
	
	public Participant(String ps, IAcheteur a) {
		pseudo = ps;
		acheteur = a;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public IAcheteur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(IAcheteur ach) {
		this.acheteur = ach;
	}
	
	
}
