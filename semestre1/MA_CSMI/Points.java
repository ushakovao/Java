package MA_CSMI;

import java.awt.Graphics;

import java.util.Random;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class Points extends JFrame {
	private int taille = 300;
	private int nbPoints = 5000;

	public Points() {

		this.setSize(taille, taille);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new Panneau());
		this.setVisible(true);
	}

	public class Panneau extends JPanel {

		public void paintComponent(Graphics g) {
			Random rand = new Random();


			int x = 0;
			int y = 0;
			int w = this.getWidth();
			int h = this.getHeight();
			for (int i = 0; i < MyPoissRand.nextPoisson(w*h*0.01); i++) {
				x = rand.nextInt(this.getWidth());
				y = rand.nextInt(this.getHeight());
				System.out.println("taille: " + x + "*" + y);
				g.fillRect(x, y, 1, 1);
			}
		}
	}

	public static void main(String[] args) {

		new Points();

	}


}
