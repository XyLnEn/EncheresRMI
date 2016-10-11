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
import implementation.serveur.ServeurVente;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static int portConnexion = 8811;
	private final static String PseudoServeur = "//localhost:" + portConnexion + "/serveur";
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

	private String pseudo;
	private int prixObjEnEnchere;
	private ObjetEnVente obj;
	private String PseudoMaxDonnateur;
	private EtatClient state;
	private IServeurVente serv;
	
	

	public Client(String Pseudo, String id, int prix, ObjetEnVente obj,
			String PseudoMaxDonnateur) {
		super();
		this.pseudo = Pseudo;
		this.prixObjEnEnchere = prix;
		this.obj = obj;
		this.PseudoMaxDonnateur = PseudoMaxDonnateur;
		this.setState(EtatClient.ATTENTE);
		this.serv = null;
		LOGGER.setLevel(Level.INFO);
	}
	
	public Client() {
		super();
		this.pseudo = null;
		this.prixObjEnEnchere = 0;
		this.obj = null;
		this.PseudoMaxDonnateur = null;
		this.setState(EtatClient.ATTENTE);
		LOGGER.setLevel(Level.INFO);
	}

	public void envoyerPrix(){
		Scanner sc = new Scanner(System.in);
		LOGGER.info("nouveau prix : ");
		int newprix = sc.nextInt();
		this.envoiRencherir(newprix);
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
		LOGGER.info("objet vendu a " + PseudoMaxDonnateur);
		
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) throws RemoteException {
		this.setState(EtatClient.ENCHERE);
		this.prixObjEnEnchere = prix;
		PseudoMaxDonnateur = pseudo;
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
		return serveurVente;
		
	}
	
//*******************Methodes qui entrainent des changements d'etat coté serveur******************//
	public void envoiInscription() {
		try {
			serv.inscriptionAcheteur(this.pseudo, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setState(EtatClient.ENCHERE);
	}
	
	public void envoiRencherir(int prix) {
		this.setState(EtatClient.ATTENTE);
		try {
			serv.rencherir(prix, this);//marche pas car mauvaise insertion client
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {

//		IAcheteur cli = new Client(inscrit.getTexte());
//		((Client)cli).setServ(bindingClient("//localhost:8810/serveur"));
//		((Client)cli).envoiInscription(inscrit.getTexte());

		IAcheteur cli = new Client();
		((Client)cli).setPseudo("toto");
		((Client)cli).setServ(bindingClient(PseudoServeur));
		((Client)cli).envoiInscription();
	}

	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public int getPrix() {
		return prixObjEnEnchere;
	}

	public ObjetEnVente getObj() {
		return obj;
	}

	public String getPseudoMaxDonnateur() {
		return PseudoMaxDonnateur;
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
