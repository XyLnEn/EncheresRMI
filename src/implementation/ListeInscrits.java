package implementation;

import interfaces.IAcheteur;

import java.util.List;
import java.util.Vector;

public class ListeInscrits {
	private List<Participant> inscrits;
	
	public ListeInscrits() {
		inscrits = new Vector<Participant>();
	}
	
	public int taille() {
		return inscrits.size();
	}
	
	public void add(String pseudo, IAcheteur ach) {
		synchronized (this) {
			inscrits.add(new Participant(pseudo, ach));
		}
	}
	
	public List<Participant> getInscrits() {
		return inscrits;
	}
}
