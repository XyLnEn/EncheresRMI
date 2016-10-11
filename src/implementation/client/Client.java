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

import ihm.IHMInscription;
import implementation.serveur.ObjetEnVente;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static int portConnexion = 8811;
	private final static String nomServeur = "//localhost:" + portConnexion + "/serveur";
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

	private String nom;
	private String id;
	private int prixObjEnEnchere;
	private ObjetEnVente obj;
	private String nomMaxDonnateur;
	private EtatClient state;
	private IServeurVente serv;
	
	

	public Client(String nom, String id, int prix, ObjetEnVente obj,
			String nomMaxDonnateur) {
		super();
		this.nom = nom;
		this.id = id;
		this.prixObjEnEnchere = prix;
		this.obj = obj;
		this.nomMaxDonnateur = nomMaxDonnateur;
		this.setState(EtatClient.ATTENTE);
		this.serv = null;
		LOGGER.setLevel(Level.INFO);
	}
	
	public Client(String nom) {
		super();
		this.nom = nom;
		this.id = "1";
		this.prixObjEnEnchere = 0;
		this.obj = null;
		this.nomMaxDonnateur = null;
		this.setState(EtatClient.ATTENTE);
		LOGGER.setLevel(Level.INFO);
	}

	public void envoyerPrix(){
		Scanner sc = new Scanner(System.in);
		LOGGER.info("nouveau prix : ");
		int newprix = sc.nextInt();
		this.envoiRencherir(newprix, this.serv);
	}

	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException {
		this.setState(EtatClient.ENCHERE);
		obj = Objet;
		this.prixObjEnEnchere = prix;
		LOGGER.info("objet:" + obj.getNom());
		LOGGER.info("descr:" + obj.getDescription());
		LOGGER.info("prix:" + this.getPrix());
		this.envoyerPrix();
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
		LOGGER.info("Nouveau prix : " + prix);
		LOGGER.info("envoye par " + pseudo);
		this.envoyerPrix();
	}
	
	
	public static IServeurVente bindingClient(String adresse) {
		IServeurVente serveurVente = null;
		try {
			Registry registry = LocateRegistry.getRegistry(portConnexion);
			serveurVente = (IServeurVente)registry.lookup(adresse);
			
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("connexion etablie");
		//passe en etat enchere
		return serveurVente;
		
	}
	
//*******************Methodes qui entrainent des changements d'etat coté serveur******************//
	public void envoiInscription(String pseudo) {
		try {
			this.getServ().inscriptionAcheteur(pseudo, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setState(EtatClient.ENCHERE);
	}
	
	public void envoiRencherir(int prix, IServeurVente serveurVente) {
		this.setState(EtatClient.ATTENTE);
		try {
			this.getServ().rencherir(prix, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
//		IHMClient guiclient = new IHMClient();
		IHMInscription inscrit = new IHMInscription();
		// Alicia ? qui a quoi
		IAcheteur cli = new Client(inscrit.getTexte());
		((Client)cli).setServ(bindingClient(nomServeur));
		((Client)cli).envoiInscription(inscrit.getTexte());
	}

	public String getNom() {
		return nom;
	}

	public String getId() {
		return id;
	}

	public int getPrix() {
		return prixObjEnEnchere;
	}

	public ObjetEnVente getObj() {
		return obj;
	}

	public String getNomMaxDonnateur() {
		return nomMaxDonnateur;
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
	
	
}
