package Project_FFT;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by ushakova on 05/05/16.
 */
public class FFT2d {


    public Complex[][] input;

    public Complex[][] output;

    public static class ComplexRGB {
        public Complex getComplexNumber(int rgb) {
            int red = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue = (rgb) & 0xff;
            float brightness = (float) ((.2126 * red + .7152 * green + .0722 * blue) / 255); //ITU  grayScaleIntensity
            return new Complex(brightness, 0.);
        }

        public double getPhase(Complex c) {
            return c.getArgument();
        }

        public int getRGB(Complex c) {
            return Color.HSBtoRGB(0f, 0f, (float) getIntensity(c));
        }

        public double getIntensity(Complex c) {
            return c.getMagnitude();
        }
    }

    public static ComplexRGB complexRGB = new ComplexRGB();

    public FFT2d() {
    }




    public static BufferedImage pixelsToImage(int[][] pixels) throws IllegalArgumentException {

        if (pixels == null) {
            throw new IllegalArgumentException();
        }

        int width = pixels[0].length;
        int height = pixels.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < height; row++) {
            image.setRGB(0, row, width, 1, pixels[row], 0, width);
        }
        return image;
    }

    public static int[][] imageToPixels(BufferedImage image) throws IllegalArgumentException {
        if (image == null) {
            throw new IllegalArgumentException();
        }

        int w = image.getWidth();
        int h = image.getHeight();
        int[][] pixels = new int[h][w];
        for (int row = 0; row < h; row++) {
            image.getRGB(0, row, w, 1, pixels[row], 0, w);
        }
        return pixels;
    }


    public static Complex[][] build2DArray(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        Complex[][] result = new Complex[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = complexRGB.getComplexNumber(image.getRGB(i, j));
            }
        }
        return result;
    }


    public static  Complex[][] build2DArray(int[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Complex[][] result = new Complex[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = complexRGB.getComplexNumber(pixels[i][j]);
            }
        }
        return result;
    }



    public void setRow(Complex[][] matrix, int row, Complex[] data) {
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[i][row] = data[i];
        }
    }

    public static Complex[] getRow(Complex[][] matrix, int row) {
        Complex[] result = new Complex[matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[i][row];
        }
        return result;
    }


    public FFT2d(Complex[][] input) {
        this.input = input;
        compute(true);
    }


    public FFT2d(int[][] pixels) {
        input = build2DArray(pixels);

        compute(false);
    }


    public FFT2d(BufferedImage image) {
        input = build2DArray(image);

        compute(false);
    }

    public void setInput(Complex[][] input) {
        this.input = input;
    }


    public static void show(Complex[][] x) {

        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                System.out.println(x[i][j]);
            }
        }
        System.out.println();
    }



    public static Complex[][] convolve(Complex[][] a, Complex[][] b) {
        Complex[][] result = new Complex[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = a[i][j].mul(b[i][j]);
            }
        }
        return result;
    }







    public void setInput(BufferedImage image) {
        input = build2DArray(image);
    }


    private static int[][] getPixels(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int[][] pixels = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                pixels[i][j] = image.getRGB(i, j);
            }
        }
        return pixels;
    }


    public void compute(boolean inverse) {
        long startTime = System.nanoTime();
        int w = input.length;
        int h = input[0].length;
        Complex[][] intermediate = new Complex[w][h];

        output = new Complex[w][h];

        for (int i = 0; i < input.length; i++) {
          //  intermediate[i]=FFT1D.inverseTransformCT(input[i]);
            intermediate[i]=FFT1D.CooleyTukey_rec(input[i], inverse);
          //  intermediate[i]=FFT1D.inverseTransformB(input[i]);
        }
        for (int i = 0; i < intermediate[0].length; i++) {
         //   setRow(output, i, FFT1D.inverseTransformCT(getRow(intermediate, i)));
            setRow(output, i, FFT1D.CooleyTukey_rec(getRow(intermediate, i),inverse));
          //  setRow(output, i, FFT1D.inverseTransformB(getRow(intermediate, i)));

        }
        long endTime = System.nanoTime();
        long duration = (endTime-startTime)/100000;
        System.out.println("Computing time:    " +duration+" ms" );
    }



}
