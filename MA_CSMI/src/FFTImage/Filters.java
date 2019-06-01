package FFTImage;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

/**
 * Created by ushakova on 15/03/16.
 */
public class Filters   {


    String pathfrom = "assets/images/Lenna.png";
    String pathto = "assets/images/out/Lenna_out.png";
    String type = "png";


    public void blurred() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            Kernel kernel = new Kernel(3, 3, new float[]{1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f});
            BufferedImageOp op = new ConvolveOp(kernel);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_blurrred.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void moreblurred() {
        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++)
            matrix[i] = 1.0f / 400.0f;
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            BufferedImageOp op = new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_moreblurrred.png"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void relief() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            Kernel kernel = new Kernel(3, 3, new float[]{-2, 0, 0, 0, 1, 0, 0, 0, 2});
            BufferedImageOp op = new ConvolveOp(kernel);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_embossed.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void sharp() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            Kernel kernel = new Kernel(3, 3, new float[]{-1, -1, -1, -1, 9, -1, -1,
                    -1, -1});
            BufferedImageOp op = new ConvolveOp(kernel);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_sharp.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public void bright() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            float scaleFactor = 1.3f;
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_bright.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }





    public void flip() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            RescaleOp op = new RescaleOp(.9f, 0, null);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_flip.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




    public void spoiled() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            RescaleOp op = new RescaleOp(.9f, 0, null);
            bufferedImage = op.filter(bufferedImage, null);
            ImageIO.write(bufferedImage, type, new File("assets/images/out/laurel_spoiled.gif"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public double  meanValue(BufferedImage bufferedImage) throws  Exception {

            Raster raster = bufferedImage.getRaster();
            double sum = 0.0;
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                for (int x = 0; x < bufferedImage.getWidth(); ++x) {
                    sum += raster.getSample(x, y, 0);
                }

            }
        System.out.print(sum / (bufferedImage.getWidth() * bufferedImage.getHeight()));
        return sum / (bufferedImage.getWidth() * bufferedImage.getHeight());
    }


    public void sepia() throws Exception {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathfrom));
            RescaleOp op = new RescaleOp(.9f, 0, null);
           // bufferedImage = op.filter(bufferedImage, null);

            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int p = bufferedImage.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    //calculate tr, tg, tb
                    int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                    int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                    int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                    //check condition
                    if (tr > 255) {
                        r = 255;
                    } else {
                        r = tr;
                    }

                    if (tg > 255) {
                        g = 255;
                    } else {
                        g = tg;
                    }

                    if (tb > 255) {
                        b = 255;
                    } else {
                        b = tb;
                    }

                    //set new RGB value
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferedImage.setRGB(x, y, p);
                }
            }
            ImageIO.write(bufferedImage, type, new File("assets/images/out/Lenna_sepia.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }




    public  void medianfilter() throws  Exception {

        Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        File output=new File("assets/images/out/bruit.png");
        BufferedImage bufferedImage = ImageIO.read(new File("assets/images/bruit.png"));
        ImageIO.write(bufferedImage, type, new File("assets/images/out/bruit.png"));


        for(int i=1;i<bufferedImage.getWidth()-1;i++)
            for(int j=1;j<bufferedImage.getHeight()-1;j++)
            {
                pixel[0]=new Color(bufferedImage.getRGB(i-1,j-1));
                pixel[1]=new Color(bufferedImage.getRGB(i-1,j));
                pixel[2]=new Color(bufferedImage.getRGB(i-1,j+1));
                pixel[3]=new Color(bufferedImage.getRGB(i,j+1));
                pixel[4]=new Color(bufferedImage.getRGB(i+1,j+1));
                pixel[5]=new Color(bufferedImage.getRGB(i+1,j));
                pixel[6]=new Color(bufferedImage.getRGB(i+1,j-1));
                pixel[7]=new Color(bufferedImage.getRGB(i,j-1));
                pixel[8]=new Color(bufferedImage.getRGB(i,j));
                for(int k=0;k<9;k++){
                    R[k]=pixel[k].getRed();
                    B[k]=pixel[k].getBlue();
                    G[k]=pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                bufferedImage.setRGB(i,j,new Color(R[4],B[4],G[4]).getRGB());
            }
        ImageIO.write(bufferedImage,"png",output);
    }



}


