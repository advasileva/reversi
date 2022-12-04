package org.example;

import java.util.Scanner;

/**
 * Logic encapsulating a single game
 */
public class Game {
    /**
     * Current field state
     */
    private final Field field;

    /**
     * First player (user)
     */
    private Human first;

    /**
     * Second player (opponent)
     */
    private Player second;

    /**
     * Score of players from class Human
     */
    private Integer humanScore;

    /**
     * Ctor
     */
    public Game() {
        field = new Field();
        humanScore = 0;
    }

    /**
     * Starts a new game
     */
    public void start() {
        enterPlayers();
        System.out.println("~~~ GAME STARTED ~~~");
        Boolean isFirstMoved, isSecondMoved;
        do {
            System.out.println("~~~~ NEXT ROUND ~~~~");
            System.out.println("~~~ FIRST PLAYER ~~~");
            isFirstMoved = first.nextMove(field);
            System.out.println("~~ SECOND  PLAYER ~~");
            isSecondMoved = second.nextMove(field);
        } while (isFirstMoved || isSecondMoved);
        System.out.println("~~~~ GAME  OVER ~~~~");
        printResults();
    }

    /**
     * Enter players:
     * first - our user
     * second - depends on user
     */
    private void enterPlayers() {
        first = new Human();
        first.setColor(new Color(1));
        System.out.println("Choose the game mode:\n[1] - easy\n[2] - medium\n[3] - pvp");
        Scanner in = new Scanner(System.in);
        String input = in.next();
        while (!"1".equals(input) && !"2".equals(input) && !"3".equals(input)) {
            System.out.println("Please, enter '1', '2' or '3'");
            input = in.next();
        }
        if ("1".equals(input)) {
            second = new Computer();
        } else if ("2".equals(input)) {
            second = new SmartComputer();
        } else {
            second = new Human();
        }
        second.setColor(new Color(2));
    }

    /**
     * Prints the results of the game to the console
     */
    private void printResults() {
        Integer firstScore = first.calculate(field),
                secondScore = second.calculate(field);
        System.out.println("First score: " + firstScore);
        System.out.println("Second score: " + secondScore);
        if (firstScore > humanScore) {
            humanScore = firstScore;
        }
        if (second instanceof Human && secondScore > humanScore) {
            humanScore = secondScore;
        }
        System.out.println("Human score: " + humanScore);
    }

    /**
     * Getter
     * @return value
     */
    public Integer getHumanScore() {
        return humanScore;
    }
}
