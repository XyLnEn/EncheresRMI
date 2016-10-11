package implementation.serveur;

import interfaces.IAcheteur;

import java.util.ArrayList;
import java.util.List;


public class ListeInscrits {

	private List<IAcheteur> inscrits;
	//private List<Participant> inscrits;

	public ListeInscrits() {
		inscrits = new ArrayList<IAcheteur>(); 
				//new Vector<Participant>();
		
	}
	public void inscrire(IAcheteur ach) {
		this.inscrits.add(ach);
	}

	public List<IAcheteur> getInscrits() {
		return inscrits;
	}

	public void setInscrits(List<IAcheteur> inscrits) {
		this.inscrits = inscrits;
	}

	
	
}
