package org.example;

import java.util.List;

/**
 * Artificial unintelligence
 */
public class Computer extends Player {
    @Override
    public Boolean nextMove(Field field) {
        List<Cell> valuableCells = field.getValuableCells(color);
        if (valuableCells.isEmpty()) {
            System.out.println("Player can't move");
            return false;
        }
        Cell selectedCell = selectCell(field, color);
        field.move(selectedCell, color);
        return true;
    }

    /**
     * Selects more valuable cell
     * @param field curr field state
     * @param cellColor color of target cell
     * @return cell with best value
     */
    public Cell selectCell(Field field, Color cellColor) {
        List<Cell> steps = field.getValuableCells(cellColor);
        Cell bestCell = steps.get(0);
        for (Cell cell: steps) {
            if (cell.value() > bestCell.value()) {
                bestCell = cell;
            }
        }
        return bestCell;
    }
}
