package implementation.serveur;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente {

	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

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
		LOGGER.setLevel(Level.INFO);
	}
	
	/** @author lenny
	 * methode qui recupere la descr d'un objet 
	 * @param sc un scanner
	 * @return la descr
	 */
	public String NouvDescrObjet(Scanner sc) {
		LOGGER.info("descr de l'objet : ");
		String descr = sc.nextLine();
		System.out.println("");
		return descr;
	}
	
	/** @author lenny
	 * methode qui recupere le nom d'un objet 
	 * @param sc le scanner
	 * @return le nom
	 */
	public String NouvNomObjet(Scanner sc) {
		LOGGER.info("nom de l'objet : ");
		String nom = sc.nextLine();
		System.out.println("");
		return nom;
	}
	
	/** @author lenny
	 * methode qui recupere le prix d'un objet
	 * @param sc le scanner
	 * @return le prix
	 */
	public int NouvPrix(Scanner sc) {
		LOGGER.info("prix de l'objet : ");
		int prix = sc.nextInt();
		System.out.println("");
		return prix;
	}
	
	/**@author lenny
	 * methode qui annonce le nouvel objet a vendre
	 * @throws RemoteException
	 */
	public void DebutVente() throws RemoteException {
		venteEnCours = true;
		encheres.getListeEnchere().clear();//pour eviter les encheres fantomes
		for (IAcheteur ach: participants.getInscrits().keySet()) {
			ach.nouvelleSoumission(objVente, prix);
		}
		
	}

	/*(non-Javadoc)
	 * @see interfaces.IServeurVente#inscriptionAcheteur(java.lang.String, interfaces.IAcheteur)
	 */
	@Override
	public synchronized void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException {
		LOGGER.info("Demande d'inscription de " + pseudo + " bien reçue");
		try {
			while(venteEnCours) {
				wait();
			}
			LOGGER.info("traitement...");
			participants.add(acheteur, pseudo);
			notifyAll();//pour declencher une chaine d'inscription
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("fin inscription");
	}
	
	/** @author lenny
	 * methode qui determine quelle enchere est la gagnante
	 * @return gagnante l'enchere la plus eleve 
	 */
	public Enchere getBestEnchere() {
		Enchere gagnante = new Enchere(null, 0);
		for (Enchere current : encheres.getListeEnchere()) {
			if (this.prix < current.getEnchere()) {
				gagnante.setEnchere(current.getEnchere());
				gagnante.setEnchereur(current.getEnchereur());
				this.prix = current.getEnchere();
			}
		}
		return gagnante;
	}
	
	/**@author lenny
	 * methode qui previens les clients que l'enchere est finie
	 */
	public void finEnchere() {
		for (Map.Entry<IAcheteur, String> entry : participants.getInscrits().entrySet()) {//iteration sur chaque inscrits
			try {
				entry.getKey().objetVendu();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		venteEnCours = false;
//		notifyAll();
	}
	
	/**@author lenny
	 * methode qui previens les clients que le round est terminé. On envoie le nom du gagnant actuel avec la somme proposée
	 * @param gagnante
	 */
	public void FinRoundEnchere(Enchere gagnante) {
		for (Map.Entry<IAcheteur, String> entry : participants.getInscrits().entrySet()) {//iteration sur chaque inscrits
			try {
				entry.getKey().nouveauPrix(gagnante.getEnchere(), participants.getPseudo(gagnante.getEnchereur()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**@author lenny
	 * methode qui recupere la meilleure enchere et decide si la vente est finie ou si il faut effectuer un nouveau tour.
	 * 
	 */
	public void realiserRoundEnchere() {
		Enchere gagnante = getBestEnchere();
		boolean enchereFinie = (gagnante.getEnchere() == 0);//pour detecter si personne n'a fait d'encheres
		if(enchereFinie) {
			finEnchere();
		}
		else {
			FinRoundEnchere(gagnante);
		}
	}

	//possibilite d'un round ou encheres != 0 mais toutes en dessous du prix serv? possibilit� d'enchere ou prix <= prix courant client?
	/* (non-Javadoc)
	 * @see interfaces.IServeurVente#rencherir(int, interfaces.IAcheteur)
	 */
	@Override
	public synchronized void rencherir(int prix, IAcheteur acheteur) throws RemoteException {
		Enchere ench = new Enchere(acheteur, prix);
		encheres.add(ench);
		if (encheres.taille() >= participants.taille()) {
			realiserRoundEnchere();
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.IServeurVente#tempsEcoule(interfaces.IAcheteur)
	 */
	@Override
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException {
		rencherir(0, acheteur);

	}
	
	/**@author lenny
	 * prepare le serveur pour la connexion des clients
	 * @param adresse
	 * @param serveur
	 */
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
		LOGGER.info("Server ready");
	}
	
	/**@author lenny
	 * attend qu'un nombre x de clients soit pret pour lancer l'enchere
	 * @param x
	 */
	public void attenteDeDebutEnchere(int x) {
		while(participants.taille() < x) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(IAcheteur ach : participants.getInscrits().keySet()) {
			try {
				ach.nouvelleSoumission(objVente, prix);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws RemoteException {
		
		IServeurVente serveur = new ServeurVente();
		bindingServeur("//localhost:8810/serveur", serveur);
		((ServeurVente)serveur).attenteDeDebutEnchere(3);
		
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
