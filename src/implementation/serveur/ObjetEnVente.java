package implementation.serveur;

import java.io.Serializable;

public class ObjetEnVente implements Serializable {
	
	private String nom;
	private String description;
	private int prix;
	private String image;
	
	public ObjetEnVente(String nomobj, String descr, int p, String img) {
		nom = nomobj;
		description = descr;
		prix = p;
		image = img;
	}
	
	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
