package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Mutable object represented field state
 */
public class Field {
    /**
     * Matrix with colors of board cells
     */
    private final Color[][] matrix;

    /**
     * The side of the game board
     */
    private final static Integer size = 8;

    /**
     * Ctor...
     * with code...
     */
    public Field() {
        matrix = new Color[size][size];
        clear();
        setStartCells();
    }

    /**
     * Selects the cells in which is possible to make a move
     *
     * @param color color of target cell
     * @return list of evaluated cells
     */
    public List<Cell> getValuableCells(Color color) {
        List<Cell> possible = new ArrayList<>();
        for (Integer i = 0; i < size; i++) {
            for (Integer j = 0; j < size; j++) {
                Cell cell = new Cell(i, j);
                if (isValidCell(cell, color)) {
                    possible.add(cell);
                }
            }
        }
        return possible;
    }

    /**
     * Checks that a move can be made to the cell
     *
     * @param cell  target cell
     * @param color color of target cell
     * @return validation result
     */
    private Boolean isValidCell(Cell cell, Color color) {
        if (!cell.isValid(size) || !matrix[cell.row()][cell.column()].empty()) {
            return false;
        }
        return evaluateCell(cell, color) >= 1;
    }

    /**
     * Calculate value of cell
     *
     * @param cell  target cell
     * @param color color of target cell
     * @return value
     */
    private Double evaluateCell(Cell cell, Color color) {
        double value = 0.0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    int l = 1;
                    while (isCellColor(cell.row() + l * i,
                            cell.column() + l * j, color.other())) {
                        l++;
                    }
                    if (isCellColor(cell.row() + l * i,
                            cell.column() + l * j, color)) {
                        for (int k = 1; k < l; k++) {
                            if (new Cell(cell.row() + k * i,
                                    cell.column() + k * j).isEdge(size)) {
                                value += 2;
                            } else {
                                value += 1;
                            }
                        }
                    }
                }
            }
        }
        if (cell.isCorner(size)) {
            value += 0.8;
        } else if (cell.isEdge(size)) {
            value += 0.4;
        }
        cell.setValue(value);
        return value;
    }

    /**
     * Safely checks the color of the cell
     *
     * @param row    row of target cell
     * @param column column of target cell
     * @param color  color of target cell
     * @return check result
     */
    private Boolean isCellColor(Integer row, Integer column, Color color) {
        if (row < 0 || row >= size || column < 0 || column >= size) {
            return false;
        }
        return matrix[row][column].equals(color);
    }

    /**
     * Make move to target cell
     *
     * @param cell  target cell
     * @param color color of target cell
     * @throws IllegalArgumentException fail fast in case of a wrong move
     */
    public void move(Cell cell, Color color) throws IllegalArgumentException {
        if (!isValidCell(cell, color)) {
            throw new IllegalArgumentException("Invalid cell");
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    int l = 1;
                    while (isCellColor(cell.row() + l * i,
                            cell.column() + l * j, color.other())) {
                        l++;
                    }
                    if (isCellColor(cell.row() + l * i,
                            cell.column() + l * j, color)) {
                        for (int k = 1; k < l; k++) {
                            matrix[cell.row() + k * i][cell.column() + k * j] = color;
                        }
                    }
                }
            }
        }
        matrix[cell.row()][cell.column()] = color;
    }

    /**
     * Make field from empty cells
     */
    private void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = new Color(0);
            }
        }
    }

    /**
     * Add 4 start cells to field
     */
    private void setStartCells() {
        int center = size / 2;
        setCell(center - 1, center - 1, 2);
        setCell(center, center, 2);
        setCell(center - 1, center, 1);
        setCell(center, center - 1, 1);
    }

    /**
     * Encapsulation of cells setting
     *
     * @param row        row of target cell
     * @param column     column of target cell
     * @param colorValue value of color of target cell
     */
    private void setCell(Integer row, Integer column, Integer colorValue) {
        matrix[row][column] = new Color(colorValue);
    }

    /**
     * Field printing to the console
     */
    public void printWithSteps(List<Cell> steps) {
        char[] chars = new char[65];
        Arrays.fill(chars, '-');
        String divider = " \t" + new String(chars);
        System.out.print(" \t");
        for (int i = 0; i < size; i++) {
            System.out.print(" \t" + i + "\t");
        }
        System.out.println("\n" + divider);
        for (Integer i = 0; i < size; i++) {
            System.out.print(i + "\t");
            for (Integer j = 0; j < size; j++) {
                Cell cell = new Cell(i, j);
                if (steps.contains(cell)) {
                    System.out.print("|\t_\t");
                } else {
                    System.out.print("|\t" + matrix[i][j] + "\t");
                }
            }
            System.out.println("|\n" + divider);
        }
    }

    /**
     * Calculate number of cells with color
     *
     * @param color color of target cell
     * @return number of cells
     */
    public Integer calculate(Color color) {
        Integer count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].equals(color)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Make a deep copy of field
     *
     * @return new field
     */
    public Field copy() {
        Field field = new Field();
        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, field.matrix[i], 0, size);
        }
        return field;
    }

    /**
     * Update curr field
     *
     * @param field new field
     */
    public void update(Field field) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(field.matrix[i], 0, matrix[i], 0, size);
        }
    }
}
