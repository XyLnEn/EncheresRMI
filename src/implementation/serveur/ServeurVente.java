package implementation.serveur;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Scanner;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente {

	private ListeInscrits participants;
	private ObjetEnVente objVente;
	private int prix;
	private ListeEncheres encheres;
	private boolean venteEnCours = false;
	
	
	protected ServeurVente() throws RemoteException {
		Scanner sc = new Scanner(System.in);
		participants = new ListeInscrits();
		String nom = this.NouvNomObjet(sc);
		String descr = this.NouvDescrObjet(sc);
		setObjVente(new ObjetEnVente(nom,descr));
		prix = this.NouvPrix(sc);
	}
	
	public String NouvDescrObjet(Scanner sc) {
		System.out.print("descr de l'objet : ");
		String descr = sc.nextLine();
		System.out.println("");
		return descr;
	}
	
	public String NouvNomObjet(Scanner sc) {
		System.out.print("nom de l'objet : ");
		String nom = sc.nextLine();
		System.out.println("");
		return nom;
	}
	
	public int NouvPrix(Scanner sc) {
		System.out.print("prix de l'objet : ");
		int prix = sc.nextInt();
		System.out.println("");
		return prix;
	}
	
	public void DebutVente() throws RemoteException {
		venteEnCours = true;
		encheres.getListeEnchere().clear();//pour eviter les encheres fantomes
		for (IAcheteur ach: participants.getInscrits().keySet()) {
			ach.nouvelleSoumission(objVente, prix);
		}
		
	}

	@Override
	public synchronized void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException {
		try {
			while(venteEnCours) {
				wait();
			}
			participants.add(acheteur, pseudo);
			notify();//pour declencher une chaine d'inscription
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fin inscription");
	}
	
	
	public void realiserRoundEnchere() {
		boolean enchereFinie = true;
		Enchere e = new Enchere(null, -1);
		for (Enchere current : encheres.getListeEnchere()) {
			if(current.getEnchere() != 0) {
				enchereFinie = false;
			}
			if (this.prix < current.getEnchere()) {
				e.setEnchere(current.getEnchere());
				e.setEnchereur(current.getEnchereur());
				this.prix = current.getEnchere();
			}
		}
		if(enchereFinie) {
			//cas enchere finie
			for (Map.Entry<IAcheteur, String> entry : participants.getInscrits().entrySet()) {//iteration sur chaque inscrits
				try {
					entry.getKey().objetVendu();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			venteEnCours = false;
			notifyAll();
			
		}
		else {
			//prevenir tout client du resultat du round
			for (Map.Entry<IAcheteur, String> entry : participants.getInscrits().entrySet()) {//iteration sur chaque inscrits
				try {
					entry.getKey().nouveauPrix(e.getEnchere(), participants.getPseudo(e.getEnchereur()));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	//possibilite d'un round ou encheres != 0 mais toutes en dessous du prix serv? possibilit� d'enchere ou prix <= prix courant client?
	@Override
	public synchronized void rencherir(int prix, IAcheteur acheteur) throws RemoteException {
		Enchere ench = new Enchere(acheteur, prix);
		encheres.add(ench);
		if (encheres.taille() >= participants.taille()) {
			realiserRoundEnchere();
		}
	}

	@Override
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	public static void bindingServeur(String adresse, IServeurVente serveur) {
		try {
			Registry registry = LocateRegistry.createRegistry(8810);
			registry.rebind(adresse, serveur);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.err.println("Server ready");
	}
	
	public static void main(String[] args) throws RemoteException {
		
		IServeurVente serveur = new ServeurVente();
		bindingServeur("//localhost:8810/serveur", serveur);
		
//			String url;
//			url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/serveur";
//			System.out.println("Enregistrement de l'objet avec l'url : " + url);
//			Naming.bind(url, serveur);
		
		
	}
	
	public ListeInscrits getParticipants() {
		return participants;
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
