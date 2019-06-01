/**
 * Created by ushakova on 19/07/16.
 */
public class MyBT {
    public double MyBT(double T, double S, double r, double K, double sigma, double q, int n) {
        double delta = T / n;
        double u_p = Math.exp(sigma * Math.sqrt(delta));
        double p_0 = (u_p * Math.exp(-r * delta) - Math.exp(-q * delta)) * u_p / (u_p * u_p - 1);
        double p_1 = Math.exp(-r * delta) - p_0;
        double[] p = new double[n+1];
        for (int i = 0; i < n; i++) {
            p[i] = K - S * Math.pow(u_p, 2 * i - n);
            if (p[i] < 0) p[i] = 0;
        }


        for (int j = n - 1; j >= 0; j--) {
            for (int i = 0; i <= j; i++)

            {
                double po = 0;
                p[i] = p_0 * p[i] + p_1 * p[i + 1];
                po = K - S * Math.pow(u_p, 2 * i - j);
                if (p[i] < po) {
                    p[i] = po;
                }
            }

        }

        return p[0];
    }

    public static void main(String[] args) {
        double T=10;
        double S = 100.0;//Initial value for the stock
        double K = 100.0;//Strike
        double q = 0.02;//Down
        double r = 0.05;//1 + Interest rate
        double sigma = 0.3;//Up
        int n = 10;//Steps

        MyBT m = new MyBT();
        double otvet =  m.MyBT(T,S,r,K,sigma,q,n);
        System.out.print(otvet);

    }



}
