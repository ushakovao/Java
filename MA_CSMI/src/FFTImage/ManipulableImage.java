package FFTImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class ManipulableImage {

	public int[][] R;
	public int[][] G;
	public int[][] B;
	public int[][] alpha;
	public Dimension dim;


	// For double indexed array, we use the matrix convention :
	//first index is row index, second index is column index


	/**
	 *
	 */
	public ManipulableImage(String nameOfTheFile) {

		int[] listeDePixel;


		// TO LOAD THE IMAGE
		// phase 1 : just give it a name
		Image image = Toolkit.getDefaultToolkit().getImage(nameOfTheFile);

		// we define a  Component-type object (here a Frame-type object) which will get the image.
		// But this object will just help us to get the dimensions of the loaded image : the Frame will not be displayed
		Frame frame = new Frame();
		MediaTracker tracker = new MediaTracker(frame);
		tracker.addImage(image, 1);
		try {
			if (!tracker.waitForID(1, 10000)) {
				// if we do not manage to load the image : ex : if the name is wrong
				System.out.println("Load error.");
				System.exit(1);
			}//end if
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		//Now the image is load. We create an empty array with the same dimension than the image
		dim = new Dimension(image.getWidth(frame), image.getHeight(frame));
		R = new int[dim.height][dim.width];
		G = new int[dim.height][dim.width];
		B = new int[dim.height][dim.width];
		alpha = new int[dim.height][dim.width];


		//With the method  grabPixels of the class PixelGrabber, we convert this our binary data into a list of pixel
		listeDePixel = new int[dim.width * dim.height];
		try {
			PixelGrabber pgObj = new PixelGrabber(
					image, 0, 0, dim.width, dim.height,
					listeDePixel, 0, dim.width);
			pgObj.grabPixels();
		} catch (InterruptedException e) {
			System.out.println(e);
		}


		// We convert the list of pixel into a ManipulableImage
		for (int row = 0; row < dim.height; row++) {
			//Extract a row of pixel data into a
			// temporary array of integers
			int[] aRow = new int[dim.width];
			for (int col = 0; col < dim.width; col++) {
				int element = row * dim.width + col;
				aRow[col] = listeDePixel[element];
			}

			for (int col = 0; col < dim.width; col++) {
				alpha[row][col] = (aRow[col] >> 24) & 0xFF;
				R[row][col] = (aRow[col] >> 16) & 0xFF;
				G[row][col] = (aRow[col] >> 8) & 0xFF;
				B[row][col] = (aRow[col]) & 0xFF;
			}
		}

	}


	public ManipulableImage(int height, int width, Fonction2D f) {
		dim = new Dimension(height, width);
		R = new int[height][width];
		G = new int[height][width];
		B = new int[height][width];
		alpha = new int[height][width];

		int[] couleur = {0, 0, 0};
		double min = f.min;
		double max = f.max;

//In order to respect the max and min values for waves, we do:
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				double y = ((f.z[row][col] - min) / (max - min)) * (780 - 380) + 380;
				couleur = Conversion.waveLengthToRGB(y);
				alpha[row][col] = 255;
				R[row][col] = couleur[0];
				G[row][col] = couleur[1];
				B[row][col] = couleur[2];
			}
		}
	}


	public Image toImage() {

		int[] listeDePixel = new int[dim.width * dim.height];

		for (int row = 0, cnt = 0; row < dim.height; row++) {
			for (int col = 0; col < dim.width; col++) {
				listeDePixel[cnt] = ((alpha[row][col] << 24) & 0xFF000000)
						| ((R[row][col] << 16) & 0x00FF0000)
						| ((G[row][col] << 8) & 0x0000FF00)
						| ((B[row][col]) & 0x000000FF);
				cnt++;
			}

		}

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.createImage(new MemoryImageSource(dim.width, dim.height, listeDePixel, 0, dim.width));


		return (img);
	}


	public void bleachDiagonal() {
		int i = 0;
		while (i < dim.width - 2 && i < dim.height - 2) {
			alpha[i][i] = (byte) 0xff;
			R[i][i] = (byte) 0xff;
			G[i][i] = (byte) 0xff;
			B[i][i] = (byte) 0xff;

			alpha[i][i + 1] = (byte) 0xff;
			R[i][i + 1] = (byte) 0xff;
			G[i][i + 1] = (byte) 0xff;
			B[i][i + 1] = (byte) 0xff;

			alpha[i][i + 2] = (byte) 0xff;
			R[i][i + 2] = (byte) 0xff;
			G[i][i + 2] = (byte) 0xff;
			B[i][i + 2] = (byte) 0xff;

			i++;
		}
	}


	public void changeColors() {
		for (int i = 0; i < dim.height; i++) {
			for (int j = 0; j < dim.width; j++) {
				R[i][j] = (byte) 0xff;
				//G[i][j] = (byte)0xff;
				//B[i][j] = (byte)0xff;
				alpha[i][j] = 80;
			}
		}
	}


	public double[][][] conversionRGBVersTSV() {
		double[][][] TSV = new double[3][dim.height][dim.width];
		for (int i = 0; i < dim.height; i++) {
			for (int j = 0; j < dim.width; j++) {
				double max = (double) Math.max(R[i][j], Math.max(G[i][j], B[i][j]));
				double min = (double) Math.min(R[i][j], Math.min(G[i][j], B[i][j]));
				if (max == min) {

					TSV[0][i][j] = 0;
				} else if (max == R[i][j]) {

					TSV[0][i][j] = u.mod((60 * (G[i][j] - B[i][j]) / (max - min)), 360);
				} else if (max == G[i][j]) {
					TSV[0][i][j] = u.mod((60 * (B[i][j] - R[i][j]) / (max - min) + 120), 360);
				} else {
					TSV[0][i][j] = u.mod((60 * (R[i][j] - G[i][j]) / (max - min) + 240), 360);
				}
				if (max == 0) {
					TSV[1][i][j] = 0;
				} else {
					TSV[1][i][j] = 1 - min / max;
				}
				TSV[2][i][j] = max;

			}
		}
		return TSV;

	}


	public void sombre() {

		System.out.print(dim);
		System.out.println(R[50][100]);
		double c = 0.00004, d = 0.00004;
		for (int i = 0; i < dim.height; i++) {
			for (int j = 0; j < dim.width; j++) {
				R[i][j] *= Math.exp(-((c * (i * i)) + (d * (j * j))));
				G[i][j] *= Math.exp(-((c * (i * i)) + (d * (j * j))));
				B[i][j] *= Math.exp(-(((c * (i * i)) + (d * (j * j)))));

			}
		}


	}



	public void convolution() {
		double[][] kernel;
		kernel = new double[3][3];
		System.out.print(dim.height+"   "+dim.width);

		double[][] r;
		r = new double[dim.height][dim.width];
		double[][] g;
		g = new double[dim.height][dim.width];
		double[][] b;
		b = new double[dim.height][dim.width];

		for (int i = 1; i < dim.height; i++) {
			for (int j = 1; j < dim.width; j++) {


				if (i - 1 >= 0) {
					r[i][j] += 0.25 * R[i - 1][j];
					g[i][j] += 0.25 * G[i - 1][j];
					b[i][j] += 0.25 * B[i - 1][j];
				}
				if (j + 1 < dim.width) {
					r[i][j] += 0.25 * R[i][j + 1];
					g[i][j] += 0.25 * G[i][j + 1];
					b[i][j] += 0.25 * B[i][j + 1];
				}
				if (i + 1 < dim.height) {
					r[i][j] += 0.25 * R[i + 1][j];
					g[i][j] += 0.25 * G[i + 1][j];
					b[i][j] += 0.25 * B[i + 1][j];

				}
				if (j - 1 >= 0) {
					r[i][j] += 0.25 * R[i][j - 1];
					g[i][j] += 0.25 * G[i][j - 1];
					b[i][j] += 0.25 * B[i][j - 1];

				}

				R[i][j] = (int) r[i][j];
				G[i][j] = (int) g[i][j];
				B[i][j] = (int) b[i][j];
			}



/*

				if (i == 0 || i == dim.height) {
					R[i][j] = 0;
					G[i][j] = 0;
					B[i][j] = 0;

				} else if (j == 0 || j == dim.width) {

					R[i][j] = 0;
					G[i][j] = 0;
					B[i][j] = 0;

				} else {
					R[i][j] = (R[i][j - 1] + R[i][j + 1] + R[i - 1][j] + R[i + 1][j]) / 4;
					G[i][j] = (G[i][j - 1] + G[i][j + 1] + G[i - 1][j] + G[i + 1][j]) / 4;
					B[i][j] = (B[i][j - 1] + B[i][j + 1] + B[i - 1][j] + B[i + 1][j]) / 4;
				}*/


			}
		}


	}





	
//fin de la classe
