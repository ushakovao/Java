package tpC;


import java.util.*;

public class Classifier {

    /**une enumération est une liste de constante. C'est toujours un champs static (donc pas besoin d'écrire static)*/
    public  enum Category{bad,good}


    /**les données de la classe. En private car on veut que les utilisateur passe par des setter et getter*/
    private HashMap<String,HashMap<Category,Integer>> featureCapCategoryCount =  new HashMap<String,HashMap<Category,Integer>>();  // En java 8 : new HashMap<>();


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


    public int nbCategory(Category category){
        int res=0;
        for (HashMap<Category,Integer> values: this.featureCapCategoryCount.values()){
            if (values.containsKey(category)) res+=values.get(category);
        }
        return res;
    }

    public int nbTotal(){
        int res=0;
        for (HashMap<Category,Integer> values: this.featureCapCategoryCount.values()){
            for (Integer nb:values.values()) res+=nb;
        }
        return res;
    }



    /**les méthodes suivantes calcules les probabilités.
     * QUESTION: a quoi sert le (double) */
    public double probaCategory(Category category){
        return (double) this.nbCategory(category)/this.nbTotal();  //convert to double
    }

    /** P[feature/category]*/
    public double probaFeatureGivenCategory(String feature, Category category){
        return (double) this.nbFeatureCapCategory(feature,category) /this.nbCategory(category);
    }

    /** wP[feature/category]
     * QUESTION: pourquoi le (double) est-il en gris cette fois-ci ? */
    public double weiProbaFeatureGivenCategory(String feature, Category category){
        int nbFeature=this.nbFeature(feature);
        return (double) (this.probaFeatureGivenCategory(feature,category)*nbFeature+1)/(nbFeature+2); //deja faite double avant
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
        return "Classifier{" +
                "nbFeatureCapCategory=" + featureCapCategoryCount +
                '}';
    }
}