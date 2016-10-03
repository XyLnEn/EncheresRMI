package ihm;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class IHMClient extends JFrame {

	public IHMClient() {
		this.setTitle("Application enchère en ligne");
	    this.setSize(500, 400);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);	
	    
	    JPanel panel = new JPanel();
	    this.setContentPane(panel);
	    
	    Border borderlabel = LineBorder.createBlackLineBorder();
	    JLabel nomObject = new JLabel("Nom de l'objet");
	    JLabel prixObject = new JLabel("20 €");
	    JLabel infoVendu = new JLabel("Vendu à ... ");
	    nomObject.setBorder(borderlabel);
	    prixObject.setBorder(borderlabel);
	 
	    
	    JButton btnRencherir = new JButton("Renchérir");
	 
	    
	    GridLayout gridlayout = new GridLayout(0, 2);
	    gridlayout.setHgap(10);
	    gridlayout.setVgap(10);
	    panel.setLayout(gridlayout);
	    
	   
	    this.getContentPane().add(nomObject);
	    this.getContentPane().add(prixObject);
	    this.getContentPane().add(infoVendu);
	    this.getContentPane().add(btnRencherir);
	    
	    this.setVisible(true);	
	}
	
}
