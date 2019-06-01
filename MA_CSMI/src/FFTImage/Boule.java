package FFTImage;

import java.awt.Dimension;


public class Boule extends Fonction2D{
	
	public Boule(Dimension d){		
		super(d);
		for (int i=0;i<d.width;i++){
			for (int j=0;j<d.height;j++){
				super.z[i][j]=Math.sqrt(i*i+j*j);
			}
		}		
	}
}
