package implementation.serveur;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import implementation.client.Client;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente {

	private final static int portConnexion = 8811;
	private final static String nomServeur = "//localhost:" + portConnexion + "/serveur";
	private static final int MIN_CLIENTS_Debut = 1;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

	private ListeInscrits participants;
	private ListeObjetEnVente listeObjsVentes;
	private ObjetEnVente objVente;
	private int prixActuel;
	private ListeEncheres encheres;
	private boolean venteEnCours = false;
	
	
	protected ServeurVente() throws RemoteException {

		this.participants = new ListeInscrits();
		this.listeObjsVentes = new ListeObjetEnVente();
		this.objVente = listeObjsVentes.getObjet();
		this.prixActuel = this.objVente.getPrix();
		this.encheres = new ListeEncheres();
		LOGGER.setLevel(Level.INFO);
	}
	
//	/** @author lenny
//	 * methode qui recupere la descr d'un objet 
//	 * @param sc un scanner
//	 * @return la descr
//	 */
//	public String NouvDescrObjet(Scanner sc) {
//		LOGGER.info("descr de l'objet : ");
//		String descr = sc.nextLine();
//		System.out.println("");
//		return descr;
//	}
//	
//	/** @author lenny
//	 * methode qui recupere le nom d'un objet 
//	 * @param sc le scanner
//	 * @return le nom
//	 */
//	public String NouvNomObjet(Scanner sc) {
//		LOGGER.info("nom de l'objet : ");
//		String nom = sc.nextLine();
//		System.out.println("");
//		return nom;
//	}
//	
//	/** @author lenny
//	 * methode qui recupere le prix d'un objet
//	 * @param sc le scanner
//	 * @return le prix
//	 */
//	public int NouvPrix(Scanner sc) {
//		LOGGER.info("prix de l'objet : ");
//		int prix = sc.nextInt();
//		System.out.println("");
//		return prix;
//	}
	
/******************************Debut des methodes pour gerer l'inscription des Clients******************************/

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
			participants.inscrire(acheteur);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("fin inscription : " + participants.getInscrits().size() );
		if(participants.getInscrits().size() >= MIN_CLIENTS_Debut) {
			DebutVente();
		} else {
			System.out.println("pas encore");
		}
	}
	
/******************************Fin des methodes pour gerer l'inscription des Clients******************************/

/******************************Debut des methodes pour realiser l'enchere******************************/
	
	/** @author lenny
	 * methode qui determine quelle enchere est la gagnante
	 * @return gagnante l'enchere la plus eleve 
	 */
	public Enchere getBestEnchere() {
		Enchere gagnante = new Enchere(null, 0);
		for (Enchere current : encheres.getListeEnchere()) {
			if (this.prixActuel < current.getEnchere()) {
				gagnante.setEnchere(current.getEnchere());
				gagnante.setEnchereur(current.getEnchereur());
				this.prixActuel = current.getEnchere();
			}
		}
		return gagnante;
	}
	
	/**@author lenny
	 * methode qui previens les clients que l'enchere est finie
	 */
	public void finEnchere() {
		for (IAcheteur entry : participants.getInscrits()) {//iteration sur chaque inscrits
			try {
				entry.objetVendu();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		venteEnCours = false;
		notifyAll();
	}
	
	/**@author lenny
	 * methode qui previens les clients que le round est terminé. On envoie le nom du gagnant actuel avec la somme proposée
	 * @param gagnante
	 */
	public void FinRoundEnchere(Enchere gagnante) {
		for (IAcheteur entry : participants.getInscrits()) {//iteration sur chaque inscrits
			try {
				entry.nouveauPrix(gagnante.getEnchere(), ((Client)gagnante.getEnchereur()).getPseudo());
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
		if (encheres.taille() >= participants.getInscrits().size()) {
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
	
/******************************Fin des methodes pour realiser l'enchere******************************/

/******************************Debut des methodes pour la gestion du Serveur******************************/
	
	/**@author lenny
	 * methode qui annonce le nouvel objet a vendre
	 * @throws RemoteException
	 */
	public void DebutVente() throws RemoteException {
		venteEnCours = true;
		encheres.getListeEnchere().clear();//pour eviter les encheres fantomes
		//objVente = listeObjsVentes.getnextObjet();
		prixActuel = objVente.getPrix();
		if(objVente != null) {
			for (IAcheteur ach: participants.getInscrits()) {
				ach.nouvelleSoumission(objVente, prixActuel);
			}
		}
		
	}
	
	/**@author lenny
	 * prepare le serveur pour la connexion des clients
	 * @param adresse
	 * @param serveur
	 */
	public static void bindingServeur(String adresse, IServeurVente serveur) {
		try {
			Registry registry = LocateRegistry.createRegistry(portConnexion);
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
	
	
/******************************Fin des methodes pour la gestion du Serveur******************************/

/******************************Debut du main du Serveur******************************/

	public static void main(String[] args) throws RemoteException {
		
		IServeurVente serveur = new ServeurVente();
		bindingServeur(nomServeur, serveur);
		
		
	}
	
/******************************Fin du main du Serveur******************************/

/******************************Getteurs/setteurs******************************/
	
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
		return prixActuel;
	}

	public void setPrix(int prix) {
		this.prixActuel = prix;
	}

}
