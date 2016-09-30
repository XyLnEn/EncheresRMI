package implementation;

import interfaces.IAcheteur;

public class Participant {
	private String pseudo;
	private IAcheteur ach;
	
	public Participant(String ps, IAcheteur a) {
		pseudo = ps;
		ach = a;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public IAcheteur getAch() {
		return ach;
	}

	public void setAch(IAcheteur ach) {
		this.ach = ach;
	}
	
	
}
