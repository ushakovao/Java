package Project_FFT;

/**
 * Created by ushakova on 26/04/16.
 */
public class Complex {

        private double re, im;

        public Complex() {
        }

        public Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public Complex(Complex source) {
            if (source != null) {
                re = source.re;
                im = source.im;
            }
        }

        public double getRe() {
            return re;
        }

        public void setRe(double re) {
            this.re = re;
        }

        public double getIm() {
            return im;
        }

        public void setIm(double im) {
            this.im = im;
        }

        public Complex getConjugate() {

            return new Complex(re, im * -1);
        }

        public Complex add(Complex n) {

            return new Complex(re + n.getRe(), im + n.getIm());
        }

        public Complex sub(Complex n) {

            return new Complex(re - n.getRe(), im - n.getIm());
        }

        public Complex mul(Complex n) {
            return new Complex(re * n.getRe() - im * n.getIm(), re * n.getIm() + im * n.getRe());
        }


        public Complex times(double alpha) {
        return new Complex(re * alpha , im * alpha);
        }

        public Complex div(Complex op) {
            Complex result = new Complex(this);
            result = result.mul(op.getConjugate());
            double opNormSq = op.getRe() * op.getRe() + op.getIm() * op.getIm();
            result.setRe(result.getRe() / opNormSq);
            result.setIm(result.getIm() / opNormSq);
            return result;
        }


        public static Complex fromPolar(double magnitude, double angle) {
            Complex result = new Complex();
            result.setRe(magnitude * Math.cos(angle));
            result.setIm(magnitude * Math.sin(angle));
            return result;
        }

        public double getMagnitude() {
            return Math.sqrt(re * re + im * im);
        }

        public double getArgument() {
            return Math.atan2(im, re);
        }

        @Override
        public String toString() {
           // return re + "+i." + im + " (" + getMagnitude() + ".exp(i " + (getArgument() / Math.PI) + " PI)";
            return re + "+i." + im ;
        }

    }

