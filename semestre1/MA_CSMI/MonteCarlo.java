package MA_CSMI;



public class MonteCarlo {

    MyRandom rand = new MyRandom();

    public MonteCarlo() {

    }

    public double estimeEsperance(VariableAleatoire va, double precision) {

        double res = 0;
        int compteurDePetitDelta = 0;
        int N = 0;
        do {
            N++;
            double tirage = va.nextSimulation();
            if (Math.abs(tirage / N) < precision) compteurDePetitDelta++;
            else compteurDePetitDelta = 0;
            res = res + tirage;
        } while (compteurDePetitDelta < 20);

        return res / N;
    }

    public double estimeVariance(VariableAleatoire va, double precision) {

        double esp = estimeEsperance(va, precision);

        double espDuCarre = 0;
        int compteurDePetitDelta = 0;
        int N = 0;
        do {
            N++;
            double tirage = va.nextSimulation();
            if (Math.abs(tirage / N) < precision) compteurDePetitDelta++;
            else compteurDePetitDelta = 0;
            espDuCarre += tirage * tirage;
        } while (compteurDePetitDelta < 20);

        espDuCarre = espDuCarre / N;

        return espDuCarre - esp * esp;
    }



    public static void main(String[] args) {

        // VariableAleatoire va=new CarreDuneVaUniforme();

        VariableAleatoire va = new VaUnifDansDisque();

        MonteCarlo monteCarlo = new MonteCarlo();

        double a = monteCarlo.estimeVariance(va, 0.0001);
        System.out.println("estimation= " + a);

    }

}
