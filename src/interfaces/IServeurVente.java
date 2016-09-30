package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServeurVente extends Remote {

	public void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException;
	
	public void rencherir(int prix, IAcheteur acheteur) throws RemoteException;
	
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException;
	
}
