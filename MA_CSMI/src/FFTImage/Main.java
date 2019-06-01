package FFTImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {


	public static void main(String[] args) throws Throwable {

		Filters f = new Filters();
		f.blurred();
		f.moreblurred();
		f.sharp();
		f.sepia();
		f.relief();



		BufferedImage bufferedImage = ImageIO.read(new File("assets/images/bruit.png"));
		f.meanValue(bufferedImage);
		f.medianfilter();







		ManipulableImage nb=new ManipulableImage("assets/images/bruit.png");
		new DisplayManipublableImage(nb);
		nb.convolution();
		new DisplayManipublableImage(nb);






	/*	Dimension d = new Dimension(600, 600);


		Fonction2D f = new Fonction2D(d);
		f.carreHautGauche();



		ManipulableImage test=new ManipulableImage(d, f);
		new DisplayManipublableImage(test);
*/
	/*	int longueur = 600;
		int largeur = 600;



		Dimension dim = new Dimension(longueur, largeur);

		Fonction2D f = new Fonction2D(dim);

		f.topleft();
		// Ici on choisit quelle fonction associer à f

		ManipulableImage test=new ManipulableImage(longueur, largeur, f);
		new DisplayManipublableImage(test);*/









	}

}
