package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAcheteur extends Remote {

	public boolean nouvelleSoumission(String descrObjet, int prix) throws RemoteException;
	
	public void objetVendu() throws RemoteException;
	
	public boolean nouveauPrix(int prix) throws RemoteException;
	
}
