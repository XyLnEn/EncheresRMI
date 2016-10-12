package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import implementation.serveur.ObjetEnVente;

public interface IServeurVente extends Remote {

	/*** @author lenny
	 * inscrit un acheteur au serveur. L'acheteur est en attente tant qu'une enchere est en cours
	 * 
	 * @param pseudo
	 * @param acheteur
	 * @throws RemoteException
	 */
	public void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException;
	
	/** @author lenny
	 * propose une enchere au serveur
	 * @param prix
	 * @param acheteur
	 * @throws RemoteException
	 */
	public void rencherir(int prix, IAcheteur acheteur) throws RemoteException;
	
	/**@author lenny
	 * previens le serveur que le client ne souhaite pas rencherir ce tour
	 * @param acheteur
	 * @throws RemoteException
	 */
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException;
	
	public void ajouterEnchere(ObjetEnVente obj) throws RemoteException;
}
