package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import implementation.serveur.ObjetEnVente;

public interface IServeurVente extends Remote{

	/**
	 * inscrit un acheteur au serveur. L'acheteur est en attente tant qu'une enchere est en cours
	 * @throws RemoteException
	 */
	public void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException;
	
	/**
	 * propose une enchere au serveur
	 * @throws RemoteException
	 */
	public void rencherir(int prix, IAcheteur acheteur) throws RemoteException;
	
	/**
	 * previens le serveur que le client ne souhaite pas rencherir ce tour
	 * @throws RemoteException
	 */
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException;
	
	public void ajouterEnchere(ObjetEnVente obj) throws RemoteException;
	
	public void lanceurEnchere() throws RemoteException;
}
