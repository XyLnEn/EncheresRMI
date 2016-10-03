package implementation;

import interfaces.IAcheteur;

import java.util.HashMap;
import java.util.Map;

public class ListeInscrits {

	private Map<IAcheteur, String> inscrits;
	//private List<Participant> inscrits;

	public ListeInscrits() {
		inscrits = new HashMap<>(); 
				//new Vector<Participant>();
		
	}
	
	public int taille() {
		return inscrits.size();
	}
	
	public void add(IAcheteur ach, String pseudo) {
		inscrits.put(ach, pseudo);
	}
	
	public String getPseudo(IAcheteur ach) {
		return inscrits.get(ach);
	}
	
	/**
	 * @return the inscrits
	 */
	public Map<IAcheteur, String> getInscrits() {
		return inscrits;
	}
}
