package org.example;

/**
 * Abstract Reversi player
 */
public abstract class Player {
    /**
     * Game color
     */
    protected Color color;

    /**
     * Setter
     * @param color value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Make next move
     * @param field game field
     */
    public abstract Boolean nextMove(Field field);

    /**
     * Calculate number of cells player color
     * @param field game field
     * @return number of cells
     */
    public Integer calculate(Field field) {
        return field.calculate(color);
    }
}
