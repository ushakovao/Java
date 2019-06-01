package Project_FFT;

import java.awt.*;

/**
 * Created by ushakova on 10/05/16.
 */
public class FFT1D {

//we don't need this method to compare algos, but this method is useful in bigger projects, where we don't know lenght of FFT /*

    public static Complex[] fft_by_algo(Complex[] complex) {

        int n = complex.length;
        if ((n & (n - 1)) == 0)
            CooleyTukey(complex);
        else
            Bluestein(complex);
        return complex;
    }


/////////////////Cooley-Tukey Recursive with Complex/////////////////////////

    public static Complex[] CooleyTukey_rec(Complex[] x, boolean inverse) {
        int n = x.length;

        if (Integer.highestOneBit(n) != n) {
            throw new RuntimeException("N is not a power of 2");
        }


        Complex[] result = new Complex[n];
        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];

        if (n == 1) {
            result[0] = x[0];
        } else {

            even = CooleyTukey_rec(evenValues(x), inverse);
            odd = CooleyTukey_rec(oddValues(x), inverse);

            for (int k = 0; k < n / 2; k++) {
                Complex factor =
                        inverse ? Project_FFT.Complex.fromPolar(1., 2. * Math.PI * k / n) : Project_FFT.Complex.fromPolar(1., -2.
                                * Math.PI * k / n);
                result[k] = even[k].add(odd[k].mul(factor));
                result[k + n / 2] = even[k].sub(odd[k].mul(factor));
            }
        }
        return result;
    }

    private static Complex[] evenValues(Complex[] x) {
        Complex[] result = new Complex[x.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = x[2 * i];
        }
        return result;
    }

    private static Complex[] oddValues(Complex[] x) {
        Complex[] result = new Complex[x.length / 2];
        for (int i = 0; i < result.length; i++) {
            result[i] = x[2 * i + 1];
        }
        return result;
    }


    public static Complex[] convolve(Complex[] a, Complex[] b) {
        Complex[] result = new Complex[a.length];
        for (int i = 0; i < result.length; i++) {

            result[i] = a[i].mul(b[i]);
        }
        return result;

    }


/////////////////Cooley-Tukey optimized with Complex/////////////////////////

    public static Complex[] CooleyTukey(Complex[] complex) {

        int N = complex.length;
        if (Integer.highestOneBit(N) != N) {
            throw new RuntimeException("N is not a power of 2");
        }

        // bit reversal permutation
        int shift = 1 + Integer.numberOfLeadingZeros(N);
        for (int k = 0; k < N; k++) {
            int j = Integer.reverse(k) >>> shift;
            if (j > k) {
                Complex temp = complex[j];
                complex[j] = complex[k];
                complex[k] = temp;
            }
        }


        for (int L = 2; L <= N; L = L + L) {
            for (int k = 0; k < L / 2; k++) {
                double kth = -2 * k * Math.PI / L;
                Complex w = new Complex(Math.cos(kth), Math.sin(kth));
                for (int j = 0; j < N / L; j++) {
                    Complex tao = w.mul(complex[j * L + k + L / 2]);
                    complex[j * L + k + L / 2] = complex[j * L + k].sub(tao);
                    complex[j * L + k] = complex[j * L + k].add(tao);
                }
            }
        }
        return complex;
    }


/////////////////Cooley-Tukey optimized with real/imag/////////////////////////


    public static void CooleyTukey(double[] real, double[] imag) {

        int n = real.length;
        if (Integer.highestOneBit(n) != n) {
            throw new IllegalArgumentException("Length is not a power of 2");
        }
        int log_factor = (int) Math.floor(Math.log(n) / Math.log(2));

        double[] cosTable = new double[n / 2];
        double[] sinTable = new double[n / 2];
        for (int i = 0; i < n / 2; i++) {
            cosTable[i] = Math.cos(2 * Math.PI * i / n);
            sinTable[i] = Math.sin(2 * Math.PI * i / n);
        }

        for (int i = 0; i < n; i++) {
            int j = Integer.reverse(i) >>> (32 - log_factor);
            if (j > i) {
                double temp = real[i];
                real[i] = real[j];
                real[j] = temp;
                temp = imag[i];
                imag[i] = imag[j];
                imag[j] = temp;
            }
        }


        for (int size = 2; size <= n; size *= 2) {
            int halfsize = size / 2;
            int tablestep = n / size;
            for (int i = 0; i < n; i += size) {
                for (int j = i, k = 0; j < i + halfsize; j++, k += tablestep) {
                    double tpre = real[j + halfsize] * cosTable[k] + imag[j + halfsize] * sinTable[k];
                    double tpim = -real[j + halfsize] * sinTable[k] + imag[j + halfsize] * cosTable[k];
                    real[j + halfsize] = real[j] - tpre;
                    imag[j + halfsize] = imag[j] - tpim;
                    real[j] += tpre;
                    imag[j] += tpim;
                }
            }
            if (size == n)
                break;
        }
    }

/////////////////Bluestein with return with Complex/////////////////////////

