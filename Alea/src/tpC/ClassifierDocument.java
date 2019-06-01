package tpC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by ushakova on 10/10/16.
 */
public class ClassifierDocument {


/**une enumération est une liste de constante. C'est toujours un champs static (donc pas besoin d'écrire static)*/
    public  enum Category{bad,good}


    /**les données de la classe. En private car on veut que les utilisateur passe par des setter et getter*/
    private HashMap<String,HashMap<Category,Integer>> featureCapCategoryCount =  new HashMap<String,HashMap<Category,Integer>>();  // En java 8 : new HashMap<>();


    //This HashMap contains documents and their category, i.e. "casino and money" - "bad"
    //Remarque: this HashMap can be given under the form HashMap<String,HashMap<Category,Integer>>, where Integer is binary or boolean.
    private HashMap<String,Category> docCapCategoryCount =  new HashMap<String,Category>();

    public ArrayList<String> extractFeatures(String document){
        String[] words=document.split(" ");

        ArrayList<String> ensembleOfWords=new ArrayList<String>();

        ensembleOfWords.addAll(Arrays.asList(words));
        /** ce qui est équivalent à:
         * for (String word : words) ensembleOfWords.add(word);
         * */
        return ensembleOfWords;
    }



    /**les méthodes suivantes sont des sortes de setter: il modifient nos données*/

    /**cette première méthode est privée car elle ne sera utilisé qu'en interne, par la méthode train*/
    private void incFeature(String f, Category category){

        if(!this.featureCapCategoryCount.containsKey(f)) this.featureCapCategoryCount.put(f,new HashMap<Category,Integer>());
        if (!this.featureCapCategoryCount.get(f).containsKey(category)) this.featureCapCategoryCount.get(f).put(category,0);

        /* En java 8, les 2 lignes précédentes donne :
        * this.nbFeatureCapCategory.putIfAbsent(f,new HashMap<>());
        this.nbFeatureCapCategory.get(f).putIfAbsent(category,0); */

        this.featureCapCategoryCount.get(f).put(category,this.featureCapCategoryCount.get(f).get(category)+1);

        /*  en java 8  (avec une petite amélioration des performances) :
            this.nbFeatureCapCategory.get(f).computeIfPresent(category,(k,v)->v+1);
         */

    }

    public void train(String item,Category category){
        for (String feature: this.extractFeatures(item)){
            this.incFeature(feature,category);
        }
    }


    //Method that helps to fill the table - if in a document DOC we meet feature FEATURE?
    public int isFeatureInDoc (String feature, String doc) {
        int res = 0;
        Collection<String> features = this.extractFeatures(doc);
        for (String featureX : features)
            if (featureX == feature) {
                res = 1;
                break;}
        return res;
    }







    /** les méthodes suivantes sont des sortes de getter: il facilitent l'extration des données */


    public int nbFeatureCapCategory(String feature, Category category){
        if (this.featureCapCategoryCount.containsKey(feature) && this.featureCapCategoryCount.get(feature).containsKey(category)) return this.featureCapCategoryCount.get(feature).get(category);
        else return 0;
    }


    public int nbFeature(String feature){
        int res=0;
        for (Category category:Category.values()) res+=this.nbFeatureCapCategory(feature,category);
        return  res;
    }
        //Nb of documents by Category
        //ATT: However, if "good" is in a Key-field, not in a Category-field, this method may lead to confusion
        //(i.e. "good casino"->"bad")
        //We can't put containsKey(category) as we are counting values that have this key
    public int nbDoc(Category category){
        int res=0;
        if (this.docCapCategoryCount.containsValue(category)) res+=1;
        return res;
    }

        //For both keys ("good" and "bad") we count number of values
    public int nbTotal(){
        int res=0;
        for (Category category:Category.values()) res+=nbDoc(category);
        return res;
    }




    /**les méthodes suivantes calcules les probabilités.
     * QUESTION: a quoi sert le (double) */
    public double probaCategory(Category category){
        return (double) this.nbDoc(category)/this.nbTotal();
    }

    /** P[feature/category]
     *  QUESTION: a quoi sert le (double) */
    public double probaFeatureGivenCategory(String feature, Category category){
        return (double) this.nbFeatureCapCategory(feature,category) /this.nbDoc(category); // (double) convertir en double
    }

    /** wP[feature/category]
     * QUESTION: pourquoi le (double) est-il en gris cette fois-ci ? */
    public double weiProbaFeatureGivenCategory(String feature, Category category){
        int nbFeature=this.nbFeature(feature);
        return (double) (this.probaFeatureGivenCategory(feature,category)*nbFeature+1)/(nbFeature+2); //déjà convertie en double avant
    }



    public double probaDocumentGivenCategory(String document, Category category){
        Collection<String> features=this.extractFeatures(document);
        double res=1.;
        for (String feature : features) res*=this.weiProbaFeatureGivenCategory(feature,category);
        return res;
    }


    public double probaCategoryGivenDocument(Category category, String document){
        return this.probaDocumentGivenCategory(document,category)*this.probaCategory(category);
    }

    @Override
    public String toString() {
        return "ClassifierDocument{" +
                "nbFeatureCapCategory=" + featureCapCategoryCount +
                '}';
    }



    public static void main(String [] args){

        ClassifierDocument classifier=new ClassifierDocument();
        System.out.println(classifier.isFeatureInDoc("the", "the boy"));


    }
}