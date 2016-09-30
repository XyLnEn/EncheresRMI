package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServeurVente extends Remote {

	public boolean inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException;
	
	public boolean rencherir(int prix, IAcheteur acheteur) throws RemoteException;
	
	public boolean tempsEcoule(IAcheteur acheteur) throws RemoteException;
	
	public int testmethode() throws RemoteException;
}
