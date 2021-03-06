package implementation.client;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import implementation.serveur.ObjetEnVente;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client extends UnicastRemoteObject implements IAcheteur, Serializable {
	
	public class ChronoFinEnchere extends TimerTask
	{
		private Client cli;
		public ChronoFinEnchere(Client c) {
			cli = c;
		}
		
		@Override
		public void run() {
			System.out.println("TROP TARD");
			cli.setState(EtatClient.ATTENTE);
			try {
				serv.tempsEcoule(cli);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

/****************Pour le Client******************/
	private String nom;
	private int prixObjEnEnchere;
	private ObjetEnVente obj;
	private String nomMaxDonnateur;
	private EtatClient state;
	
/****************Pour le Serveur*****************/
	private IServeurVente serv;
	
/****************Pour le Timer*******************/
	private Timer timer = new Timer();
	
/***************Pour l'Interface****************/
	private ObserverClient obsClient;
	
	public Client(String nom) throws RemoteException {
		this.nom = nom;
		this.prixObjEnEnchere = 0;
		this.obj = null;
		this.nomMaxDonnateur = null;
		this.setState(EtatClient.ATTENTE);
		LOGGER.setLevel(Level.INFO);
		
	}

	public Client() throws RemoteException {
		this.prixObjEnEnchere = 0;
		this.obj = null;
		this.nomMaxDonnateur = null;
		this.setState(EtatClient.ATTENTE);
		LOGGER.setLevel(Level.INFO);
	}
	
	public void cancelTimer() {
		timer.cancel();
		timer = new Timer();
	}
	
	public void lancerTimer() {
		timer.schedule(new ChronoFinEnchere(this), 30000);
	}
	
	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException {
		this.setState(EtatClient.ENCHERE);
		obj = Objet;
		this.prixObjEnEnchere = prix;
		obsClient.notifier();
	}

	@Override
	public void objetVendu() throws RemoteException {
		this.setState(EtatClient.TERMINE);
		LOGGER.info("objet vendu a " + nomMaxDonnateur);
		
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) throws RemoteException {
		this.setState(EtatClient.ENCHERE);
		this.prixObjEnEnchere = prix;
		nomMaxDonnateur = pseudo;
		obsClient.notifier();
	}
	
	
	public static IServeurVente bindingClient(String adresse,int portConnexion) {
		IServeurVente serveurVente = null;
		try {
			serveurVente = (IServeurVente)LocateRegistry.getRegistry(adresse, portConnexion).lookup("implementation.serveur.ServeurVente");
			
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		LOGGER.info("connexion etablie");
		return serveurVente;
		
	}
	
//*******************Methodes qui entrainent des changements d'etat coté serveur******************//
	public void envoiInscription(String pseudo) {
		try {
			serv.inscriptionAcheteur(pseudo, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setState(EtatClient.ENCHERE);
	}
	
	public void envoiRencherir(int prix) {
		if(this.getState() != EtatClient.ATTENTE) {
			this.setState(EtatClient.ATTENTE);
			try {
				serv.rencherir(prix, this);
				serv.tempsEcoule(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
//***********************************************getteur et setteurs***********************************************//	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPrixObjEnEnchere() {
		return prixObjEnEnchere;
	}

	public void setPrixObjEnEnchere(int prixObjEnEnchere) {
		this.prixObjEnEnchere = prixObjEnEnchere;
	}

	public ObjetEnVente getObj() {
		return obj;
	}

	public void setObj(ObjetEnVente obj) {
		this.obj = obj;
	}

	public String getNomMaxDonnateur() {
		return nomMaxDonnateur;
	}

	public void setNomMaxDonnateur(String nomMaxDonnateur) {
		this.nomMaxDonnateur = nomMaxDonnateur;
	}

	public EtatClient getState() {
		return state;
	}

	public void setState(EtatClient state) {
		this.state = state;
	}

	public IServeurVente getServ() {
		return serv;
	}

	public void setServ(IServeurVente serv) {
		this.serv = serv;
	}

	public ObserverClient getObsClient() {
		return obsClient;
	}

	public void setObsClient(ObserverClient obsClient) {
		this.obsClient = obsClient;
	}
	
	public void checkConnexion() {
	}
	
}
