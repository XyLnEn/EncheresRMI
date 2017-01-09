package implementation.serveur;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.Map.Entry;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import implementation.client.Client;
import implementation.client.EtatClient;
import implementation.client.Client.ChronoFinEnchere;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class ServeurVente extends UnicastRemoteObject implements IServeurVente, Serializable {

	public class ChronoVerifConnexion extends TimerTask
	{
		
		@Override
		public void run() {
			
			Iterator<Map.Entry<IAcheteur, String>> iter = participants.entrySet().iterator();
			while (iter.hasNext()) {
			    Map.Entry<IAcheteur, String> entry = iter.next();
			    try {
					entry.getKey().checkConnexion();
				} catch (RemoteException e) {
					LOGGER.info("detection d'un client qui est partit, suppression...");
					iter.remove();
				}
			}
			
		}
	}
	
	/****************Pour le Timer*******************/
	private Timer timer = new Timer();
	
	private final int portConnexion = 1099;
	private String nomServeur = "implementation.serveur.ServeurVente";
	private final static int NB_MIN_ACHETEURS = 1;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

	private Map<IAcheteur, String> participants;
	private ListeObjetEnVente listeObjsVentes;
	private ObjetEnVente objVente;
	private int prixActuel;
	private ListeEncheres encheres;
	private int nbParticipants;
	private boolean venteEnCours = false;
	
	
	protected ServeurVente() throws RemoteException {

		this.participants = new Hashtable<IAcheteur, String>();
		this.listeObjsVentes = new ListeObjetEnVente();
		this.objVente = null;
		this.prixActuel = 0;
		this.encheres = new ListeEncheres();
		LOGGER.setLevel(Level.INFO);
		this.timer.scheduleAtFixedRate(new ChronoVerifConnexion(),0, 30000);
	}
	
/******************************Debut des methodes pour gerer l'inscription des Clients******************************/

	/*(non-Javadoc)
	 * @see interfaces.IServeurVente#inscriptionAcheteur(java.lang.String, interfaces.IAcheteur)
	 */
	@Override
	public synchronized void inscriptionAcheteur(String pseudo, IAcheteur acheteur) throws RemoteException { //L'inscription marchera 
		LOGGER.info("Demande d'inscription de " + pseudo + " bien reçue");
		try {
			while(venteEnCours) {
				wait();
			}
			LOGGER.info("traitement...");
			participants.put(acheteur, pseudo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("fin inscription");
	}
	
/******************************Fin des methodes pour gerer l'inscription des Clients******************************/

/******************************Debut des methodes pour realiser l'enchere******************************/
	
	/** 
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
	
	public synchronized void signalFinRound() {
		venteEnCours = false;
		notifyAll();
	}
	
	/**
	 * methode qui previens les clients que l'enchere est finie
	 */
	public void finEnchere() {
		listeObjsVentes.FinirVente();
		for (Map.Entry<IAcheteur, String> entry : participants.entrySet()) {//iteration sur chaque inscrits
			try {
				entry.getKey().objetVendu();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		signalFinRound();
		if((listeObjsVentes.getObjetsVentes().size() > 0) && (participants.size() >= NB_MIN_ACHETEURS)) {
			try {
				DebutVente();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			LOGGER.info("attente plus de personnes ou nouvel objet");
		}
	}
	
	/**
	 * methode qui previent les clients que le round est terminé. On envoie le nom du gagnant actuel avec la somme proposée
	 */
	public void FinRoundEnchere(Enchere gagnante) {
		for (Map.Entry<IAcheteur, String> entry : participants.entrySet()) {//iteration sur chaque inscrits
			try {
				entry.getKey().nouveauPrix(gagnante.getEnchere(), participants.get(gagnante.getEnchereur()));
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * methode qui recupere la meilleure enchere et decide si la vente est finie ou si il faut effectuer un nouveau tour.
	 */
	public void realiserRoundEnchere() {
		Enchere gagnante = getBestEnchere();
		boolean enchereFinie = true;
		if(gagnante.getEnchereur() != null) {
			enchereFinie = (gagnante.getEnchere() == 0);//pour detecter si personne n'a fait d'encheres
			encheres.getListeEnchere().clear();//reset des encheres dans tout les cas
		}
		if(!enchereFinie) {
			nbParticipants = participants.size();
			FinRoundEnchere(gagnante);
		} else {
			finEnchere();
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.IServeurVente#rencherir(int, interfaces.IAcheteur)
	 */
	@Override
	public synchronized void rencherir(int prix, IAcheteur acheteur) throws RemoteException {
		Enchere ench = new Enchere(acheteur, prix);
		encheres.add(ench);
	}

	/* (non-Javadoc)
	 * @see interfaces.IServeurVente#tempsEcoule(interfaces.IAcheteur)
	 */
	@Override
	public void tempsEcoule(IAcheteur acheteur) throws RemoteException {
		nbParticipants--;
		if (nbParticipants == 0) {
			realiserRoundEnchere();
		}

	}
	
/******************************Fin des methodes pour realiser l'enchere******************************/

/******************************Debut des methodes pour la gestion du Serveur******************************/
	
	public synchronized void ajouterEnchere(ObjetEnVente obj) {//devrait etre synchronized
		listeObjsVentes.ajouterObjet(obj);
		LOGGER.info("objet bien reçu");
	}
	
	public void lanceurEnchere(){
		if(participants.size() >= NB_MIN_ACHETEURS) {
			try {
				DebutVente();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**methode qui annonce le nouvel objet a vendre
	 * @throws RemoteException
	 */
	public void DebutVente() throws RemoteException {
		nbParticipants = participants.size();
		venteEnCours = true;
		encheres.getListeEnchere().clear();//pour eviter les encheres fantomes
		objVente = listeObjsVentes.getVenteActuelle();
		prixActuel = objVente.getPrix();
		if(objVente != null) {
			for (IAcheteur ach: participants.keySet()) {
				ach.nouvelleSoumission(objVente, prixActuel);
			}
		}
		
	}
	
	/**
	 *prepare le serveur pour la connexion des clients
	*/
	public void bindingServeur(String adresse, int portConnexion) {
		try {
			LOGGER.info("adresse du serveur: " + InetAddress.getLocalHost().getHostAddress());
			Registry registry = LocateRegistry.createRegistry(portConnexion);
			registry.rebind(adresse, this);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Server ready");
	}
	
	
/******************************Fin des methodes pour la gestion du Serveur******************************/

/******************************Debut du main du Serveur******************************/

	public static void main(String[] args) throws RemoteException {
		
		ServeurVente serveur = new ServeurVente();
		serveur.bindingServeur(serveur.nomServeur, serveur.portConnexion);
		
	}
	
/******************************Fin du main du Serveur******************************/

/******************************Getteurs/setteurs******************************/
	
	public Map<IAcheteur, String> getParticipants() {
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
