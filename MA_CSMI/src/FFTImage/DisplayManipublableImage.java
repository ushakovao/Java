package FFTImage;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.JFrame;


public class DisplayManipublableImage extends JFrame{ 

	
	
	private ManipulableImage manipulableImage;
	private Traceur traceur;
	private Image image;
	private Dimension dim;


	// Inset values for the container object
	int inTop;
	int inLeft;
	

	public DisplayManipublableImage(ManipulableImage aManipulableImage){
		
		manipulableImage=aManipulableImage;
		dim=manipulableImage.dim;
		image=manipulableImage.toImage();



		//coordinates of the point on the top-left (just under the gray bar)      
		inTop = this.getInsets().top;
		inLeft = this.getInsets().left;    

		// rules window properties 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(inLeft+dim.width,inTop+dim.height);
		this.setBackground(Color.yellow);
		this.setVisible(true);

	}





	public DisplayManipublableImage(Traceur aTraceur){

		traceur=aTraceur;
		dim=traceur.dim;
		image=traceur.toImage();



		//coordinates of the point on the top-left (just under the gray bar)
		inTop = this.getInsets().top;
		inLeft = this.getInsets().left;

		// rules window properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(inLeft+dim.width,inTop+dim.height);
		this.setBackground(Color.yellow);
		this.setVisible(true);

	}


	public void paint(Graphics g){
		g.drawImage(image,inLeft,inTop,this);	
	}







}//fin de la classe