/*
    public static Complex[] Bluestein_return(Complex[] c) {
        c = c.clone();
        double[] real = new double[c.length];
        double[] imag = new double[c.length];

        for (int i = 0; i < c.length; i++) {
            real[i] = c[i].getRe();
            imag[i] = c[i].getIm();

        }

        if (real.length != imag.length)
            throw new IllegalArgumentException("Mismatched lengths");
        int n = real.length;
        if (n >= 0x20000000)
            throw new IllegalArgumentException("Array too large");
        int m = Integer.highestOneBit(n * 2 + 1) << 1;


        double[] cosTable = new double[n];
        double[] sinTable = new double[n];
        for (int i = 0; i < n; i++) {
            int j = (int) ((long) i * i % (n * 2));  // This is more accurate than j = i * i
            cosTable[i] = Math.cos(Math.PI * j / n);
            sinTable[i] = Math.sin(Math.PI * j / n);
        }

        // Temporary vectors and preprocessing
        double[] areal = new double[m];
        double[] aimag = new double[m];
        for (int i = 0; i < n; i++) {
            areal[i] = real[i] * cosTable[i] + imag[i] * sinTable[i];
            aimag[i] = -real[i] * sinTable[i] + imag[i] * cosTable[i];
        }
        double[] breal = new double[m];
        double[] bimag = new double[m];
        breal[0] = cosTable[0];
        bimag[0] = sinTable[0];
        for (int i = 1; i < n; i++) {
            breal[i] = breal[m - i] = cosTable[i];
            bimag[i] = bimag[m - i] = sinTable[i];
        }

        // Convolution
        double[] creal = new double[m];
        double[] cimag = new double[m];

        convolve(areal, aimag, breal, bimag, creal, cimag);

        // Postprocessing
        for (int i = 0; i < n; i++) {
            real[i] = creal[i] * cosTable[i] + cimag[i] * sinTable[i];
            imag[i] = -creal[i] * sinTable[i] + cimag[i] * cosTable[i];
            c[i].setRe(real[i]);
            c[i].setIm(imag[i]);

        }
        return c;
    }

*/



/////////////////Bluestein with Complex with no return/////////////////////////



    public static void Bluestein(Complex[] c) {
    c = c.clone();
    double[] real = new double [c.length];
    double[] imag = new double [c.length];

    for (int i = 0; i < c.length; i++) {
        real[i] = c[i].getRe();
        imag[i]=c[i].getIm();

    }

    if (real.length != imag.length)
        throw new IllegalArgumentException("Mismatched lengths");
    int n = real.length;
    if (n >= 0x20000000)
        throw new IllegalArgumentException("Array too large");
    int m = Integer.highestOneBit(n * 2 + 1) << 1;


    double[] cosTable = new double[n];
    double[] sinTable = new double[n];
    for (int i = 0; i < n; i++) {
        int j = (int)((long)i * i % (n * 2));  // This is more accurate than j = i * i
        cosTable[i] = Math.cos(Math.PI * j / n);
        sinTable[i] = Math.sin(Math.PI * j / n);
    }

    // Temporary vectors and preprocessing
    double[] areal = new double[m];
    double[] aimag = new double[m];
    for (int i = 0; i < n; i++) {
        areal[i] =  real[i] * cosTable[i] + imag[i] * sinTable[i];
        aimag[i] = -real[i] * sinTable[i] + imag[i] * cosTable[i];
    }
    double[] breal = new double[m];
    double[] bimag = new double[m];
    breal[0] = cosTable[0];
    bimag[0] = sinTable[0];
    for (int i = 1; i < n; i++) {
        breal[i] = breal[m - i] = cosTable[i];
        bimag[i] = bimag[m - i] = sinTable[i];
    }

    // Convolution
    double[] creal = new double[m];
    double[] cimag = new double[m];
    convolve(areal, aimag, breal, bimag, creal, cimag);

    // Postprocessing
    for (int i = 0; i < n; i++) {
        real[i] =  creal[i] * cosTable[i] + cimag[i] * sinTable[i];
        imag[i] = -creal[i] * sinTable[i] + cimag[i] * cosTable[i];

    }



}


/////////////////Bluestein with real/imag/////////////////////////


    public static void Bluestein(double[] real, double[] imag  ) {

        if (real.length != imag.length)
            throw new IllegalArgumentException("Mismatched lengths");
        int n = real.length;
        if (n >= 0x20000000)
            throw new IllegalArgumentException("Array too large");
        int m = Integer.highestOneBit(n * 2 + 1) << 1;


        double[] cosTable = new double[n];
        double[] sinTable = new double[n];
        for (int i = 0; i < n; i++) {
            int j = (int)((long)i * i % (n * 2));  // This is more accurate than j = i * i
            cosTable[i] = Math.cos(Math.PI * j / n);
            sinTable[i] = Math.sin(Math.PI * j / n);
        }

        // Temporary vectors and preprocessing
        double[] areal = new double[m];
        double[] aimag = new double[m];
        for (int i = 0; i < n; i++) {
            areal[i] =  real[i] * cosTable[i] + imag[i] * sinTable[i];
            aimag[i] = -real[i] * sinTable[i] + imag[i] * cosTable[i];
        }
        double[] breal = new double[m];
        double[] bimag = new double[m];
        breal[0] = cosTable[0];
        bimag[0] = sinTable[0];
        for (int i = 1; i < n; i++) {
            breal[i] = breal[m - i] = cosTable[i];
            bimag[i] = bimag[m - i] = sinTable[i];
        }

        // Convolution
        double[] creal = new double[m];
        double[] cimag = new double[m];
        convolve(areal, aimag, breal, bimag, creal, cimag);

        // Postprocessing
        for (int i = 0; i < n; i++) {
            real[i] =  creal[i] * cosTable[i] + cimag[i] * sinTable[i];
            imag[i] = -creal[i] * sinTable[i] + cimag[i] *

 cosTable[i];
        }

    }



