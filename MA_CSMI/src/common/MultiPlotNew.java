package common;

/**
 * Created by Oxana on 18/11/2015.
 */

import common.FonctionConstante;
import common.PlotablePoint;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Plot graphics. Entries must implement the interface  PlotableMA.
 * Legend are automatically added
 * add Graduation can be add, color and stroke can be chosen
 *
 * @author vigon
 *
 */

public class MultiPlotNew {
    public static enum JoinType{
        join, square, bullet,joinSquare,joinBullet
    }
    //entries
    private HashMap<String, PlotablePlus> nameToPlotablePlus = new HashMap<String, PlotablePlus>();
    public Dimension marge = new Dimension(40, 40);
    public int nbGraduationX = 4;
    public int nbGraduationY = 4;
    public String title=null;
    // other
    private Dimension dimUsefull;
    private Dimension dimFenetre = new Dimension(1000, 1000);
    private double rectangle[] = new double[4];
    private boolean computeRectangle = true;
    private Color[] defaultColors = {
            new Color(0, 0, 0), // black
            new Color(135, 206, 250), // blue
            new Color(255, 193, 37), // yellow
            new Color(193, 255, 193), // green
            new Color(255, 127, 36), // orange
            new Color(205, 183, 181) // brown
    };
    public void setDim(int x, int y){
        Dimension test = new Dimension(x,y);
        dimFenetre=test;
    }
    public void addPlotable(String name, PlotableMA plotable) {
        if (nameToPlotablePlus.get(name) != null) System.out.println("attention, vous avez �cras� un ancien PlotableMA");
        nameToPlotablePlus.put(name, new PlotablePlus(name, plotable, JoinType.join));
    }
    /**en plus d'une liste de Xs et Us, les plotablePoints fournissent une liste de radius : le rayon de chacune des bullets � tracer  */
    public void addPlotablePoint(String name, PlotablePoint plotablePoint) {
        if (nameToPlotablePlus.get(name) != null) System.out.println("attention, vous avez �cras� un ancien PlotableMA");
        PlotablePlus plotablePlus=new PlotablePlus(name, plotablePoint,JoinType.bullet);
        nameToPlotablePlus.put(name, plotablePlus);
    }


    //	/**en plus d'une liste de Xs et Us, les plotablePoints fournissent une liste de radius : le rayon de chacune des bullets � tracer  */
	/*public void addPlotablePoint(String name, PlotablePoint plotablePoint) {
	if (nameToPlotablePlus.get(name) != null) System.out.println("attention, vous avez �cras� un ancien PlotableMA");
		PlotablePlus plotablePlus=new PlotablePlus(name, plotablePoint,JoinType.bullet);
		nameToPlotablePlus.put(name, plotablePlus);
//
	}*/



