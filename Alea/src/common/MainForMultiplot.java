package common;


import sun.org.mozilla.javascript.Function;
import tpD.CourbeDeGauss;
import tpD.FonctionConstante;
import tpD.FonctionId;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vigon on 12/05/2016.
 */
public class MainForMultiplot {

    public static void main(String[] args) {

        MultiPlotA multi = new MultiPlotA();

        String testType="genericFunction";

        if(testType.equals("plotable")){
            multi.nbGraduationX=8;
            multi.addPlotable("Gauss", new CourbeDeGauss(100, -2, 2));
            multi.addPlotable("Identité", new FonctionId(100, -2, 2));
            multi.addPlotable("Constante", new FonctionConstante(20,-2,2,0.5));
            multi.addPlotable("AutreConstante", new FonctionConstante(20,-2,2,0.1));


            // Maintenant on peut changer les couleurs
            multi.setColor("Gauss", Color.red);
            multi.setColor("Identité", Color.blue);

            // on peut changer le style de trait.
            multi.setStroke("Gauss", MultiPlotA.dashed);
            multi.setStroke("Identité", MultiPlotA.thick);

            //  ne pas joindre les points
            multi.setJoinType("Constante", MultiPlotA.JoinType.bullet,10);
            multi.setColor("Constante",Color.magenta);

            // joindre les points et mettre des bullet
            multi.setJoinType("AutreConstante", MultiPlotA.JoinType.joinBullet,10);
            multi.setStroke("AutreConstante", MultiPlotA.thick);
            // on précise une hauteur, pour faire passer cette courbe au-dessus
            multi.setZIndex("AutreConstante",10);



            // et l'on peut imposer un rectangle
            multi.imposeRectangle(-2, -1, 1, 1);

            //quand tous les paramètres sont fixé, on peut tracer
            multi.plotNow();
        }
        else  if(testType.equals("plotableColors")){
            multi.addPlotable("Identité coloré", new IdentityColored());
            multi.plotNow();
        }
        else  if(testType.equals("plotablePoint")){
            multi.addPlotable("identité pointé", new IdentityPointed(1));

            multi.addPlotable("- identité pointé", new IdentityPointed(-1));
            multi.setJoinType("- identité pointé", MultiPlotA.JoinType.joinSquare);


            multi.plotNow();
        }
        else if (testType.equals("genericFunction")) {

            ArrayList<Double> xs = new ArrayList<Double>();
            ArrayList<Double> ys = new ArrayList<Double>();

            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                xs.add((double) i);
                ys.add(random.nextDouble());

            }


            common.Function f = new common.Function(xs,ys);
            MultiPlotA multiPlot=new MultiPlotA();

            multiPlot.addPlotable("fonc rand",f);
            multiPlot.plotNow();



        }




    //Class Function est mal nommée - on doit préciser qu'on parle du package common


        }



    }


