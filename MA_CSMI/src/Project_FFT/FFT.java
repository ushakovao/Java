package Project_FFT;


import TP2.ImportWav;
import TP2.WavJava;

/**
 * Created by ushakova on 24/04/16.
 */
public class FFT {
    public static String filePath = "assets/sons/la.wav";

    public static Complex[] fft_CT(Complex[] complex) {

            complex = complex.clone();

            int n = complex.length;
            if (n == 1) return new Complex[] { complex[0] };

            if (Integer.highestOneBit(n) != n) {
                throw new RuntimeException("N is not a power of 2"); }

            Complex[] even = new Complex[n/2];
            for (int k = 0; k < n/2; k++) {
                even[k] = complex[2*k];
            }
            Complex[] q = fft_CT(even);


            Complex[] odd  = even;
            for (int k = 0; k < n/2; k++) {
                odd[k] = complex[2*k + 1];
            }
            Complex[] r = fft_CT(odd);

            Complex[] fft_out = new Complex[n];
            for (int k = 0; k < n/2; k++) {
                double kth = -2 * k * Math.PI / n;
                Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
                fft_out[k]       = q[k].add(wk.mul(r[k]));
                fft_out[k + n/2] = q[k].sub(wk.mul(r[k]));
            }
            return fft_out;
        }

        public static Complex[] ifft_CT(Complex[] x) {
            int N = x.length;
            Complex[] y = new Complex[N];

            for (int i = 0; i < N; i++) {
                y[i] = x[i].getConjugate();
            }
            y = fft_CT(y);

            for (int i = 0; i < N; i++) {
                y[i] = y[i].getConjugate();
            }
            for (int i = 0; i < N; i++) {
                y[i] = y[i].times(1.0 / N);
            }

            return y;

        }





