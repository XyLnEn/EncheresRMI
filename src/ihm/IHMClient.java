package ihm;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class IHMClient extends JFrame {

	public IHMClient() {
		this.setTitle("Application enchère en ligne");
	    this.setSize(700, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);	
	    
	    JPanel panelPrincipal = new JPanel();
	    this.setContentPane(panelPrincipal);
	    
	    Border borderlabel = LineBorder.createBlackLineBorder();
	    JLabel nomObject = new JLabel("Une grosse patoune");
	    nomObject.setBorder(borderlabel);
	    JLabel prixObject = new JLabel("20 €");
	    prixObject.setBorder(borderlabel);
	    JLabel infoVendu = new JLabel("Vendu à ... ");
	    infoVendu.setBorder(borderlabel);
	    
	    JButton btnRencherir = new JButton("Renchérir");
	    
	    JSpinner increment = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
	    
	    JPanel panelNom = new JPanel();
	    panelNom.add(nomObject);
	
	    
	    Image icon = null;
		try {
			icon = ImageIO.read(new File("images/grossePatoune.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    JLabel labelImage = new JLabel();
	    labelImage.setIcon(new ImageIcon(icon.getScaledInstance(300, 300, Image.SCALE_DEFAULT)));
	    
	    JPanel panelImage = new JPanel();
	    panelImage.add(labelImage);
	    
	    
	    JPanel panelDetail = new JPanel();
	    panelDetail.setLayout(new GridLayout(2,2, 10, 10));
	    panelDetail.add(prixObject);
	    panelDetail.add(btnRencherir);
	    panelDetail.add(increment);
	    panelDetail.add(infoVendu);
	    
	    
	    JPanel panelObjet = new JPanel();
	   // panelObjet.setLayout(new BoxLayout(panelObjet, BoxLayout.LINE_AXIS));
	    panelObjet.add(panelDetail);
	    
	    
	    panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.PAGE_AXIS));
	    panelPrincipal.add(panelNom);
	    panelPrincipal.add(panelImage);
	    panelPrincipal.add(panelObjet);
	    this.setVisible(true);	
	}
	
}
