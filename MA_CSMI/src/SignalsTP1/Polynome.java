package SignalsTP1;

import java.util.Arrays;

/**
 * Created by Oxana on 27/01/2016.
 */
public class Polynome {


    double[] coef;
    int pow ;
    double [] zero = {0};

  /*  public Polynome ( double coefs, int pow){
        coef= new double[pow+1];
        coef[pow] = coefs;
        pow = this.pow;
    }

*/
    public Polynome ( double[] coef, int pow){
            coef= new double[pow+1];
            coef = this.coef;
        }

/*
    public Polynome derive () {
            if (pow == 0) return new Polynome(0,0);
            double [] coef_new;
            Polynome p = new Polynome(0, pow-1);
            p.pow= pow-1;
            coef_new= new double[pow+1];
            for (int i =0; i< pow; i++){
                    coef_new[i] = coef[i] * i;
                }
            p.coef= coef_new;
            return p;
    }
*/
        @Override
        public String toString() {
            return "Polynomes{" +
                    "coef=" + Arrays.toString(coef) +
                    ", pow=" + pow +
                    '}';
        }

        public Polynome add (Polynome A, Polynome B) {

            double[] coef_new;
            coef_new= new double[pow];
            coef= new double[pow];
            pow = Math.max(A.pow, B.pow);

            for (int i =0; i<= pow; i++){
                coef_new[i]= A.coef[i] + B.coef[i] ;
            }
            coef = coef_new;
            return new Polynome(coef, pow);
        }


        public Polynome multi (Polynome A, double x) {

            double[] coef_new;
            coef_new= new double[pow];
            coef= new double[pow];

            for (int i =0; i<= pow; i++){
                coef_new[i]= A.coef[i] *x  ;
            }
            coef = coef_new;
            return new Polynome(coef_new, pow);
        }


        public Polynome multiPol (Polynome A, Polynome B) {

            double[] coef_new;
            Polynome C = new Polynome(zero, A.pow + B.pow);
              for (int i =0; i<= pow; i++){
                C.coef[i]= A.coef[i] * B.coef[i];
             }
             return C;
        }







        public static void main(String[] args) {


            double [] coef_new={7.,2.,3.,5.};
            int p = coef_new.length;
            double [] coef_new2={7.,0.,3.,9.};

           Polynome P1 = new Polynome(coef_new, p);
           Polynome P2 = new Polynome(coef_new2, p);

            System.out.println("Poly with coefs :"+ Arrays.toString(coef_new)+ "  and the dergee: " +(p-1));
            Polynome B = P1.add(P1,P2);
            Polynome C = P1.multiPol(P1,P2);
           // System.out.println(Arrays.toString(B.coef) + B.pow);
            System.out.println(C);

            // System.out.println(P1.multi(P1, 6));


        }
    }

