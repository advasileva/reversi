package org.example;

import java.util.Objects;

/**
 * Representation of cell color
 * @param value Integer color value
 */
public record Color(Integer value) {

    public Color other() {
        if (value == 1) {
            return new Color(2);
        }
        return new Color(1);
    }

    public Boolean empty() {
        return value == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Color other) {
            return Objects.equals(value, other.value());
        }
        return false;
    }

    @Override
    public String toString() {
        if (value == 0) {
            return "";
        }
        return value.toString();
    }
}
