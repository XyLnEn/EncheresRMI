package implementation.client;

import java.util.ArrayList;
import java.util.List;

import ihm.IHM;


public class ObserverClient {

	private List<IHM> ihms = new ArrayList<IHM>();

	public List<IHM> getIhms() {
		return ihms;
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
