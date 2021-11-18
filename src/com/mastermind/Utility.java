package com.mastermind;

import java.util.ArrayList;
import java.util.HashMap;

public class Utility {

    // Fonction qui transforme un entier en Hashmap
    public static HashMap<String, ArrayList<Integer>> transformerIntEnHMap(int input){
        // On transforme le chiffre en une chaine de caractères
        String inputString = String.valueOf(input);

        /* On crée une map avec des couples clé, valeur
            où les clés sont les chiffres
            et la valeur est l'ensmble des positions de chacun
        */
        HashMap<String, ArrayList<Integer>> result = new HashMap<>();
        for (int i = 0; i < inputString.length() ; i++){
            String ch = String.valueOf(inputString.charAt(i));
            ArrayList<Integer> positions = result.get(ch) == null ? new ArrayList<>() : result.get(ch);
            positions.add(i);
            result.put(String.valueOf(inputString.charAt(i)), positions);
        }
        return result;
    }

    // Une simple fonction qui retourne le min des deux entiers
    public static int min(int a, int b){
        return Math.min(a, b);
    }

}
