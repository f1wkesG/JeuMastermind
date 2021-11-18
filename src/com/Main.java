package com;

import com.mastermind.Jeu;
import com.mastermind.JeuMastermind;
import com.mastermind.TestEnum;

public class Main {

    public static void main(String[] args) {

        // Lancer le jeu en mode normal
        Jeu jeuMastermind = new JeuMastermind();
        jeuMastermind.jouer();

        // Lancer le jeu en mode test
        // Jeu jeuMastermind = new JeuMastermind(TestEnum.ACTIVER);
        // jeuMastermind.jouer();

    }


}
