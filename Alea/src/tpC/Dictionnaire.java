package tpC;

import common.uu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Dictionnaire {


    public static void main(String[] args){


        hashMapStringString();
        uu.o("");
        uu.o("");
        hashMapStringInteger();
        uu.o("");
        uu.o("");
        hashSet();



        /** pour les étudiants qui n'éprouvent pas de difficulté jusque là regardez ceci en TP. Sinon faites les à la maison, c'est intéressant: */
        uu.o("");
        uu.o("");
        hashMapObject();

    }






    private static void hashMapStringString(){

        uu.o("===================HashMap: à chaque clef on associe une valeur. Les clefs ne peuvent être répétée (sinon écrasement de la valeur associée) ----------");

        HashMap<String,String> dictionnaire=new HashMap<String, String>();

        dictionnaire.put("java","Danse populaire parisienne, typique des bals musette");
        dictionnaire.put("tohu-bohu","Désordre et confusion");
        dictionnaire.put("capricieux","irrégulier, fantaisiste");

        uu.o("---------- la Case compte ------------");
        uu.oo(dictionnaire.get("java"));
        uu.oo(dictionnaire.get("Java"));

        uu.o("----------on écrase une entrée ------------");

        dictionnaire.put("java","mais non, c'est un langage informatique");
        uu.oo(dictionnaire.get("java"));

        uu.o("------------toutes les valeurs ----------");
        for (String value : dictionnaire.values()) uu.oo(value);
        uu.o("---------- toutes les clefs ------------");
        for (String key: dictionnaire.keySet()) uu.oo(key);

    }


    private static void hashMapStringInteger(){

        /**trouvez l'erreur dans le petit programme ci-dessous. Il devrait normalement renvoyer:
         *
         * {les=3, gars=3, bonjour=2}
         *
         * Pensez à analyser les sorties*/

        uu.o("==================créons un compteur de mot---------");

        HashMap<String,Integer> compteDesMots=new HashMap<String, Integer>();

        String phrase="bonjour bonjour les gars les gars les gars";

        String[] mots=phrase.split(" ");

        for (String word: mots){
            if (compteDesMots.get(word)==null) compteDesMots.put(word,1);
            else compteDesMots.put(word,compteDesMots.get(word)+1); //enlever ""
        }

        uu.oo(compteDesMots);


    }


    private static void hashSet(){

        uu.o("=======================hash set: un ensemble de clef, sans valeur associées---------");


        HashSet<Double> ensemble=new HashSet<Double>();
        //here are only 2 distinct keys
        ensemble.add(1.5);
        ensemble.add(1.5);
        ensemble.add(1.5);
        ensemble.add(Math.exp(2));
        ensemble.add(Math.exp(2));

        uu.oo(ensemble);
        uu.o("------------");
        for (double key : ensemble) uu.o(2*key);

    }






    private static void hashMapObject(){

        /**on peut faire des hash map avec n'importe quel objet. Mais, par défaut, c'est l'adresse mémoire qui sert de clef*/

        Bonhomme bonhomme1=new Bonhomme(30,"jean","dupont");
        Bonhomme bonhomme2=new Bonhomme(30,"jean","dupont");
        Bonhomme bonhomme3=new Bonhomme(40,"vincent","vigon");


        /**bonhomme1 et bonhomme2 sont "sémentiquement" identiques, mais ils n'ont pas la même adresse mémoire*/
        /** ces deux test répondent faux */
        uu.oo("bonhomme1==bonhomme2:"+(bonhomme1==bonhomme2));
        uu.oo("bonhomme1.equals(bonhomme2):"+(bonhomme1.equals(bonhomme2)));


        Bonhomme[] tous = new Bonhomme[]{bonhomme1,bonhomme2,bonhomme1,bonhomme3};

        {
            HashMap<Bonhomme, Integer> compteLesBonhomme = new HashMap<Bonhomme, Integer>();
            for (Bonhomme bonhomme : tous) {
                if (compteLesBonhomme.get(bonhomme) == null) compteLesBonhomme.put(bonhomme, 1);
                else compteLesBonhomme.put(bonhomme, compteLesBonhomme.get(bonhomme) + 1);
            }
            /**il y aura donc 3 clefs distinctes*/
            uu.oo(compteLesBonhomme);
        }



        {
            /**solution pour y remédier: basé les clefs sur l'objet transformé en string*/
            HashMap<String, Integer> compteLesBonhommeViatoString = new HashMap<String, Integer>();
            for (Bonhomme bonhomme : tous) {
                String bohommeAsString=bonhomme.toString();
                if (compteLesBonhommeViatoString.get(bohommeAsString) == null) compteLesBonhommeViatoString.put(bohommeAsString, 1);
                else compteLesBonhommeViatoString.put(bohommeAsString, compteLesBonhommeViatoString.get(bohommeAsString) + 1);
            }
            /**il n'y a maintenant plus que 2 clefs*/
            uu.oo(compteLesBonhommeViatoString);

            /**attention: pour que ce soit plus propre : changer le nom de toString en "toStringForHash",
             * car la méthode toString est censée changée tout le temps en fonction des besoins de debuggages,
             * tandis que votre méthode "toStringForHash" définira votre propre égalité sémantique entre objet*/
        }




        /**et pour faire encore mieux : on peut définir proprement l'égalité sémantique d'objet en java:
         * clique gauche sur la classe Bonhomme > Generate >  equals() and hashCode()
         * On vous demande de choisir à partir de quel champs on déterminera l'égalité sémantique: choisissez.
         * regardez rapidement ces nouvelles méthodes. Refaites tournez le programme :
         *
         *  En particulier

         uu.oo("bonhomme1==bonhomme2:"+(bonhomme1==bonhomme2));
         uu.oo("bonhomme1.equals(bonhomme2):"+(bonhomme1.equals(bonhomme2)));
         répondrons "false" et "true"

         et compteLesBonhomme n'aura plus que 2 clefs.
         *
         * */



    }






    private static class Bonhomme{
        public int age;
        public String nom;
        public String prenom;


        public Bonhomme(int age, String nom, String prenom) {
            this.age = age;
            this.nom = nom;
            this.prenom = prenom;
        }

        @Override
        public String toString() {
            return "Bonhomme{" +
                    "age=" + age +
                    ", nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    '}';
        }

    }







}