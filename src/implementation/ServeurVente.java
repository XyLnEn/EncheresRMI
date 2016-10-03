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
import java.util.Scanner;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente {

	private ListeInscrits participants;
	private ObjetEnVente objVente;
	private int prix;
	private ListeEncheres encheres;
	
	
	protected ServeurVente() throws RemoteException {
		participants = new ListeInscrits();
		setObjVente(new ObjetEnVente(NouvNomObjet(), NouvDescrObjet()));
		prix = NouvPrix();
	}
	
	public String NouvDescrObjet() {
		Scanner sc = new Scanner(System.in);
		System.out.print("descr de l'objet : ");
		String descr = sc.next();
		sc.close();
		System.out.println("");
		return descr;
	}
	
	public String NouvNomObjet() {
		Scanner sc = new Scanner(System.in);
		System.out.print("nom de l'objet : ");
		String nom = sc.next();
		sc.close();
		System.out.println("");
		return nom;
	}
	
	public int NouvPrix() {
		Scanner sc = new Scanner(System.in);
		System.out.print("prix de l'objet : ");
		int prix = sc.nextInt();
		sc.close();
		System.out.println("");
		return prix;
	}

	@Override
	public synchronized void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException {
		participants.add(pseudo, acheteur);
	}

	//possibilité d'un round ou encheres != 0 mais toutes en dessous du prix serv? possibilité d'enchere ou prix <= prix courant client?
	@Override
	public synchronized void rencherir(int prix, IAcheteur acheteur) throws RemoteException {

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
			

			System.out.println("Serveur lancÃ©");
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

	public ListeEncheres getEncheres() {
		return encheres;
	}

	public void setEncheres(ListeEncheres encheres) {
		this.encheres = encheres;
	}

	public ObjetEnVente getObjVente() {
		return objVente;
	}

	public void setObjVente(ObjetEnVente objVente) {
		this.objVente = objVente;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
}
