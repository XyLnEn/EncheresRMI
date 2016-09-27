package implementation;

import java.rmi.RemoteException;
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
	public boolean inscriptionAcheteur(String pseudo, IAcheteur acheteur)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rencherir(int prix, IAcheteur acheteur)
			throws RemoteException {
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
	
}
