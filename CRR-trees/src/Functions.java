/**
 * Created by ushakova on 19/07/16.
 */
import java.util.Scanner;
public class Functions
{
    public static double RiskNeutralProbability(double down, double up, double r)
    {
// This function returns the risk neutral probability of an up movement
        double q = (r - down) / (up - down);
        return q;
    }
    public static double StockEvolution(double S_0, double down, double up, int totalsteps, int upsteps)
    {
// First of all, We check if the inputs are reasonable
        while (upsteps < 0 || upsteps > totalsteps)
        {
            System.out.println("You have inserted a wrong values for the variable upsteps, please provide me an acceptable one:");
            Scanner temp = new Scanner(System.in);
            upsteps = temp.nextInt();
        }
        double S_t = S_0*(Math.pow(up, upsteps))*(Math.pow(down, totalsteps - upsteps)); //Compute the stock evolution

        return S_t; //Return the stock value at the n-th step
    }
    public static double CallPayoff(double S, double K)
    {
        return Math.max(S - K, 0.0); //Return the payoff of a vanilla call option
    }
    public static double PutPayoff(double S, double K)
    {
        return Math.max(K - S, 0.0); //Return the payoff of a vanilla put option
    }
    public static double BinomialPricer(double S_0, double down, double up, double r, int steps,
                                        double K)
    {
        Scanner temp = new Scanner(System.in);
//Firstly, We check if the inputs are correct
        if (S_0 <= 0.0 || up <= -1.0 || down <= -1.0 || r <= -1.0 || up <= down)
        {
            System.out.println("Wrong Inputs, the program is closing");
            System.exit(1);
        }
//Then We check if the market excludes arbitrage opportunities
        if (r < down || r > up)
        {
            System.out.println("Wrong Inputs, the program is closing");
            System.exit(1);
        }
//Once the cheking phase is passed, We decide which vanilla options must be priced
        System.out.println("Select one of the following options: " + "\n" + "1 - European Option" +
                "\n" + "2 - American Option" + "\n");
        int mainselection = temp.nextInt();
        double[] prices = new double[steps + 1]; //array that stores all the values of the option
        double q = RiskNeutralProbability(down, up, r);
        if (mainselection == 1)
        {
            System.out.println("\n" + "Select one of the following options: " + "\n" + "1 - Vanilla Call" + "\n" + "2 - Vanilla Put" + "\n");
            int selection = temp.nextInt();
            if (selection == 1)
            {
                for (int i = 0; i <= steps; i++)
                {
                    prices[i] = CallPayoff(StockEvolution(S_0, down, up, steps, i), K);

                }
            }
            else if (selection == 2)
            {
                for (int i = 0; i <= steps; i++)
                {
                    prices[i] = PutPayoff(StockEvolution(S_0, down, up, steps, i), K);
                }
            }
            else
            {
                System.out.println("You should choose among the available options, the program is closing");
                        System.exit(1);
            }
            for (int n = steps - 1; n >= 0; n--)
            {
                for (int i = 0; i <= n; i++)
                {
                    prices[i] = (1 / r) * (prices[i + 1] * q + (1 - q) * prices[i]);

                }
                System.out.println("The option price at node n="+n+ " is " + prices[n]);
            }
        }
        else if (mainselection == 2)
        {
            System.out.println("\n" + "Select one of the following options: " + "\n" + "1 - Vanilla Call" + "\n" + "2 - Vanilla Put" + "\n");int selection = temp.nextInt();
            if (selection == 1)
            {
                for(int i = 0; i <= steps; i++)
                {
                    prices[i] = CallPayoff(StockEvolution(S_0, down, up, steps, i), K);
                }
            }
            else if (selection == 2)
            {
                for (int i = 0; i <= steps; i++)
                {
                    prices[i] = PutPayoff(StockEvolution(S_0, down, up, steps, i), K);
                }
            }
            else
            {
                System.out.println("You should choose among the available options, the program is closing");
                        System.exit(1);
            }
            for (int n = steps - 1; n >= 0; n--)
            {
                for (int i = 0; i <= n; i++)
                {
                    prices[i] = (1 / r) * (prices[i + 1] * q + (1 - q) * prices[i]);
                    if (selection== 1 && (StockEvolution(S_0, down, up, n, i) - K) > prices[i])
                    {
                        prices[i]= StockEvolution(S_0, down, up, n, i) - K;

                    }
                    if (selection== 2 && (K - StockEvolution(S_0, down, up, n, i)) > prices[i])
                    {
                        prices[i]= K - StockEvolution(S_0, down, up, n, i);

                    }


                }
                System.out.println("The option price at node n="+n+ " is " + prices[n]);
            }
        }
        else
        {
            System.out.println("You should choose among the available options, the program is closing");
                    System.exit(1);
        }
        return prices[0]; //Return the price at time zero of the option


    }
}


