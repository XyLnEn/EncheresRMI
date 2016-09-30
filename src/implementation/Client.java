package implementation;

import java.applet.Applet;

import java.rmi.Naming;
import java.rmi.RemoteException;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client extends Applet implements IAcheteur  {

	
	@Override
<<<<<<< HEAD
	public boolean nouvelleSoumission(ObjetEnVente Objet, int prix)
=======
	public void nouvelleSoumission(String descrObjet, int prix)
>>>>>>> 69704cbdd7f0e14263d4777f7c7e4336c0ab8c39
			throws RemoteException {
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
		
		//TODO a mettre le guiclient
		
		IServeurVente serveurVente;
		try { 
			 serveurVente = (IServeurVente)Naming.lookup("//localhost:8810/serveur");
		} catch (Exception e) {
			System.out.println("erreur sur client");
		}
		
		
	}
}
