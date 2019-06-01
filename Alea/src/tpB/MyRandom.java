package tpB;

/**
 * Created by ushakova on 03/10/16.
 */
import java.util.ArrayList;
import java.util.Random;



/*
 * Plutôt que de recréer une classe de simulation, nous étendons la précédente.
 */
public class MyRandom extends Random {



    public MyRandom(){
        // le constructeur doit faire appel au constructeur de la classe mère :
        super();
    }


    /*
     * On met ici nous propres méthodes qui s'ajouterons à celles déjà existence dans la classe mère.
     */
    public int nextDiscrete(double [] probas){
        int res=0;

        double unReel = super.nextDouble();
        double cumul=probas[0];

        while (unReel > cumul){
            res++;
            cumul = cumul + probas[res];
        }

        return res;
    }

    public int nextRadGen(int g, int d) {

        int amplitude = d - g + 1;
        int alea = this.nextInt(amplitude);

        return alea + g;
    }


    public static double nextUnif(double begin, double end) {
        Random randomdouble = new Random();
        return begin + (end - begin) * randomdouble.nextDouble();
    }


    public int nextRademacher(){
        return super.nextInt(2)*2-1;
    }

    public double nextGeo (double p){
        double unReel = super.nextDouble();
        double res = 0;
        double q = 1 - p;
        return Math.floor(Math.log10(unReel)/Math.log(q));
    }




    public double Beta (double x, double alpha, double beta){
        return Math.pow(x, alpha-1)*Math.pow(1-x, beta-1);

    }

    public double nextBeta (double alpha, double beta){
        //find max

        // double max = Beta((alpha-1)/(alpha+beta-2),alpha, beta);
        double max=Beta((1-alpha)/(2-alpha-beta),alpha,beta);
        double x=1,y=1;
        while (y>Beta(x,alpha,beta)){
            x= nextUnif(0,1);
            y=nextUnif(0,max);
                    }
        return x;
    }



    /**
     *  Créer maintenant une méthode qui renvoie un tirage de loi géométrique.
     */


    /**
     * A l' avenir, vous rajouterez ici toutes les méthodes qui permettent de
     * tirer des nombres aléatoire (il y aura une nextPoisson, une nextGeometric, une nextAlpha).
     */


    public static void main(String[] args) {

        MyRandom myRand=new MyRandom();
        double [] probabilites={0.2,0.2,0.6};

        ArrayList<Integer> desTirages= new ArrayList<Integer>();

        for (int i=0;i<20;i++){
            desTirages.add(myRand.nextDiscrete(probabilites));
        }
        System.out.println(desTirages);

        ArrayList<Double> desTiragesD = new ArrayList<Double>();
        for (int i = 0; i < 5; i++) {
            desTiragesD.add(myRand.nextGeo(0.1));
        }
        System.out.println("Next Geo  "+desTiragesD);

    }

}