    public void setColor(String name, Color color) {
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null)
            pp.color = color;
        else
            System.err.println("attention, il n'y a pas de PlotableMA du nom de "
                    + name);
    }
    public void setStroke(String name, Stroke stroke) {
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null) pp.stroke = stroke;
        else  System.err.println("attention, il n'y a pas de PlotableMA du nom de " + name);
    }
    public void setJoinType(String name,JoinType joinType,int defaultRadius){
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null) {
            pp.joinType = joinType;
            pp.defaultBulletRadius =defaultRadius;
        }
        else  System.err.println("attention, il n'y a pas de PlotableMA du nom de " + name);
    }
    // empty constructor. Default values are defined above
    public MultiPlotNew() {
    }
    // must be called after the filling of parameters
    public void plotNow() {
        if (nameToPlotablePlus.size() == 0) {
            System.err.println("no plotable");
        } else {
            if (computeRectangle)
                computeDefaultRectangle();
            int i = 0;
            for (PlotablePlus pp : nameToPlotablePlus.values()) {
                if (pp.color == null)
                    pp.color = defaultColors[i % defaultColors.length];
                i++;
            }
            new PlotFrame();
            new LegendFrame();
        }
    }
    private class PlotFrame extends JFrame {
        public PlotFrame() {
            setLocation(0, 0);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(dimFenetre.width, dimFenetre.height);
            setBackground(Color.white);
            setContentPane(new PlotPanel());
            setVisible(true);
        }
    }
    private class LegendFrame extends JFrame {
        public LegendFrame() {
            setSize(200, 20 * (nameToPlotablePlus.size() + 1) + 30);
            setLocation(dimFenetre.width, 0);
            setBackground(Color.white);
            setContentPane(new LegendPanel());
            setVisible(true);
        }
    }
    private class LegendPanel extends JPanel {
        public void paintComponent(Graphics g1) {
            // creates a copy of the Graphics instance, with more options, like
            // the stroke
            Graphics2D g = (Graphics2D) g1.create();
            if (title!=null) g.drawString(title, 10, 20);
            int i = 1;
            for (PlotablePlus pp : nameToPlotablePlus.values()) {
                g.setColor(pp.color);
                g.setStroke(pp.stroke);
                g.drawLine(0, 20 + 20 * i, 40, 20 + 20 * i);
                g.drawString(pp.name, 43, 20 + 20 * i);
                i++;
            }
        }
    }
    public void imposeRectangle(double Xmin, double Ymin, double Xmax,
                                double Ymax) {
        rectangle[0] = Xmin;
        rectangle[1] = Ymin;
        rectangle[2] = Xmax;
        rectangle[3] = Ymax;
        computeRectangle = false;
    }
    private void computeDefaultRectangle() {
        ArrayList<Double> lesMinX = new ArrayList<Double>();
        ArrayList<Double> lesMaxX = new ArrayList<Double>();
        ArrayList<Double> lesMinY = new ArrayList<Double>();
        ArrayList<Double> lesMaxY = new ArrayList<Double>();
        for (PlotablePlus pp : nameToPlotablePlus.values()) {
            lesMinX.add(minimum(pp.plotable.getXs()));
            lesMaxX.add(maximum(pp.plotable.getXs()));
            lesMinY.add(minimum(pp.plotable.getYs()));
            lesMaxY.add(maximum(pp.plotable.getYs()));
        }
        double maxX = maximum(lesMaxX);
        double minX = minimum(lesMinX);
        double maxY = maximum(lesMaxY);
        double minY = minimum(lesMinY);
        rectangle = new double[4];
        rectangle[0] = minX;
        rectangle[1] = minY;
        rectangle[2] = maxX;
        rectangle[3] = maxY;
    }
    public int[][] convertiToPix(Dimension dimPanneau, PlotableMA fonc) {
        dimUsefull = new Dimension(dimPanneau.width - marge.width * 2,
                dimPanneau.height - marge.height * 2);
        int[][] sortie = new int[2][fonc.getXs().size()];
        double amplitudeX = rectangle[2] - rectangle[0];
        double amplitudeY = rectangle[3] - rectangle[1];
        for (int i = 0; i < fonc.getXs().size(); i++) {
            sortie[0][i] = (int) ((fonc.getXs().get(i) - rectangle[0])
                    / amplitudeX * dimUsefull.width)
                    + marge.width;
            sortie[1][i] = dimPanneau.height
                    - (int) ((fonc.getYs().get(i) - rectangle[1]) / amplitudeY * dimUsefull.height)
                    - marge.height;
        }
        return (sortie);
    }
    private class PlotPanel extends JPanel {
        public void paintComponent(Graphics g1) {
            // creates a copy of the Graphics instance, with more options, like
            // the stroke
            Graphics2D g = (Graphics2D) g1.create();
            for (PlotablePlus pp : nameToPlotablePlus.values()) {
                int[][] uneFoncPix;// = new int[2][pp.plotable.getXs().size()];
                uneFoncPix = convertiToPix(new Dimension(getWidth(),
                        getHeight()), pp.plotable);
                g.setColor(pp.color);
                /**on joint les points par les lignes bris�es*/
                if (pp.joinType== JoinType.join || pp.joinType== JoinType.joinBullet || pp.joinType== JoinType.joinSquare ) {
                    g.setStroke(pp.stroke);
                    g.drawPolyline(uneFoncPix[0], uneFoncPix[1], uneFoncPix[0].length);
                }
                /**on rajoute des bullets ou des carr�s sur chaque points*/
                if (pp.joinType == JoinType.bullet || pp.joinType == JoinType.joinBullet) {
                    int pointSize;
                    for (int i = 0; i < uneFoncPix[0].length; i++) {
                        if (pp.normalisedBulletRadii !=null) pointSize = (int) Math.floor(pp.normalisedBulletRadii.get(i));
                        else pointSize=pp.defaultBulletRadius;
                        g.fillArc(uneFoncPix[0][i], uneFoncPix[1][i], pointSize, pointSize, 0, 360);
                    }
                }
                if (pp.joinType == JoinType.square || pp.joinType == JoinType.joinSquare) {
                    int pointSize;
                    for (int i = 0; i < uneFoncPix[0].length; i++) {
                        if (pp.normalisedBulletRadii !=null) pointSize = (int) Math.floor(pp.normalisedBulletRadii.get(i));
                        else pointSize=pp.defaultBulletRadius;
                        g.fillRect(uneFoncPix[0][i], uneFoncPix[1][i], pointSize, pointSize);
                    }
                }
            }
            g.setColor(Color.black);
            g.setStroke(normal);
            g.drawRect(marge.width, marge.height, dimUsefull.width,
                    dimUsefull.height);
            // to clear an eventual line outside the zone
            g.setColor(Color.white);
            g.fillRect(0, 0, dimUsefull.width+2*marge.width, marge.height);
            g.fillRect(0, marge.height+dimUsefull.height+1, dimUsefull.width+2*marge.width, marge.height);
            g.fillRect(marge.width+dimUsefull.width+1, 0, marge.width, 2*marge.height+dimUsefull.height);
            if (title!=null) g.drawString(title, 10, 10);
            if (nbGraduationX > 0)
                rajouteLesGraduationsX(g);
            if (nbGraduationY > 0)
                rajouteLesGraduationsY(g);
        }
    }
    private double maximum(List<Double> tab) {
        double theMax = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMax < tab.get(i)) {
                theMax = tab.get(i);
            }
        }
        return (theMax);
    }
    private double minimum(List<Double> tab) {
        double theMin = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMin > tab.get(i)) {
                theMin = tab.get(i);
            }
        }
        return (theMin);
    }
    private void rajouteLesGraduationsX(Graphics g) {
        // graduations on x-axis
        g.setColor(Color.black);
        for (int i = 0; i < nbGraduationX + 1; i++) {
            int x = marge.width + (dimUsefull.width / nbGraduationX * i);
            int y = marge.height + dimUsefull.height;
            g.drawLine(x, y, x, y + 5);
            DecimalFormat format = new DecimalFormat("#0.0");
            String texte = format.format(rectangle[0]
                    + (rectangle[2] - rectangle[0]) / nbGraduationX * i);
            Dimension dimString = new Dimension((int) g.getFontMetrics()
                    .getStringBounds(texte, g).getWidth(), (int) g
                    .getFontMetrics().getStringBounds(texte, g).getHeight());
            g.drawString(texte, x - dimString.width / 2, y + dimString.height
                    + 5);
        }
    }
    private void rajouteLesGraduationsY(Graphics g) {
        // graduations on y-axis
        g.setColor(Color.black);
        for (int i = 0; i < nbGraduationY + 1; i++) {
            int x = marge.width;
            int y = 2
                    * marge.height
                    + dimUsefull.height
                    - (int) (marge.height + dimUsefull.height / nbGraduationY
                    * i);
            g.drawLine(x, y, x - 5, y);
            DecimalFormat format = new DecimalFormat("#0.0");
            String texte = format.format(rectangle[1]
                    + (rectangle[3] - rectangle[1]) / nbGraduationY * i);
            Dimension dimString = new Dimension((int) g.getFontMetrics()
                    .getStringBounds(texte, g).getWidth(), (int) g
                    .getFontMetrics().getStringBounds(texte, g).getHeight());
            g.drawString(texte, x - dimString.width - 5, y + dimString.height
                    / 2 - 3);
        }
    }
    public static void main(String[] args) {
        MultiPlotNew multi = new MultiPlotNew();
        multi.nbGraduationX=8;
     //   multi.addPlotable("Gauss", new CourbeDeGauss(100, -2, 2));
       // multi.addPlotable("Identit�", new FonctionId(100, -2, 2));
        multi.addPlotable("Constante", new FonctionConstante(20,-2,2,0.5));
        // Maintenant on peut changer les couleurs
        multi.setColor("Gauss", Color.red);
        multi.setColor("Identit�", Color.blue);
        // on peut changer le style de trait.
        multi.setStroke("Gauss", MultiPlotNew.dashed);
        multi.setStroke("Identit�", MultiPlotNew.thick);
        // ou bien ne pas joindre les points
        multi.setJoinType("Constante",JoinType.bullet,10);
        multi.setColor("Constante",Color.magenta);
        // et l'on peut imposer un rectangle
        multi.imposeRectangle(-2, -1, 1, 1);
        //quand tous les param�tres sont fix�, on peut tracer
        multi.plotNow();
    }
    /** you can create other strocke if you want */
    public static Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
    public static Stroke normal = new BasicStroke(1);
    public static Stroke thick = new BasicStroke(3);
    private class PlotablePlus {
        public String name;
        public PlotableMA plotable;
        private PlotablePoint plotablePoint;
        private ArrayList<Integer> normalisedBulletRadii =null;
        public Stroke stroke = normal;
        public Color color = null;
        public JoinType joinType=null;
        public int defaultBulletRadius =3;
        public int maxBulletRadius =5;
        public PlotablePlus(String aName, PlotableMA aPlotable,JoinType joinType) {
            this.joinType=joinType;
            if (aPlotable instanceof PlotablePoint ) {
                this.plotablePoint= (PlotablePoint) aPlotable;
                if (joinType==null) this.joinType= JoinType.bullet;
                double maxRad=-Double.MAX_VALUE;
                double minRad=Double.MAX_VALUE;
                for (Double r:this.plotablePoint.radius()){
                    if (r>maxRad) maxRad=r;
                    if (r<minRad) minRad=r;
                }
                boolean allEqual=false;
                if (maxRad-minRad<Double.MIN_VALUE*100) allEqual=true;
                normalisedBulletRadii =new ArrayList<Integer>();
                for (Double r:this.plotablePoint.radius()){
                    int norR;
                    if (allEqual) norR=30;
                    else norR= (int) Math.floor( (r-minRad)/(maxRad-minRad)*this.maxBulletRadius);
                    if (norR<3) norR=3;
                    this.normalisedBulletRadii.add(norR);
                }
            }
            name = aName;
            plotable = aPlotable;
        }
    }
}




