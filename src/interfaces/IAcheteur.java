package interfaces;

import implementation.ObjetEnVente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAcheteur extends Remote {

	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException;
	
	public void objetVendu() throws RemoteException;
	
	public void nouveauPrix(int prix, String pseudo) throws RemoteException;
	
}
