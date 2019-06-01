package FFTImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.math.BigDecimal;

/**
 * Created by ushakova on 12/03/16.
 */
public class Traceur  {
    public int[][] R;
    public int[][] G;
    public int[][] B;
    public int[][] alpha;
    public Dimension dim;
    public  Conversion cv;
    public Fonction2D f;
    public int ak;
    public double[][] hsb_t;
    public float[] hsb;
    int [] listeDePixel;




    public  Traceur (Dimension adim )  {


        Toolkit tk = Toolkit.getDefaultToolkit();

        Image image =Toolkit.getDefaultToolkit().createImage("new_image");

       // f = f2d;
        dim = adim;
        R=new int[dim.height][dim.width];
        G=new int[dim.height][dim.width];
        B=new int[dim.height][dim.width];
        alpha=new int[dim.height][dim.width];


        listeDePixel = new int[dim.width * dim.height];
        try{
            PixelGrabber pgObj = new PixelGrabber(
                    image,0,0,dim.width,dim.height,
                    listeDePixel,0,dim.width);
            pgObj.grabPixels();
        }catch(InterruptedException e){System.out.println(e);}


     /*   for(int row = 0;row < dim.height;row++){
            int[] aRow = new int[dim.width];
            for(int col = 0; col < dim.width;col++){
                int element = row * dim.width + col;
                aRow[col] = listeDePixel[element];
            }

            for(int col = 0;col < dim.width;col++){
                alpha[row][col] = (aRow[col] >> 24)	& 0xFF;
                R[row][col] = (aRow[col] >> 16) & 0xFF;
                G[row][col] = (aRow[col] >> 8) & 0xFF;
                B[row][col] = (aRow[col])& 0xFF;
            }
        }*/


    }


    public Image toImage(){
        Conversion c = new Conversion();
        hsb_t = new double[dim.height][dim.width];
        hsb = new float[dim.height * dim.width];
        listeDePixel= new int[dim.width*dim.height];
        for (int i=0, k =0; i<dim.height; i++) {
            for (int j = 0; j < dim.width; j++) {
                int r = R[i][j];
                int g = G[i][j];
                int b = B[i][j];
                hsb = Color.RGBtoHSB(r, g, b, null);

                double y = new BigDecimal(String.valueOf(hsb)).doubleValue();
                int o = (int) Math.round(y);
                listeDePixel[k] = o;
                hsb_t[i][j] = y;

            }
        }

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.createImage(new MemoryImageSource(dim.width,dim.height,listeDePixel,0,dim.width));
        return img;
    }



    public void parab (){

        for (int i=0; i<dim.height; i++) {
            for (int j = 0; j < dim.width; j++) {
                R[i][j] = i * i + j * j;
                int r = R[i][j];
                G[i][j] = i * i + j * j;
                int g = G[i][j];
                B[i][j] = i * i + j * j;
                int b = B[i][j];

                float[] hsb = Color.RGBtoHSB(r, g, b, null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

            }
        }
    }





 /*   public Image toImage(){

        int [] listeDePixel= new int[dim.width*dim.height];

        for(int row = 0,cnt = 0;row < dim.height;row++){
            for(int col = 0;col < dim.width;col++){
                listeDePixel[cnt] = ((alpha[row][col] << 24)& 0xFF000000)
                        | ((R[row][col] << 16) & 0x00FF0000)
                        | ((G[row][col] << 8)& 0x0000FF00)
                        | ((B[row][col])& 0x000000FF);
                cnt++;
            }

        }

        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.createImage(new MemoryImageSource(dim.width,dim.height,listeDePixel,0,dim.width));


        return(img);
    }*/


}
