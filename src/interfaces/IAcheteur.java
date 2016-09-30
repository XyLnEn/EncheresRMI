package interfaces;

import implementation.ObjetEnVente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAcheteur extends Remote {

	public boolean nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException;
	
	public void objetVendu() throws RemoteException;
	
	public boolean nouveauPrix(int prix) throws RemoteException;
	
}
