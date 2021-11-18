package com.mastermind;

import java.util.*;
import static com.mastermind.Utility.*;

public class JeuMastermind implements Jeu {

    private int tentaives;
    private boolean succes;
    private int nbrSignePlus;
    private int nbrSigneMoins;
    private boolean testRun;
    private HashMap<String, ArrayList<Integer>> secret;

    public JeuMastermind() {
        testRun = false;
    }

    public JeuMastermind(TestEnum testEnum) {
        // On verifie si le mode de test est activé
        testRun = testEnum.equals(TestEnum.ACTIVER);
    }

    @Override
    public void jouer() {
        // On initialise les valeurs des attributs avant de commencer le jeu
        initialiserJeu();

        // On affiche les messages de bienvenue pour le joueur
        acceuillirJoueur();

        // On vérfie si on est en mode test pour afficher le code secret
        if (testRun) {
            printSecret();
        }

        // On entre dans une loop tant que l'utilisateur n'a pas réussi de deviner le code
        while (!succes) {
            // On initialise les signes, y compris on initialise le résultat pour une autre tentative
            initialiserSignes();

            // On récupère l'input de l'utilisateur
            HashMap<String, ArrayList<Integer>> input = obtenirInput();

            // On vérifie pour chaque chiffre présent dans l'input
            for (String chiffre : input.keySet()) {

                // Si le code secret contient le chiffre en question
                if (secret.containsKey(chiffre)) {

                    // On récupère les positions de ce chiffre dans l'input et le secret
                    ArrayList<Integer> positionsDansInput = input.get(chiffre);
                    ArrayList<Integer> positionsDansSecret = secret.get(chiffre);

                    // On crée une liste pour marquer les positions matchées
                    ArrayList<Integer> positionsMatches = new ArrayList<>();

                    /* On calcul le nombre de matchs possibles,
                    c'est le min des deux nombres d'occurences pour ce chiffre
                   */
                    int nbrMatchPossibles = min(positionsDansInput.size(), positionsDansSecret.size());


                    for (int i = 0; i < nbrMatchPossibles; i++) {
                        boolean matchExact = false;
                        for (int pos : positionsDansInput) {
                            if (!positionsMatches.contains(pos) && positionsDansSecret.contains(pos)) {
                                matchExact = true;
                                // On marque la position comme matchée
                                positionsMatches.add(pos);
                                // Si on a un match exact on quitte la boucle
                                break;
                            }
                        }

                        // On rajoute des moins ou des plus selon si on a un match exact ou partiel
                        if (matchExact) {
                            nbrSignePlus++;
                        } else {
                            nbrSigneMoins++;
                        }
                    }
                }
            }
            // On affiche le résultat
            System.out.println("Resultat : " + genererResultat(nbrSignePlus, nbrSigneMoins));
        }

        // On affiche le messages de félicitation pour le joueur
        feliciterJoueur();
    }

    private void initialiserJeu() {
        this.tentaives = 0;
        this.succes = false;
        initialiserSignes();

        this.secret = this.obtenirSecret();
    }

    private void initialiserSignes() {
        this.nbrSigneMoins = 0;
        this.nbrSignePlus = 0;
    }

    private HashMap<String, ArrayList<Integer>> obtenirInput() {
        boolean done = false;
        boolean error = false;

        System.out.println("Tentative numero : " + ++this.tentaives);

        Scanner scanner = new Scanner(System.in);

        while (!done) {
            // Si une erreur s'est prdouite on affiche le message de la vérification
            if (error) {
                System.out.println("Veuillez verifier que vous entrez exactement 4 chiffres !");
                error = false;
            }

            System.out.print("Veuillez entrer 4 chiffres : ");
            try {
                String inputStr = scanner.next();
                // On vérifie qu'on a bien 4 chiffres
                if (inputStr.length() != 4) {
                    error = true;
                } else {
                    int input = Integer.parseInt(inputStr);
                    done = true;
                    return transformerIntEnHMap(input);
                }
                // On catch l'exception si le formattage est échoué
            } catch (NumberFormatException e) {
                error = true;
            }
        }
        return null;
    }

    public HashMap<String, ArrayList<Integer>> obtenirSecret() {
        int code = 0;
        for (int i = 0; i < 4; i++) {
            // On somme les chiffres générés entre 1 et 9 multipliés par 10 à la puissance @int i ( e.g. 1, 10, 100, ou 1000 )
            code += (int) ((new Random().nextInt(9) + 1) * Math.pow(10, i));
        }
        // On tranfrome ainsi le resultat en HashMap
        return transformerIntEnHMap(code);
    }


    public String genererResultat(int nbrPlus, int nbrMoins) {
        String resultat = "";

        if (nbrMoins+nbrPlus == 0){
            resultat = "Pas de match !";
        }else{

            // On concat les "+" à gauche avant les "-"
            while (nbrPlus > 0) {
                resultat = ("+").concat(resultat);
                nbrPlus--;
            }
            while (nbrMoins > 0) {
                resultat = resultat.concat("-");
                nbrMoins--;
            }

            // On vérifie si le joueur a réussi à deviner notre code
            if (resultat.equals("++++")) {
                this.succes = true;
            }
        }

        return resultat + "\n";
    }

    public void acceuillirJoueur() {
        System.out.println("|||||| Bonjour dans le jeu Mastermind ||||||");
        System.out.println("Vous devez dans ce jeu entrer 4 chiffres pour essayer de deviner notre code secret :)");
    }

    public void feliciterJoueur() {
        System.out.println("Bravoo, you got it !! Vous avez gagné après " + tentaives + " tentatives.");
    }

    public void printSecret() {
        String[] secretString = new String[4];
        for (String i : secret.keySet()) {
            for (int pos : secret.get(i)) {
                secretString[pos] = i;
            }
        }
        for (String s : secretString) {
            System.out.print(s);
        }
        System.out.print("\n");
    }

}
