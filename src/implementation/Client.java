package implementation;

import java.rmi.RemoteException;

import interfaces.IAcheteur;

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

}
