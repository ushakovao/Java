package FFTImage;

import java.awt.Dimension;

public class Fonction2D {

	public Dimension dim;
	public double [][] z;
	public double max=-2147483647.;
	public double min=2147483647.;

	public Fonction2D(Dimension d){
		dim=d;
		z=new double[dim.height][dim.width];
	}


	public void fois(double k) {
		for (int i = 0; i < dim.height; i++) {
			for (int j = 0; j < dim.width; j++) {
				z[i][j] = z[i][j] * k;
			}
		}
	}






	public void centre(){
		for (int i=0;i<dim.height;i++){
			for (int j=0;j<dim.width;j++){

				z[i][j]=(i-0.5*dim.height)*(i-0.5*dim.height)+(j-0.5*dim.width)*(j-0.5*dim.width);

				if(z[i][j]>max){
					max=z[i][j];}
				if(z[i][j]<min){
					min=z[i][j];}
			}
		}
	}

	public void topleft(){
		for (int i=0;i<dim.height;i++){
			for (int j=0;j<dim.width;j++){

				z[i][j]=(i)*(i)+(j)*(j);

				if(z[i][j]>max){
					max=z[i][j];}
				if(z[i][j]<min){
					min=z[i][j];}
			}
		}
	}
	public static double fonction_CarreHautGauche(double x, double y) { return x*x+y*y;}

	public void carreHautGauche(double x0, double xn, double y0, double yn){
		double incrementX = (xn - x0) / dim.height;
		double incrementY = (yn - y0) / dim.width;
		for (int i=0;i<dim.height;i++){
			for (int j=0;j<dim.width;j++){

				z[i][j]=fonction_CarreHautGauche(x0 + i*incrementX, y0 + j*incrementY);

				if(z[i][j]>max){max=z[i][j];}
				if(z[i][j]<min){min=z[i][j];}
			}
		}
	}

}


