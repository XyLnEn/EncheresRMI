package implementation.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ihm.IHM;


public class ObserverClient {

	private Collection<IHM> ihms = new ArrayList<IHM>();

	public Collection<IHM> getIhms() {
		return ihms;
	}
	
	
	public void setIhms(Collection<IHM> collection) {
		this.ihms = collection;
	}


	public void ajoutIhm(IHM e) {
		ihms.add(e);
	}
	
	
	
	public void notifier() {
		for (IHM jFrame : ihms) {
			jFrame.notifier();
		}
	}
}
