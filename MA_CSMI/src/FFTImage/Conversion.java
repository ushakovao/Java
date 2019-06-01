package FFTImage;

public class Conversion {

	//wavelength must range between 380 and 780
	
	static private double Gamma = 0.8;
	static private double IntensityMax = 255;

	/** Taken from Earl F. Glynn's web page:
	* <a href="http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm">Spectra Lab Report</a>
	* */
	public static int[] waveLengthToRGB(double Wavelength){
	    double factor;
	    double Red,Green,Blue;

	    if((Wavelength >= 380) && (Wavelength<440)){
	        Red = -(Wavelength-440) / (440 - 380);
	        Green = 0.0;
	        Blue = 1.0;
	    }else if((Wavelength >= 440) && (Wavelength<490)){
	        Red = 0.0;
	        Green = (Wavelength - 440) / (490 - 440);
	        Blue = 1.0;
	    }else if((Wavelength >= 490) && (Wavelength<510)){
	        Red = 0.0;
	        Green = 1.0;
	        Blue = -(Wavelength - 510) / (510 - 490);
	    }else if((Wavelength >= 510) && (Wavelength<580)){
	        Red = (Wavelength - 510) / (580 - 510);
	        Green = 1.0;
	        Blue = 0.0;
	    }else if((Wavelength >= 580) && (Wavelength<645)){
	        Red = 1.0;
	        Green = -(Wavelength - 645) / (645 - 580);
	        Blue = 0.0;
	    }else if((Wavelength >= 645) && (Wavelength<780)){
	        Red = 1.0;
	        Green = 0.0;
	        Blue = 0.0;
	    }else{
	        Red = 0.0;
	        Green = 0.0;
	        Blue = 0.0;
	    };

	    /*
	     * The factor variable is here to low the light intensity when we approach the extreme of the 
	     * visible spectrum.
	     */ 

	    if((Wavelength >= 380) && (Wavelength<=419)){
	        factor = 0.3 + 0.7*(Wavelength - 380) / (420 - 380);
	    }else if((Wavelength >= 420) && (Wavelength<=700)){
	        factor = 1.0;
	    }else if((Wavelength >= 701) && (Wavelength<=780)){
	        factor = 0.3 + 0.7*(780 - Wavelength) / (780 - 700);
	    }else{
	        factor = 0.0;
	    };
	    


	    int[] rgb = new int[3];

	    // these test exclude the case Red=0.0. 
	    //we do not want the usual convention : 0^0 = 1. 
	    rgb[0] = (Red==0.0) ? 0 : (int) Math.round(IntensityMax * Math.pow(Red * factor, Gamma));
	    rgb[1] = (Green==0.0) ? 0 : (int) Math.round(IntensityMax * Math.pow(Green * factor, Gamma));
	    rgb[2] = (Blue==0.0) ? 0 : (int) Math.round(IntensityMax * Math.pow(Blue * factor, Gamma));

// 	   here is a simpler, but less beautiful method  
//	    rgb[0]=(int) (Red*255);
//	    rgb[1]=(int) (Green*255);
//	    rgb[2]=(int) (Blue*255);
	    
	    return rgb;
	}
	

}
