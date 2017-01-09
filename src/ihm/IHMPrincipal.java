package ihm;

import implementation.client.Client;
import implementation.client.ObserverClient;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class IHMPrincipal implements IHM {
	
	private Map<String, IHM> ihms;
	private Client client;
	
	public Map<String, IHM> getIhms() {
		return ihms;
	}
	
	public IHMPrincipal() throws RemoteException {
		ihms = new HashMap<String, IHM>();
		client = new Client();
		ihms.put("inscription",new IHMInscription(client, this));
		ihms.put("client", new IHMClient(client, this));
		ihms.put("nouvelObjet", new IHMNouvelObjetEnVente(client, this));
		ihms.get("inscription").changerVisibilite(true);
		ObserverClient obs = new ObserverClient();
		obs.setIhms(ihms.values());
		client.setObsClient(obs);
	}
	
	
	@Override
	public void notifier() {
	
		
	}

	@Override
	public void changerVisibilite(boolean b) {
	}

	public void signal(String key) {
		switch (key) {
		case "inscription" :
			System.out.println("client :" + client.getNom());
			ihms.get("inscription").changerVisibilite(false);
			ihms.get("client").changerVisibilite(true);
			ihms.get("nouvelObjet").changerVisibilite(true);
			client.envoiInscription(client.getNom());
			break;

		default:
			break;
		}	
	}

	public static void main(String[] args) throws RemoteException {
		new IHMPrincipal();
	}
}
