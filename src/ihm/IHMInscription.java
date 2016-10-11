package ihm;

import implementation.client.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IHMInscription extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	
	private JPanel panelPrincipal;
	private JTextField labelPseudo;
	private JButton btnPseudo;
	private String texte;
	
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
		btnPseudo.addActionListener(this);
		
		panelPrincipal.add(labelPseudo);
		panelPrincipal.add(btnPseudo);
		
		this.setContentPane(panelPrincipal);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setTexte(labelPseudo.getText());
		new IHMClient();
	}
	

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}
}
