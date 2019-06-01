package FFTImage;

import java.awt.Dimension;


public class Fonction2DPlus {

	
public double [][] z;
public double [] xGraduations;
public double [] yGraduations;


public Fonction2DPlus(double[][] z, double[] xGraduations,
		double[] yGradudations) {
	super();
	this.z = z;
	this.xGraduations = xGraduations;
	this.yGraduations = yGradudations;
	
	if (z.length!=this.yGraduations.length) throw new RuntimeException("entrées non compatibles");
	
	for (int i=0;i<z.length;i++){
		if (z[i].length!=this.xGraduations.length) throw new RuntimeException("entrées non compatibles");
	}
		
}







}
