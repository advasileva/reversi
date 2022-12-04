package org.example;

import java.util.List;

/**
 * Computer analyzing opponent's moves
 */
public class SmartComputer extends Computer {
    @Override
    public Cell selectCell(Field field, Color cellColor) {
        List<Cell> steps = field.getValuableCells(cellColor);
        Cell bestCell = steps.get(0);
        for (Cell cell: steps) {
            if (recursiveValue(field, cell, color) > recursiveValue(field, bestCell, color)) {
                bestCell = cell;
            }
        }
        return bestCell;
    }

    /**
     * Calculates cell value with analyzing opponent's moves
     * @param field curr field state
     * @param cell target cell
     * @param color color of target cell
     * @return calculated value
     */
    private Double recursiveValue(Field field, Cell cell, Color color) {
        Field tempField = field.copy();
        tempField.move(cell, color);
        return cell.value() - super.selectCell(tempField, color.other()).value();
    }
}
