package implementation.serveur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ListeObjetEnVente {

	private List<ObjetEnVente> objetsVentes = new ArrayList<ObjetEnVente>();
	

	public ListeObjetEnVente() {
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("ressources/listeObjet.txt"));
			String ligne;
			while ((ligne = br.readLine()) != null){
				objetsVentes.add(new ObjetEnVente(ligne.split(", ")[0], ligne.split(", ")[1], Integer.parseInt(ligne.split(", ")[2]), ligne.split(", ")[3]));
			}
			br.close(); 
		}		
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void afficherListe() {
		for (ObjetEnVente obj : objetsVentes) {
			System.out.println("Nom de l'objet : " + obj.getNom());
			System.out.println("Description de l'objet : " + obj.getDescription());
			System.out.println("Prix initial de l'objet : " + obj.getPrix());
			System.out.println("Lien de l'image de l'objet : " + obj.getImage());
		}
	}
	
	public List<ObjetEnVente> getObjetsVentes() {
		return objetsVentes;
	}

	public void setObjetsVentes(List<ObjetEnVente> objetsVentes) {
		this.objetsVentes = objetsVentes;
	}
	
}
