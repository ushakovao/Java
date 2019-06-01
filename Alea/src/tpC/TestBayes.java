package tpC;

import common.Tester;
import common.uu;

public class TestBayes {

    public static void main(String [] args){

        Tester tester=new Tester();

        test1(tester);
      //  test1(tester);

        uu.oo(tester.toString());

    }


    private static void test0(Tester tester){
        Classifier classifier=new Classifier();

        classifier.train("the boy", Classifier.Category.good);
        classifier.train("the cat", Classifier.Category.good);
        classifier.train("the boy and the cat", Classifier.Category.good);


        classifier.train("the casino", Classifier.Category.bad);
        classifier.train("the casino and the money", Classifier.Category.bad);


        tester.asserTrue(classifier.nbFeatureCapCategory("the", Classifier.Category.good)==4);
        tester.asserTrue(classifier.nbCategory( Classifier.Category.good)==9);
        tester.asserTrue(classifier.nbTotal( )==16);
        tester.asserTrue(classifier.nbFeature("the")==7);

        tester.asserTrue(Double.toString(classifier.probaDocumentGivenCategory("casino and money win money", Classifier.Category.good)).equals("0.004243827160493827"));
        tester.asserTrue(Double.toString(classifier.probaDocumentGivenCategory("casino and money win money", Classifier.Category.bad)).equals("0.009162848812994584"));


    }


    private static void test1(Tester tester){
        ClassifierDocument classifier=new ClassifierDocument();

        classifier.train("the boy", ClassifierDocument.Category.good);
        classifier.train("the cat", ClassifierDocument.Category.good);
        classifier.train("the boy and the cat", ClassifierDocument.Category.good);


        classifier.train("the casino", ClassifierDocument.Category.bad);
        classifier.train("the casino and the money", ClassifierDocument.Category.bad);

        uu.oo(classifier.toString());


        tester.asserTrue(classifier.nbFeatureCapCategory("the", ClassifierDocument.Category.good)==3);
        tester.asserTrue(classifier.nbDoc( ClassifierDocument.Category.good)==3);
        tester.asserTrue(classifier.nbTotal()==5);
        tester.asserTrue(classifier.nbFeature("the")==5);



        tester.asserTrue(Double.toString(classifier.probaDocumentGivenCategory("casino and money win money", ClassifierDocument.Category.good)).equals("0.01736111111111111"));
        tester.asserTrue(Double.toString(classifier.probaDocumentGivenCategory("casino and money win money", ClassifierDocument.Category.bad)).equals("0.09375"));


    }



    private static void test2(Tester tester){

        Classifier classifier=new Classifier();

        classifier.train("boy and cat win together", Classifier.Category.good);
        classifier.train("the boy gives the cat", Classifier.Category.good);
        classifier.train("the cat gives the boy", Classifier.Category.good);
        classifier.train("the cat eats the boy now", Classifier.Category.good);

        classifier.train("win money online now", Classifier.Category.bad);
        classifier.train("the online casino gives money", Classifier.Category.bad);
        classifier.train("win casino and money now", Classifier.Category.bad);
    }

}