package org.example;

import java.util.Objects;

/**
 * Representation of field cell
 */
public final class Cell {
    private final Integer row;
    private final Integer column;
    private Double value;

    public Cell(Integer row, Integer column) {
        this(row, column, 0.0);
    }

    public Cell(Integer row, Integer column, Double value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    private Boolean isValid(Integer parameter, Integer size) {
        return 0 <= parameter && parameter < size;
    }

    public Boolean isValid(Integer size) {
        return isValid(row, size) && isValid(column, size);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell other) {
            return Objects.equals(row, other.row()) && Objects.equals(column, other.column());
        }
        return false;
    }

    public Boolean isCorner(Integer size) {
        return isBorder(row, size) && isBorder(column, size);
    }

    public Boolean isEdge(Integer size) {
        return isBorder(row, size) || isBorder(column, size);
    }

    private Boolean isBorder(Integer side, Integer size) {
        return side == 0 || side == size - 1;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer row() {
        return row;
    }

    public Integer column() {
        return column;
    }

    public Double value() {
        return value;
    }
}
