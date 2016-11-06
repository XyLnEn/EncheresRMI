package ihm;

import implementation.client.Client;
import interfaces.IAcheteur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IHMInscription extends JFrame implements IHM, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final int portConnexion = 8811;
	private String nomServeur;

	private Client client;
	private IHMPrincipal ihmPrincipal;
	
	private JPanel panelPrincipal;
	private JTextField labelPseudo;
	private JTextField labelAdresse;
	private JButton btnPseudo;
	
	public IHMInscription(Client c, IHMPrincipal p) {
		client = c;
		ihmPrincipal = p;
		demandePseudo();
		this.setTitle("Demande d'inscription");
	    this.setSize(400, 100); 
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	}

	private void demandePseudo() {
		panelPrincipal = new JPanel();
		
		labelPseudo = new JTextField("Saisir votre pseudo", 20);
		labelPseudo.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	labelPseudo.setText("");
            }
        });
		
		labelAdresse = new JTextField("Saisir l'adresse du serveur", 20);
		labelAdresse.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	labelAdresse.setText("");
            }
        });
		
		btnPseudo = new JButton("Confirmer");
		btnPseudo.addActionListener(this);
		
		panelPrincipal.add(labelPseudo);
		panelPrincipal.add(labelAdresse);
		panelPrincipal.add(btnPseudo);
		
		this.setContentPane(panelPrincipal);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String texte = (labelPseudo.getText());
		client.setNom(texte);
		this.nomServeur = "rmi://" + labelAdresse.getText() + "/serveur";
		client.setServ(client.bindingClient(nomServeur,portConnexion));
		this.travaillerTermine();
	}

	public void changerVisibilite(boolean b) {
		this.setVisible(b);
	}
	
	@Override
	public void notifier() {
		
	}
	
	public void travaillerTermine() {
		ihmPrincipal.signal("inscription");
	}
	
}