/////////////////Convolutions/////////////////////////


    //Convolution
    public static void convolve(double[] x, double[] y, double[] out) {
        if (x.length != y.length || x.length != out.length)
            throw new IllegalArgumentException("Mismatched lengths");
        int n = x.length;
        convolve(x, new double[n], y, new double[n], out, new double[n]);
    }

    //Cyclic Convolution
    public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }

        int N = x.length;

        // compute FFT of each sequence
        Complex[] a = CooleyTukey(x);
        Complex[] b = CooleyTukey(y);

        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].mul(b[i]);
        }

        // compute inverse FFT
        return inverseTransformB(c);
    }





    public static void convolve(double[] xreal, double[] ximag, double[] yreal, double[] yimag, double[] outreal, double[] outimag) {
        if (xreal.length != ximag.length || xreal.length != yreal.length || yreal.length != yimag.length || xreal.length != outreal.length || outreal.length != outimag.length)
            throw new IllegalArgumentException("Mismatched lengths");

        int n = xreal.length;
        xreal = xreal.clone();
        ximag = ximag.clone();
        yreal = yreal.clone();
        yimag = yimag.clone();

        CooleyTukey(xreal, ximag);
        CooleyTukey(yreal, yimag);

        for (int i = 0; i < n; i++) {
        double temp = xreal[i] * yreal[i] - ximag[i] * yimag[i];
        ximag[i] = ximag[i] * yreal[i] + xreal[i] * yimag[i];
        xreal[i] = temp;
        }

        inverseTransformB(xreal, ximag);

        for (int i = 0; i < n; i++) {
        outreal[i] = xreal[i] / n;
        outimag[i] = ximag[i] / n;
        }
        }



/////////////////Inverse/////////////////////////

    public static Complex[] inverseTransformCT(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].getConjugate();
        }

        // compute forward FFT
        y = CooleyTukey(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].getConjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;
    }





    public static void inverseTransformCT(double[] real, double[] imag) {

        int n = real.length;
        double[] coim =new double[n];
        for (int i = 0; i < n; i++) {
            coim[i] = - imag[i];
        }
        CooleyTukey(real, coim);
        for (int i = 0; i < n; i++) {
            imag[i] = - coim[i];
        }


    }


    public static Complex[] inverseTransformB(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].getConjugate();
        }

        // compute forward FFT
        y = CooleyTukey(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].getConjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

    }


    public static void inverseTransformB(double[] real, double[] imag) {

        int n = real.length;
        double[] coim = new double[n];
        for (int i = 0; i < n; i++) {
            coim[i] = -imag[i];
        }
        CooleyTukey(real, coim);
        for (int i = 0; i < n; i++) {
            imag[i] = -coim[i];
        }
    }




//////////////////////////////////////////////////////////


    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }



    public static void main(String[] args) {

       /* WavJava w = new WavJava();
        new ImportWav(filePath, w );
        int n = 0;
        if(w.samples.length % 2 == 0) {
            n = w.samples.length - 1;
        }
        else {
            n = w.samples.length;
        }

        w.lastTimeDrawn = 0.5;

        Complex[] w_new = new Complex[ w.samples.length];
        for (int i=0; i< w.samples.length; i++) {
            w_new[i].setRe(w.samples[i]);
            w_new[i].setIm(0.);
        }


        Complex[] wav=CooleyTukey(w_new);
        show(wav, "wav");
*/



        int N = 256;
        Complex[] x = new Complex[N];

        for (int i = 0; i < N; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
        }
        long startTime_c = System.nanoTime();
        Complex[] c = CooleyTukey_rec(x,false);
        long endTime_c = System.nanoTime();


        long startTime_ct = System.nanoTime();
        Complex[] ct = CooleyTukey(x);
        long endTime_ct = System.nanoTime();


        long startTime_b = System.nanoTime();
        Bluestein(x);
        long endTime_b = System.nanoTime();






        long duration_ct = (endTime_ct - startTime_ct)/100000;
        long duration_b = (endTime_b - startTime_b)/100000;
        long duration_c = (endTime_c - startTime_c)/100000;


        System.out.println("N = "+N);
        System.out.println("duration de Cooley-Tukey = " + duration_c +" ms");
        System.out.println("duration de Cooley-Tukey avec bits = " + duration_ct+" ms");
        System.out.println("duration de Bluestein = " + duration_b+" ms");
    }
}
