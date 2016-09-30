package implementation;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur {

	@Override
	public boolean nouvelleSoumission(String descrObjet, int prix)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void objetVendu() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nouveauPrix(int prix) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		IServeurVente serveurVente;
		try { 
			 serveurVente = (IServeurVente)Naming.lookup("//localhost:8802/serveur");
			 int i = serveurVente.testmethode();
			 System.out.println(i);
		} catch (Exception e) {
			System.out.println("erreur sur client");
		}
		
		
	}
}