    public static Complex[] fft_by_algo(Complex[] complex) {

        int n = complex.length;
        if ((n & (n - 1)) == 0)
      //  if (Integer.highestOneBit(n) != n)
            CooleyTukey(complex);
        else
            transformBluestein(complex);
        return  complex;
    }
//sans rescaling!!
    public static void inverseTransform(double[] real, double[] imag) {

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

    public static void inverseTransformB(double[] real, double[] imag) {

        int n = real.length;
        double[] coim =new double[n];
        for (int i = 0; i < n; i++) {
            coim[i] = - imag[i];
        }
        transformBluestein(real, coim);
        for (int i = 0; i < n; i++) {
            imag[i] = - coim[i];
        }


    }

    public static void inverseTransform1(double[] real, double[] imag) {

        CooleyTukey(imag, real);
    }






    public static Complex[] inverseTransform(Complex[] complex) {
        int N = complex.length;
        Complex[] y = new Complex[N];
        double[] new_re= new double[N];
        double[] new_im= new double[N];

        for (int i = 0; i < N; i++) {
            new_re[i]=complex[i].getIm();
            new_im[i]=complex[i].getRe();
            complex[i].setRe(new_re[i]);
            complex[i].setIm(new_im[i]);
        }
        y = fft_by_algo(complex);

        return y;
    }


    public static Complex[] inverseTransform1(Complex[] complex) {
        int N = complex.length;
        Complex[] y = new Complex[N];
        Complex[] res = new Complex[N];
        double[] new_re= new double[N];
        double[] new_im= new double[N];

        for (int i = 0; i < N; i++) {
            new_re[i]=complex[i].getIm();
            new_im[i]=complex[i].getRe();
            complex[i].setRe(new_re[i]);
            complex[i].setIm(new_im[i]);
        }
        y = fft_by_algo(complex);
        for (int i = 0; i < N; i++) {
            res[i] = y[i].times(1. / N);
        }

        return res;
    }




    public static void CooleyTukey(double[] real, double[] imag) {

        int n = real.length;
        if (Integer.highestOneBit(n) != n){
            throw new IllegalArgumentException("Length is not a power of 2");
        }
        int log_factor = (int) Math.floor(Math.log(n)/Math.log(2));

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
                    double tpre =  real[j+halfsize] * cosTable[k] + imag[j+halfsize] * sinTable[k];
                    double tpim = -real[j+halfsize] * sinTable[k] + imag[j+halfsize] * cosTable[k];
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




    public static Complex[] CooleyTukey(Complex[] complex) {
        complex=complex.clone();
        int n = complex.length;
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

        //Bit Reversing

        int shift = 1 + Integer.numberOfLeadingZeros(n);//32 ou 64: nbLZ(n)=31-floor(log(n))
        for (int k = 0; k < n; k++) {
            int j = Integer.reverse(k) >>> shift;
            if (j > k) {
                Complex temp = complex[j];
                complex[j] = complex[k];
                complex[k] = temp;
            }
        }


        for (int size = 2; size <= n; size = size + size) {
            for (int i = 0; i < size / 2; i += size) {
                double pi_part = -2 * i * Math.PI / size;
                Complex pi_array = new Complex(Math.cos(pi_part), Math.sin(pi_part));
                for (int j = 0; j < n / size; j++) {  //n/size - tablestep
                    Complex res = pi_array.mul(complex[j * size + i + size / 2]);
                    complex[j * size + i + size / 2] = complex[j * size + i].sub(res);
                    complex[j * size + i] = complex[j * size + i].add(res);
                }
            }
        }
        return complex;
    }




    public static void transformBluestein(Complex[] c ) {
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



    public static void transformBluestein(double[] real, double[] imag  ) {

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






    public static Complex[] Bluestein(Complex[] complex) {

        int n = complex.length;
        int m = Integer.highestOneBit(n * 2 + 1) << 1;


        double[] cosTable = new double[n];
        double[] sinTable = new double[n];
        for (int i = 0; i < n; i++) {
            int j = (i*i);
           // complex[i].getRe();
            cosTable[i] = Math.cos(Math.PI * j / n);
            sinTable[i] = Math.sin(Math.PI * j / n);
        }


        double[] areal = new double[m];
        double[] aimag = new double[m];
        for (int i = 0; i < n; i++) {

            complex[i].setRe(complex[i].getRe()*cosTable[i] + complex[i].getIm()*sinTable[i]);
            areal[i] = complex[i].getRe();

            complex[i].setIm(-complex[i].getRe()*sinTable[i] + complex[i].getIm()*cosTable[i]);
            aimag[i] = complex[i].getIm();

        }
        double[] breal = new double[m];
        double[] bimag = new double[m];
        breal[0] = cosTable[0];
        bimag[0] = sinTable[0];
        for (int i = 1; i < n; i++) {
            breal[i] = breal[m - i] = cosTable[i];
            bimag[i] = bimag[m - i] = sinTable[i];
        }


        double[] creal = new double[m];
        double[] cimag = new double[m];
        convolve(areal, aimag, breal, bimag, creal, cimag);


        for (int i = 0; i < n; i++) {
            complex[i].setRe(creal[i] * cosTable[i] + cimag[i] * sinTable[i]);
            complex[i].setIm(-creal[i] * sinTable[i] + cimag[i] * cosTable[i]);

        }
        return complex;
    }

    public static void convolve(double[] x, double[] y, double[] out) {
        if (x.length != y.length || x.length != out.length)
            throw new IllegalArgumentException("Mismatched lengths");
        int n = x.length;
        convolve(x, new double[n], y, new double[n], out, new double[n]);
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

        inverseTransform(xreal, ximag);

        for (int i = 0; i < n; i++) {
            outreal[i] = xreal[i] / n;
            outimag[i] = ximag[i] / n;
        }
    }





    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }

 /*   public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }

        int N = x.length;

        Complex[] a = fft_by_algo(x);
        Complex[] b = fft_by_algo(y);

        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].mul(b[i]);
        }
        return inverseTransform(c);
    }


    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2*x.length];
        for (int i = 0;        i <   x.length; i++) a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        for (int i = 0;        i <   y.length; i++) b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }
*/

 /*   public static void Magnitude(Complex[] complex){
        int n = complex.length;
        double[] magnitude = new double[n];
        for(int i=0; i<n; i++){
            magnitude[i]= complex[i].getMagnitude();

        }
    }

    public static void Argument(Complex[] complex){
        int n = complex.length;
        double[] argument = new double[n];
        for(int i=0; i<n; i++){
            argument[i]= complex[i].getArgument();

        }
    }
*/
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
        int N = 1024;
        Complex[] x = new Complex[N];

        for (int i = 0; i < N; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
        }

        long startTime_ct = System.nanoTime();
        Complex[] ct = CooleyTukey(x);
        long endTime_ct = System.nanoTime();
       show(ct, "fft_CT(x)");

        long startTime_c = System.nanoTime();
        Complex[] c = fft_CT(x);
        long endTime_c = System.nanoTime();
        show(c, "rec_fft(x)");

        long startTime_b = System.nanoTime();
        transformBluestein(x);
        long endTime_b = System.nanoTime();
       show(x, "b = fft_B(x)");




        long startTime_abc = System.nanoTime();
        Complex[] abc = fft_by_algo(x);
        long endTime_abc = System.nanoTime();
        show(abc, "fft_by_algo");



        long duration_ct = (endTime_ct - startTime_ct)/100000;
        long duration_b = (endTime_b - startTime_b)/100000;
        long duration_c = (endTime_c - startTime_c)/100000;
        long duration_abc = (endTime_abc - startTime_abc)/100000;
      //  long diff = duration_ct - duration_b;
        /*
        if (diff>0) {
            System.out.println("Bluestein est plus rapide de " + diff + " millisec");
        }
        else {
            System.out.println("Coley-Tukey est plus rapide de " + Math.abs(diff) + "millisec");
        }*/
        System.out.println("N = "+N);
        System.out.println("duration de Cooley-Tukey = " + duration_c +" ms");
        System.out.println("duration de Cooley-Tukey avec bits = " + duration_ct+" ms");
        System.out.println("duration de Bluestein = " + duration_b+" ms");
        System.out.println("duration fft_by_algo = " + duration_abc+" ms");


    }


}


