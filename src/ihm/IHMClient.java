package ihm;

import implementation.client.Client;
import interfaces.IAcheteur;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class IHMClient extends JFrame implements IHM, ActionListener {
	
	private static final long serialVersionUID = 1L;

	private Client client;
	private IHMInscription ihmInscri;
	private IHMPrincipal ihmPrincipal;


	private JPanel panelPrincipal;
	private JPanel panelImage;
	private JPanel panelEnchere;
	private JPanel panelObjet;
	
//	private JLabel labelNom;
	private JTextArea labelDescr;
    private JLabel labelPrixObject;
    private JLabel labelInfoVendu;
    private JLabel labelImage;
    
    private JButton btnEncherir;
    
    private JTextField enchere;
    
    private Image icon = null;
    
    /**
     * 
     */
	public IHMClient(Client c, IHMPrincipal p) {
		client = c;
		ihmPrincipal = p;
		construireIHM();
		this.setTitle("La teamPatoune aux enchères");
	    this.setSize(600, 400); 
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             	    	    
	}
	
	/**
	 * 
	 */
	private void construireIHM() {
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(1, 2, 5, 5));
		
//		labelNom = new JLabel("Une grosse patoune");
		labelDescr = new JTextArea(); //"Description de l'objet : " + "blablablasssssssssssssssssssssssssssssssssssssssssss"
		labelDescr.setLineWrap(true);
		labelDescr.setWrapStyleWord(true);
		labelDescr.setOpaque(false);
		labelDescr.setEditable(false);
		
		labelPrixObject = new JLabel(); //"20 €"
		labelInfoVendu = new JLabel("Pas encore d'acheteurs");	   
	    btnEncherir = new JButton("Enchérir");
	    enchere = new JTextField("Saisir votre enchère", 20);	
	    
		try {
			icon = ImageIO.read(new File("images/grossePatoune.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		labelImage = new JLabel();
	    labelImage.setIcon(new ImageIcon(icon.getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
	    labelImage.setBorder(LineBorder.createGrayLineBorder());
	    	
	    panelImage = new JPanel();
	    panelImage.add(labelImage);
	    
	    panelEnchere = new JPanel();	    	   
	    panelEnchere.add(enchere);	 
	    panelEnchere.add(btnEncherir); 
	    
	    panelObjet = new JPanel();
	    panelObjet.setLayout(new GridLayout(4, 1));
	    
//	    panelObjet.add(labelNom);
	    panelObjet.add(labelDescr);
	    panelObjet.add(labelPrixObject);
	    panelObjet.add(panelEnchere);
	    panelObjet.add(labelInfoVendu);

	    
	    panelPrincipal.add(panelImage);
	    panelPrincipal.add(panelObjet);
	    
	    this.setContentPane(panelPrincipal);
	}
	
	public void changerVisibilite(boolean b) {
		this.setVisible(b);
	}
	
	public IAcheteur getClient() {
		return client;
	}

	public IHMInscription getIhmInscri() {
		return ihmInscri;
	}
	


	@Override
	public void notifier() {
		labelDescr.setText("Description de l'objet : " + client.getObj().getDescription());
		labelPrixObject.setText(client.getPrixObjEnEnchere() + "€");
		panelObjet.setBorder(BorderFactory.createTitledBorder(client.getObj().getNom()));
		
		if(client.getNomMaxDonnateur() != null) {
			labelInfoVendu.setText("Meilleur enchereur : " + client.getNomMaxDonnateur());
		}
	}
	
	public void travaillerTermine() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String texteEnchere = (enchere.getText());
		client.envoiRencherir(Integer.parseInt(texteEnchere));
	}
	
}
