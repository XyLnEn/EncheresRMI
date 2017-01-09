package implementation.serveur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListeObjetEnVente implements Serializable {

	private List<ObjetEnVente> objetsVentes;
	

	public ListeObjetEnVente() {
		objetsVentes = new ArrayList<ObjetEnVente>();
	}
	
	public void ajouterObjet(ObjetEnVente obj) {
		objetsVentes.add(obj);
	}
	
	public void afficherListe() {
		for (ObjetEnVente obj : objetsVentes) {
			System.out.println("Nom de l'objet : " + obj.getNom());
			System.out.println("Description de l'objet : " + obj.getDescription());
			System.out.println("Prix initial de l'objet : " + obj.getPrix());
			System.out.println("Lien de l'image de l'objet : " + obj.getImage());
		}
	}
	
	public ObjetEnVente getVenteActuelle() {
		return objetsVentes.get(0);
	}
	
	public void FinirVente() {
		objetsVentes.remove(0);
	}
	
	public List<ObjetEnVente> getObjetsVentes() {
		return objetsVentes;
	}

	public void setObjetsVentes(List<ObjetEnVente> objetsVentes) {
		this.objetsVentes = objetsVentes;
	}
	
}
