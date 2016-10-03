package implementation;

import interfaces.IAcheteur;

public class Enchere {

	private IAcheteur enchereur;
	private int enchere;
	
	
	public Enchere(IAcheteur enchereur, int enchere) {
		super();
		this.enchereur = enchereur;
		this.enchere = enchere;
	}
	
	public IAcheteur getEnchereur() {
		return enchereur;
	}
	public void setEnchereur(IAcheteur enchereur) {
		this.enchereur = enchereur;
	}
	public int getEnchere() {
		return enchere;
	}
	public void setEnchere(int enchere) {
		this.enchere = enchere;
	}
	
	
}
