package implementation;

import java.util.List;
import java.util.Vector;

public class ListeEncheres {
	
	private List<Enchere> listeEnchere;

	public ListeEncheres() {
		listeEnchere = new Vector<Enchere>();
	}
	
	public void add(Enchere ench) {
		listeEnchere.add(ench);
	}
	
	public int taille() {
		return listeEnchere.size();
	}
	
	/**
	 * @return the listeEnchere
	 */
	public List<Enchere> getListeEnchere() {
		return listeEnchere;
	}

}
