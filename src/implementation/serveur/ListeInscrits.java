package implementation.serveur;

import interfaces.IAcheteur;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;


public class ListeInscrits  implements Serializable {

	private Map<IAcheteur, String> inscrits;

	public ListeInscrits() {
		inscrits = new Hashtable<>(); 
		
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