/*public class MultiPlotNewMA {

    public static enum JoinType{
        join, square, bullet,joinSquare,joinBullet
    }

    //entries
    private HashMap<String, PlotablePlus> nameToPlotablePlus = new HashMap<String, PlotablePlus>();

    public Dimension marge = new Dimension(40, 40);
    public int nbGraduationX = 4;
    public int nbGraduationY = 4;

    public String title=null;

    // other
    private Dimension dimUsefull;
    private Dimension dimFenetre = new Dimension(1000, 500);
    private double rectangle[] = new double[4];

    private boolean computeRectangle = true;


    private Color[] defaultColors = {
            new Color(0, 0, 0), // black
            new Color(135, 206, 250), // blue
            new Color(255, 193, 37), // yellow
            new Color(193, 255, 193), // green
            new Color(255, 127, 36), // orange
            new Color(205, 183, 181) // brown
    };


    public void addPlotable(String name, PlotableMA plotable) {
        if (nameToPlotablePlus.get(name) != null) System.out.println("attention, vous avez �cras� un ancien PlotableMA");
        nameToPlotablePlus.put(name, new PlotablePlus(name, plotable));
    }

*//*


    //	/**en plus d'une liste de Xs et Us, les plotablePoints fournissent une liste de radius : le rayon de chacune des bullets � tracer  */
	/*public void addPlotablePoint(String name, PlotablePoint plotablePoint) {
	if (nameToPlotablePlus.get(name) != null) System.out.println("attention, vous avez �cras� un ancien PlotableMA");
		PlotablePlus plotablePlus=new PlotablePlus(name, plotablePoint,JoinType.bullet);
		nameToPlotablePlus.put(name, plotablePlus);
//
	}







    public void setColor(String name, Color color) {
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null)
            pp.color = color;
        else
            System.err.println("attention, il n'y a pas de PlotableMA du nom de "
                    + name);
    }

    public void setStroke(String name, Stroke stroke) {
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null) pp.stroke = stroke;
        else  System.err.println("attention, il n'y a pas de PlotableMA du nom de " + name);
    }


    public void setJoinType(String name,JoinType joinType,int defaultRadius){
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null) {
            pp.joinType = joinType;
            pp.defaultBulletRadius =defaultRadius;
        }
        else  System.err.println("attention, il n'y a pas de PlotableMA du nom de " + name);
    }

    public void setJoinType(String name,JoinType joinType){
        setJoinType(name,joinType,3);
    }




    public void setZIndex(String name,double zIndex) {
        PlotablePlus pp = nameToPlotablePlus.get(name);
        if (pp != null) pp.zIndex = zIndex;
        else  System.err.println("attention, il n'y a pas de PlotableMA du nom de " + name);
    }




    // empty constructor. Default values are defined above
    public MultiPlotNewMA() {
    }

    // must be called after the filling of parameters
    public void plotNow() {

        if (nameToPlotablePlus.size() == 0) {
            System.err.println("no plotable");
        } else {
            if (computeRectangle)
                computeDefaultRectangle();
            int i = 0;
            for (PlotablePlus pp : nameToPlotablePlus.values()) {

                if (pp.color == null)
                    pp.color = defaultColors[i % defaultColors.length];

                i++;
            }

            new PlotFrame();
            new LegendFrame();
        }
    }

    private class PlotFrame extends JFrame {

        public PlotFrame() {

            setLocation(0, 0);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(dimFenetre.width, dimFenetre.height);
            setBackground(Color.white);

            setContentPane(new PlotPanel());
            setVisible(true);
        }
    }

    private class LegendFrame extends JFrame {

        public LegendFrame() {

            setSize(200, 20 * (nameToPlotablePlus.size() + 1) + 30);
            setLocation(dimFenetre.width, 0);
            setBackground(Color.white);
            setContentPane(new LegendPanel());
            setVisible(true);
        }
    }

    private class LegendPanel extends JPanel {

        public void paintComponent(Graphics g1) {

            // creates a copy of the Graphics instance, with more options, like
            // the stroke
            Graphics2D g = (Graphics2D) g1.create();

            if (title!=null) g.drawString(title, 10, 20);

            int i = 1;
            for (PlotablePlus pp : nameToPlotablePlus.values()) {
                g.setColor(pp.color);
                g.setStroke(pp.stroke);

                g.drawLine(0, 20 + 20 * i, 40, 20 + 20 * i);
                g.drawString(pp.name, 43, 20 + 20 * i);
                i++;
            }

        }
    }

    public void imposeRectangle(double Xmin, double Ymin, double Xmax,
                                double Ymax) {

        rectangle[0] = Xmin;
        rectangle[1] = Ymin;
        rectangle[2] = Xmax;
        rectangle[3] = Ymax;
        computeRectangle = false;
    }

    private void computeDefaultRectangle() {

        ArrayList<Double> lesMinX = new ArrayList<Double>();
        ArrayList<Double> lesMaxX = new ArrayList<Double>();
        ArrayList<Double> lesMinY = new ArrayList<Double>();
        ArrayList<Double> lesMaxY = new ArrayList<Double>();

        for (PlotablePlus pp : nameToPlotablePlus.values()) {

            lesMinX.add(minimum(pp.plotable.getXs()));
            lesMaxX.add(maximum(pp.plotable.getXs()));
            lesMinY.add(minimum(pp.plotable.getYs()));
            lesMaxY.add(maximum(pp.plotable.getYs()));
        }

        double maxX = maximum(lesMaxX);
        double minX = minimum(lesMinX);
        double maxY = maximum(lesMaxY);
        double minY = minimum(lesMinY);

        rectangle = new double[4];
        rectangle[0] = minX;
        rectangle[1] = minY;
        rectangle[2] = maxX;
        rectangle[3] = maxY;
    }

    public int[][] convertiToPix(Dimension dimPanneau, PlotableMA fonc) {

        dimUsefull = new Dimension(dimPanneau.width - marge.width * 2,
                dimPanneau.height - marge.height * 2);
        int[][] sortie = new int[2][fonc.getXs().size()];

        double amplitudeX = rectangle[2] - rectangle[0];
        double amplitudeY = rectangle[3] - rectangle[1];

        for (int i = 0; i < fonc.getXs().size(); i++) {
            sortie[0][i] = (int) ((fonc.getXs().get(i) - rectangle[0])
                    / amplitudeX * dimUsefull.width)
                    + marge.width;
            sortie[1][i] = dimPanneau.height
                    - (int) ((fonc.getYs().get(i) - rectangle[1]) / amplitudeY * dimUsefull.height)
                    - marge.height;
        }
        return (sortie);
    }

    private class PlotPanel extends JPanel {

        public void paintComponent(Graphics g1) {

            // creates a copy of the Graphics instance, with more options, like
            // the stroke
            Graphics2D g = (Graphics2D) g1.create();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            ArrayList<PlotablePlus> sortedPlotablePlus=new ArrayList<PlotablePlus>();
            for (PlotablePlus plotablePlus:nameToPlotablePlus.values()) sortedPlotablePlus.add(plotablePlus);
            Collections.sort(sortedPlotablePlus);

            for (PlotablePlus pp : sortedPlotablePlus) {

                int[][] uneFoncPix;// = new int[2][pp.plotable.getXs().size()];
                uneFoncPix = convertiToPix(new Dimension(getWidth(), getHeight()), pp.plotable);




                for (int i = 0; i < uneFoncPix[0].length; i++) {

                    /** on met des couleurs variantes, si le {@link MultiPlot.MultiPlot.PlotableMA} est un {@link MultiPlot.MultiPlot.PlotableColored}*/
                  /*  if (pp.normalizedColors != null) {
                        g.setColor(Color.getHSBColor(pp.normalizedColors.get(i),1,1));
                    } else g.setColor(pp.color);


                    /**on joint les points par les lignes bris�es*/
          /*          if (pp.joinType== JoinType.join || pp.joinType== JoinType.joinBullet || pp.joinType== JoinType.joinSquare ) {
                        if(i<uneFoncPix[0].length-1){
                            g.setStroke(pp.stroke);
                            g.drawLine(uneFoncPix[0][i], uneFoncPix[1][i], uneFoncPix[0][i+1], uneFoncPix[1][i+1]);
                        }

                    }

                    /**on rajoute des bullets ou des carr�s sur chaque points*/
          /*          if (pp.joinType == JoinType.bullet || pp.joinType == JoinType.joinBullet) {
                        int pointSize;

                        if (pp.normalisedBulletRadii != null) {
                            pointSize = (int) Math.floor(pp.normalisedBulletRadii.get(i));
                        }
                        else pointSize = pp.defaultBulletRadius;

                        g.fillArc(uneFoncPix[0][i]-pointSize/2, uneFoncPix[1][i]-pointSize/2, pointSize, pointSize, 0, 360);
                    }
                    else if (pp.joinType == JoinType.square || pp.joinType == JoinType.joinSquare) {
                        int pointSize;
                        if (pp.normalisedBulletRadii != null) {
                            pointSize = (int) Math.floor(pp.normalisedBulletRadii.get(i));
                        }
                        else pointSize = pp.defaultBulletRadius;

                        g.fillRect(uneFoncPix[0][i]-pointSize/2, uneFoncPix[1][i]-pointSize/2, pointSize, pointSize);
                    }


                }


            }

            g.setColor(Color.black);
            g.setStroke(normal);

            g.drawRect(marge.width, marge.height, dimUsefull.width,
                    dimUsefull.height);

            // to clear an eventual line outside the zone
            g.setColor(Color.white);
            g.fillRect(0, 0, dimUsefull.width+2*marge.width, marge.height);
            g.fillRect(0, marge.height+dimUsefull.height+1, dimUsefull.width+2*marge.width, marge.height);
            g.fillRect(marge.width+dimUsefull.width+1, 0, marge.width, 2*marge.height+dimUsefull.height);

            if (title!=null) g.drawString(title, 10, 10);



            if (nbGraduationX > 0)
                rajouteLesGraduationsX(g);
            if (nbGraduationY > 0)
                rajouteLesGraduationsY(g);

        }
    }

    private double maximum(List<Double> tab) {

        double theMax = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMax < tab.get(i)) {
                theMax = tab.get(i);
            }
        }
        return (theMax);
    }

    private double minimum(List<Double> tab) {

        double theMin = tab.get(0);
        for (int i = 1; i < tab.size(); i++) {
            if (theMin > tab.get(i)) {
                theMin = tab.get(i);
            }
        }
        return (theMin);
    }

    private void rajouteLesGraduationsX(Graphics g) {
        // graduations on x-axis
        g.setColor(Color.black);
        for (int i = 0; i < nbGraduationX + 1; i++) {
            int x = marge.width + (dimUsefull.width / nbGraduationX * i);
            int y = marge.height + dimUsefull.height;
            g.drawLine(x, y, x, y + 5);
            DecimalFormat format = new DecimalFormat("#0.0");
            String texte = format.format(rectangle[0]
                    + (rectangle[2] - rectangle[0]) / nbGraduationX * i);
            Dimension dimString = new Dimension((int) g.getFontMetrics()
                    .getStringBounds(texte, g).getWidth(), (int) g
                    .getFontMetrics().getStringBounds(texte, g).getHeight());
            g.drawString(texte, x - dimString.width / 2, y + dimString.height
                    + 5);
        }
    }

    private void rajouteLesGraduationsY(Graphics g) {
        // graduations on y-axis
        g.setColor(Color.black);
        for (int i = 0; i < nbGraduationY + 1; i++) {
            int x = marge.width;
            int y = 2
                    * marge.height
                    + dimUsefull.height
                    - (int) (marge.height + dimUsefull.height / nbGraduationY
                    * i);
            g.drawLine(x, y, x - 5, y);
            DecimalFormat format = new DecimalFormat("#0.0");
            String texte = format.format(rectangle[1]
                    + (rectangle[3] - rectangle[1]) / nbGraduationY * i);
            Dimension dimString = new Dimension((int) g.getFontMetrics()
                    .getStringBounds(texte, g).getWidth(), (int) g
                    .getFontMetrics().getStringBounds(texte, g).getHeight());
            g.drawString(texte, x - dimString.width - 5, y + dimString.height
                    / 2 - 3);
        }

    }

    public static void main(String[] args) {

        MultiPlotNewMA multi = new MultiPlotNewMA();

        String testType="plotablePoint";

        if(testType.equals("plotable")){
            multi.nbGraduationX=8;
           // multi.addPlotable("Gauss", new CourbeDeGauss(100, -2, 2));
           // multi.addPlotable("Identit�", new FonctionId(100, -2, 2));
            multi.addPlotable("Constante", new FonctionConstante(20,-2,2,0.5));
            multi.addPlotable("AutreConstante", new FonctionConstante(20,-2,2,0.1));


            // Maintenant on peut changer les couleurs
            multi.setColor("Gauss", Color.red);
            multi.setColor("Identit�", Color.blue);

            // on peut changer le style de trait.
            multi.setStroke("Gauss", MultiPlotNewMA.dashed);
            multi.setStroke("Identit�", MultiPlotNewMA.thick);

            //  ne pas joindre les points
            multi.setJoinType("Constante",JoinType.bullet,10);
            multi.setColor("Constante",Color.magenta);

            // joindre les points et mettre des bullet
            multi.setJoinType("AutreConstante",JoinType.joinBullet,10);
            multi.setStroke("AutreConstante", MultiPlotNewMA.thick);
            // on pr�cise une hauteur, pour faire passer cette courbe au-dessus
            multi.setZIndex("AutreConstante",10);



            // et l'on peut imposer un rectangle
            multi.imposeRectangle(-2, -1, 1, 1);

            //quand tous les param�tres sont fix�, on peut tracer
            multi.plotNow();
        }
        else  if(testType.equals("plotableColors")){
            multi.addPlotable("Identit� color�", new IdentityColored());
            multi.plotNow();
        }
        else  if(testType.equals("plotablePoint")){
            multi.addPlotable("identit� point�", new IdentityPointed(1));

            multi.addPlotable("- identit� point�", new IdentityPointed(-1));
            multi.setJoinType("- identit� point�", JoinType.joinSquare);


            multi.plotNow();
        }



    }





    /** you can create other strocke if you want */
 /*   public static Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
    public static Stroke normal = new BasicStroke(1);
    public static Stroke thick = new BasicStroke(3);


    private class PlotablePlus implements Comparable<PlotablePlus>{


        private Double zIndex=0.;

        public String name;

        public PlotableMA plotable;

        private ArrayList<Integer> normalisedBulletRadii =null;
        private ArrayList<Float> normalizedColors=null;

        public Stroke stroke = normal;
        public Color color = null;

        public JoinType joinType=null;

        public int defaultBulletRadius =30;
        public int defaultRadius =30;
        public int maxBulletRadius =30;


        public PlotablePlus(String aName, PlotableMA aPlotable) {

            if (aPlotable instanceof PlotablePoint ) {
                PlotablePoint plotablePoint=(PlotablePoint) aPlotable;

                if (joinType==null) this.joinType= JoinType.bullet;

                double maxRad=-Double.MAX_VALUE;
                double minRad=Double.MAX_VALUE;

                for (Double r:plotablePoint.radius()){
                    if (r>maxRad) maxRad=r;
                    if (r<minRad) minRad=r;
                }

                boolean allEqual=false;
                if (maxRad-minRad<Double.MIN_VALUE*100) allEqual=true;


                normalisedBulletRadii =new ArrayList<Integer>();
                for (Double r:plotablePoint.radius()){
                    int norR;
                    if (allEqual) norR=3;
                    else norR= (int) Math.floor( (r-minRad)/(maxRad-minRad)*this.maxBulletRadius);
                    if (norR<3) norR=3;
                    this.normalisedBulletRadii.add(norR);

                }



            }

            if (aPlotable instanceof PlotableColored) {
                //if (joinType==null) this.joinType= JoinType.bullet;

                PlotableColored plotableColored=(PlotableColored) aPlotable;

                if (joinType==null) this.joinType= JoinType.join;


                double maxRad=-Double.MAX_VALUE;
                double minRad=Double.MAX_VALUE;

                for (Double r:plotableColored.colors()){
                    if (r>maxRad) maxRad=r;
                    if (r<minRad) minRad=r;
                }

                boolean allEqual=false;
                if (maxRad-minRad<Double.MIN_VALUE*100) allEqual=true;


                normalizedColors =new ArrayList<Float>();
                for (Double r:plotableColored.colors()){
                    float norC;
                    if (allEqual) norC=1;
                    else norC= (float) ((r-minRad)/(maxRad-minRad));
                    this.normalizedColors.add(norC);
                }



            }
            else{
                if (joinType==null) this.joinType= JoinType.join;
            }

            name = aName;
            plotable = aPlotable;
        }


        public int compareTo(PlotablePlus o) {
            return this.zIndex.compareTo(o.zIndex);
        }
    }

}
*/