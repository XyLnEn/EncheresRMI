package implementation.client;

import java.util.logging.Level;
import java.util.logging.Logger;



import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ihm.IHMClient;
import ihm.IHMInscription;
import implementation.serveur.ObjetEnVente;
import interfaces.IAcheteur;
import interfaces.IServeurVente;

public class Client implements IAcheteur, Serializable {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);//permet gestion des affichages consoles

	private String nom;
	private String id;
	private int prixObjEnEnchere;
	private ObjetEnVente obj;
	private String nomMaxDonnateur;
	private EtatClient state;
	
	

	public Client(String nom, String id, int prix, ObjetEnVente obj,
			String nomMaxDonnateur) {
		super();
		this.nom = nom;
		this.id = id;
		this.prixObjEnEnchere = prix;
		this.obj = obj;
		this.nomMaxDonnateur = nomMaxDonnateur;
		LOGGER.setLevel(Level.INFO);
	}

	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) throws RemoteException {
		//State passe a enchere
		obj = Objet;
		this.prixObjEnEnchere = prix;
		LOGGER.info("objet:" + obj.getNom());
		LOGGER.info("descr:" + obj.getDescription());
		LOGGER.info("prix:" + this.getPrix());
		
	}

	@Override
	public void objetVendu() throws RemoteException {
		//on passe en etat termine
		LOGGER.info("objet vendu a " + nomMaxDonnateur);
		
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) throws RemoteException {
		//on repasse en etat enchere
		this.prixObjEnEnchere = prix;
		nomMaxDonnateur = pseudo;
		LOGGER.info("Nouveau prix : " + prix);
		LOGGER.info("envoye par " + pseudo);

	}
	
	public static IServeurVente bindingClient(String adresse, IAcheteur cli) {
		IServeurVente serveurVente = null;
		try {
			Registry registry = LocateRegistry.getRegistry(8810);
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
	

	public static void main(String[] args) {
		IHMInscription inscrit = new IHMInscription();
		IAcheteur cli = new Client(inscrit.getTexte(),"1",-1,null,null);
		IServeurVente serveurVente = bindingClient("//localhost:8810/serveur",cli);
		
		try {
			serveurVente.inscriptionAcheteur(inscrit.getTexte(), cli);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
}
