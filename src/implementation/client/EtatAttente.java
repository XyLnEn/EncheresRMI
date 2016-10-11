/**
 * 
 */
package implementation.client;

import implementation.serveur.ObjetEnVente;

/**
 * @author lenny
 *
 */
public class EtatAttente extends EtatClient {

	@Override
	public void nouvelleSoumission(ObjetEnVente Objet, int prix) {
		// TODO Auto-generated method stub
		super.nouvelleSoumission(Objet, prix);
	}

	@Override
	public void objetVendu() {
		// TODO Auto-generated method stub
		super.objetVendu();
	}

	@Override
	public void nouveauPrix(int prix, String pseudo) {
		// TODO Auto-generated method stub
		super.nouveauPrix(prix, pseudo);
	}

	
}
