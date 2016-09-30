package implementation;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
	public void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException {
	
	}

	@Override
	public void rencherir(int prix, IAcheteur acheteur) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException {
		// TODO Auto-generated method stub

	}

	public ListeInscrits getParticipants() {
		return participants;
	}
	
	public static void main(String[] args) throws RemoteException {
		try {
			ServeurVente serveur = new ServeurVente();
			Registry registry = LocateRegistry.createRegistry(8810);
		
//			String url;
//			url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/serveur";
//			System.out.println("Enregistrement de l'objet avec l'url : " + url);
//			Naming.bind(url, serveur);
			

			System.out.println("Serveur lancé");
			Naming.bind("//localhost:8810/serveur", serveur);
			
			} catch (Exception e) {
				System.out.println("fail serveur");
			} 
//			catch (MalformedURLException e) {
//				System.out.println("MalformedURLException");
//			} catch (UnknownHostException e) {
//				System.out.println("UnknownHostException");
//			} catch (AlreadyBoundException e) {
//				System.out.println("AlreadyBoundException");
//			}
		
		
	}
}
