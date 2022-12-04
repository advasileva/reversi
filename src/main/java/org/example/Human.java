package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Perhaps thinking person
 */
public class Human extends Player {
    /**
     * Field backup
     */
    private final List<Field> history;

    /**
     * Ctor
     */
    public Human() {
        history = new ArrayList<>();
    }

    @Override
    public Boolean nextMove(Field field) {
        List<Cell> valuableCells = field.getValuableCells(color);
        if (valuableCells.isEmpty()) {
            System.out.println("Player can't move");
            return false;
        }
        int row, column;
        printSteps(valuableCells);
        boolean doRedo, doRepeat;
        do {
            try {
                do {
                    valuableCells = field.getValuableCells(color);
                    field.printWithSteps(valuableCells);
                    System.out.println("What will be your next move?");
                    Scanner in = new Scanner(System.in);
                    System.out.println("Enter row: (or -1 to redo last step)");
                    row = in.nextInt();
                    System.out.println("Enter column: (or -1 to redo last step)");
                    column = in.nextInt();
                    doRedo = column == -1 || row == -1;
                    if (doRedo) {
                        redo(field);
                    }
                } while (doRedo);
                history.add(field.copy());
                field.move(new Cell(row, column), color);
                doRepeat = false;
            } catch (IllegalArgumentException | InputMismatchException ex) {
                System.out.println("Wrong row or column, please repeat");
                doRepeat = true;
            }
        } while (doRepeat);
        return true;
    }

    /**
     * Print possible cells
     *
     * @param valuableCells cell for printing
     */
    private void printSteps(List<Cell> valuableCells) {
        System.out.println("Possible moves:");
        for (Cell cell : valuableCells) {
            System.out.println("Row: " + cell.row() + ", column: " + cell.column());
        }
    }

    /**
     * Redo a move on the field
     *
     * @param field target field
     */
    private void redo(Field field) {
        if (history.size() > 0) {
            field.update(history.remove(history.size() - 1));
        }
    }

}
