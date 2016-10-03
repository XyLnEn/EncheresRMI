package implementation;

import java.rmi.Naming;
import java.rmi.RemoteException;

import ihm.IHMClient;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur  {

	
	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void objetVendu() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) throws RemoteException {
		// TODO Auto-generated method stub

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
