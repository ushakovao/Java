
package MA_CSMI;
import java.awt.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Cette classe produira un dessin dans une fenêtre. 
 * Elle étend donc JFrame 
 */
public class Carres extends JFrame {

	private int tailleFenetre = 500;
	private int tailleCarre = 3;


	/*
	 * Dans le constructeur nous réglons les paramètres de la fenêtre.
	 */
	public Carres() {

		// la taille de départ, en pixel
		this.setSize(tailleFenetre, tailleFenetre);
		// le coin de notre fenêtre est dans le coin en haut à gauche de notre
		// écran
		this.setLocation(0, 0);
		// quand on ferme la fenêtre, on ferme aussi l'application
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// le contenu de la fenêtre sera un panneau que nous créerons plus loin
		this.setContentPane(new Panneau());
		// la fenêtre est visible immédiatement, il faut le préciser
		this.setVisible(true);
	}

	/*
	 * Un panneau peut contenir un dessin, mais peut aussi contenir d'autres
	 * panneaux
	 */
	public class Panneau extends JPanel {

		/**
		 * La méthode qui paint. Remarquons que cette méthode se lance
		 * automatiquement : à quel(s) moment(s) ?
		 */

		public void paintComponent(Graphics g) {

			Random rand = new Random();
			int x = 0;
			int y = 0;
			int w = this.getWidth();
			int h = this.getHeight();
			for (int i = 0; i < w*h*0.01; i++) {
				x = rand.nextInt(this.getWidth());
				y = rand.nextInt(this.getHeight());
				System.out.println("taille: " + x + "*" + y );
				g.fillRect(x, y, tailleCarre, tailleCarre);
				g.setColor(Color.blue);
			}
		}
	}

	public static void main(String[] args) {

		new Carres();

	}



}
