package implementation;

public class ObjetEnVente {
	
	private String nom;
	private String description;
	
	public ObjetEnVente(String nomobj, String descr) {
		nom = nomobj;
		description = descr;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String descr) {
		description = descr;
	}

}
