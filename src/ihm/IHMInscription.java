package ihm;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IHMInscription extends JFrame {

	private static final long serialVersionUID = 1L;

	
	private JPanel panelPrincipal;
	private JTextField labelPseudo;
	private JButton btnPseudo;
	
	public IHMInscription() {
		demandePseudo();
		this.setTitle("Demande d'inscription");
	    this.setSize(400, 80); 
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
	}

	private void demandePseudo() {
		panelPrincipal = new JPanel();
		
		labelPseudo = new JTextField("Saisir votre pseudo", 20);
		btnPseudo = new JButton("Confirmer");
		
		panelPrincipal.add(labelPseudo);
		panelPrincipal.add(btnPseudo);
		
		this.setContentPane(panelPrincipal);
	}
}
