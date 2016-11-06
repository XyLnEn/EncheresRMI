package ihm;

import implementation.client.Client;
import implementation.serveur.ObjetEnVente;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IHMNouvelObjetEnVente extends JFrame implements IHM, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Client client;
	private ObjetEnVente objet;
	private IHMPrincipal ihmPrincipal;
	
	private JPanel panelPrincipal;
	private JTextField labelNom;
	private JTextField labelDesc;
	private JTextField labelPrix;
	private JButton btnValidation;
	
	
	
	
//	private JTextField labelNom;
//	private JButton btnNom;
	
	public IHMNouvelObjetEnVente(Client c, IHMPrincipal p) {
		client = c;
		ihmPrincipal = p;
		construireIHM();
		this.setTitle("Soumission d'un nouvel objet en vente");
	    this.setSize(600, 400); 
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             	    	    
	}
	
	
	
	private void construireIHM() {
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(4, 1));
		
		labelNom = new JTextField("Saisir le nom de l'objet", 20);
		labelNom.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	labelNom.setText("");
            }
        });
		
		labelDesc = new JTextField("Saisir la description de l'objet", 20);
		labelDesc.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	labelDesc.setText("");
            }
        });
		
		labelPrix = new JTextField("Saisir le prix de l'objet", 20);
		labelPrix.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	labelPrix.setText("");
            }
        });
		
		btnValidation = new JButton("Valider");
		btnValidation.addActionListener(this);
		
		panelPrincipal.add(labelNom);
		panelPrincipal.add(labelDesc);
		panelPrincipal.add(labelPrix);
		panelPrincipal.add(btnValidation);
		
		this.setContentPane(panelPrincipal);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		objet = new ObjetEnVente(null,null,0,null);
		String texteNom = (labelNom.getText());
		objet.setNom(texteNom);
		String texteDesc = (labelDesc.getText());
		objet.setDescription(texteDesc);
		String textePrix = (labelPrix.getText());
		objet.setPrix(Integer.parseInt(textePrix));
		try {
			client.getServ().ajouterEnchere(objet);
			client.getServ().lanceurEnchere();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
	}



	@Override
	public void notifier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changerVisibilite(boolean b) {
		this.setVisible(b);
		
	}

}
