/**
 * Created by ushakova on 19/07/16.
 */
public class Test {

    public static void main(String[] args) {
//Use this console for changing the parameters

        double S_0 = 30;//Initial value for the stock
        double K = 31;//Strike
        double u = 1.1;//Down
        double r = 1.05;//1 + Interest rate
        double d = 1/u;//Up
        int steps = 10;//Steps


        Functions item = new Functions();


        System.out.println("\n" + "The price of your option is: " + Math.round(item.BinomialPricer(S_0, d, u, r, steps, K) * 1000.0) / 1000.0 + " $");
    }
}