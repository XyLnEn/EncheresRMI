package implementation.client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import ihm.IHMClient;
import implementation.serveur.ObjetEnVente;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur  {

	private String nom;
	private String id;
	private int prix;
	private ObjetEnVente obj;
	private String nomMaxDonnateur;

	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException {
		obj = Objet;
		this.prix = prix;

	}

	@Override
	public void objetVendu() throws RemoteException {
		
		
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) throws RemoteException {
		this.prix = prix;
		nomMaxDonnateur = pseudo;

	}
	

	public static void main(String[] args) {
		
		IHMClient guiclient = new IHMClient();
		
		IServeurVente serveurVente;
		try { 
			 serveurVente = (IServeurVente)Naming.lookup("//localhost:8810/serveur");
		} catch (Exception e) {
			System.out.println("erreur sur client");
		}
		
		
	}
}
