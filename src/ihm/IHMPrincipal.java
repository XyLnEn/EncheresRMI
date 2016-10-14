package ihm;

import implementation.client.Client;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class IHMPrincipal implements IHM {

	private Map<String, IHM> ihms;
	private Client client = null;
	
	public Map<String, IHM> getIhms() {
		return ihms;
	}
	
	public IHMPrincipal() throws RemoteException {
		ihms = new HashMap<String, IHM>();
		client = new Client();
		ihms.put("inscription",new IHMInscription(client, this));
		ihms.put("client", new IHMClient(client, this));
	}
	
	
	@Override
	public void notifier() {
	
		
	}

	@Override
	public void changerVisibilite(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void signal(String key) {
		switch (key) {
		case "inscription" :
			ihms.get("inscription").changerVisibilite(false);
			ihms.get("client").changerVisibilite(true);
			break;

		default:
			break;
		}	
	}

	public static void main(String[] args) {
//		new IHMClient();
	}
}
