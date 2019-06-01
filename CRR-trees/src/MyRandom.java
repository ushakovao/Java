
import java.util.ArrayList;
import java.util.Random;



	/*
     * Plutôt que de recréer une classe de simulation, nous étendons la précédente.
     */
	public class MyRandom extends Random {



		public MyRandom(){
			// le constructeur doit faire appel au constructer de la classe mère :
			super();
		}


		/*
         * On met ici nous propres méthodes qui s'ajouterons à celles déjà existance dans la classe mère.
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


		public double nextGeo (double  probas){

			double unReel = super.nextDouble();
			double res = 0;
			double q;
			q = 1 - probas;
			while (super.nextDouble() > probas){
					res++;
			}

				return res;
			}

		public int nextPoisson (double lambda) {
			double L = Math.exp(-lambda);
			double p = 1.0;
			int k = 0;

			do {

				p *= Math.random();
				k++;
			} while (p > L);

			return k - 1;
		}


		public static double nextUnif(double begin, double end) {

			Random randomdouble = new Random();


			return begin + (end - begin) * randomdouble.nextDouble();

		}




		public double GN (int n,double mu, double var ) {
			double sum = 0;
			for  (int i=0;  i < n;i++ ) {
					double x0;
					x0 = nextLourde2(10,0.1);
					sum += x0;
			}
			double sqvar = Math.pow(var, 0.5);
			return (sum-n*mu)/(Math.pow(n,0.5)*sqvar);
		}



		public double GN_l (int n ) {
			double sum = 0;
			double alpha =1;
			for  (int i=0;  i < n;i++ ) {
				double x0;

				x0 = nextLourde2(alpha, 0.5);
				sum += x0;
			}
			double new_n = Math.pow(n, 1/(alpha-1));
			return (sum)/(new_n);
		}






		public  double nextWeibull(double echelle, double forme) {
			double x = super.nextDouble();
			return echelle*(Math.pow((-Math.log(1-x)),(1./forme)));
		}

		public double nextExp( double lambda) {

			return  Math.log(1-super.nextDouble())/(-lambda);
		}


		public  double nextLourde1(double alpha) {
			double x = nextUnif(0.1, 1);
			return Math.pow((1 - x), 1 / (1 - alpha))-1;
		}


		public  double nextLourde2(double alpha, double beta) {
			double U = super.nextDouble();
			double b = super.nextDouble();
			if (b < beta) {
				return ((Math.pow(1 - U, 1 / (1 - alpha))) - 1);
			} else {
				return -((Math.pow(1 - U, 1 / (1 - alpha))) - 1);
			}
		}


		public double st_gamma(double x){
			return Math.sqrt(2*Math.PI/x)*Math.pow((x/Math.E), x);
		}



		public double nextRademacher () {
			double c;
			double x = super.nextDouble();
			if (x>0 && x<= 0.5)
				c=-1;
			else
				c=1;
			return c;
		}

		public static double phi(double x) {
			return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
		}




		public static double Phi(double z) {
			if (z < -8.0) return 0.0;
			if (z >  8.0) return 1.0;
			double sum = 0.0, term = z;
			for (int i = 3; sum + term != sum; i += 2) {
				sum  = sum + term;
				term = term * z * z / i;
			}
			return 0.5 + sum * phi(z);
		}














		public double foncBeta(double x, double alpha, double beta){

			return Math.pow(x,alpha-1)*Math.pow(1-x,beta-1);
		}

		public double nextBeta(double alpha, double beta){
			double M,x=1,y=1;
			if (alpha<=1 || beta<=1){
				throw new RuntimeException("Les arguments ne sont pas valides");
			}
			M=foncBeta((1-alpha)/(2-alpha-beta),alpha,beta);
			while (y>foncBeta(x,alpha,beta)) {
				x=nextUnif(0, 1);
				y=nextUnif(0,M);
			}
			return x;
		}






		public double foncBetaN(double x, double alpha, double beta){
			double alpha_g = Math.sqrt(2 * Math.PI * (alpha-1)) * Math.pow(((alpha-1) / Math.E), (alpha-1));
			double beta_g = Math.sqrt(2 * Math.PI * (beta-1)) * Math.pow(((beta-1) / Math.E), (beta-1));
			double ab_g = Math.sqrt(2 * Math.PI * (alpha + beta-1)) * Math.pow(((alpha + beta-1) / Math.E), (alpha + beta-1));

			return Math.pow(x,alpha-1)*Math.pow(1-x,beta-1)* ( ab_g/(alpha_g * beta_g));
		}

		public double nextBetaN (double alpha, double beta){
			double M,x=1,y=1;
			if (alpha<=1 || beta<=1){
				throw new RuntimeException("Les arguments ne sont pas valides");
			}
			M=foncBeta((1-alpha)/(2-alpha-beta),alpha,beta);
			while (y>foncBeta(x,alpha,beta)) {
				x=nextUnif(0, 1);
				y=nextUnif(0,M);
			}
			return x;
		}








}
