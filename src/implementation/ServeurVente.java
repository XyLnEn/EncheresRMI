package implementation;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente {

	private ListeInscrits participants;
	private ObjetEnVente objVente;
	
	
	protected ServeurVente() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException {
		
		return false;
	}

	@Override
	public boolean rencherir(int prix, IAcheteur acheteur) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tempsEcoule(IAcheteur acheteur) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public ListeInscrits getParticipants() {
		return participants;
	}
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(8800);
			ServeurVente serveur = new ServeurVente();
			Naming.bind("//mamachine:8800/serveur", serveur);
		} catch (Exception e) {
			System.out.println("erreur sur serveur");
		}
		
		
	}
}
