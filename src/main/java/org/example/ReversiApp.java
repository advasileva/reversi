package org.example;

import java.util.Scanner;

/**
 * Top-level logic of the application
 */
public class ReversiApp {
    public void execute() {
        int maxHumanScore = 0;
        System.out.println("Hey, I'm Reversi!");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to play? [y/n]");
            String input = in.next();
            while (!"y".equals(input) && !"n".equals(input)) {
                System.out.println("Please, enter 'y' or 'n'");
                input = in.next();
            }
            if ("y".equals(input)) {
                Game game = new Game();
                game.start();
                maxHumanScore = Math.max(maxHumanScore, game.getHumanScore());
            } else {
                break;
            }
        }
        System.out.println("Best human score: " + maxHumanScore);
        System.out.println("Bye!");
    }
}
