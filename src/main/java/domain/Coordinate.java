package domain;

import java.util.Objects;

public class Coordinate {
    private final int intersectionIndex1;
    private final int intersectionIndex2;

    public Coordinate(int index1, int index2) {
        this.intersectionIndex1 = index1;
        this.intersectionIndex2 = index2;
    }

    public int getIndex1() {
        return intersectionIndex1;
    }

    public int getIndex2() {
        return intersectionIndex2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return (intersectionIndex1 == that.intersectionIndex1 && intersectionIndex2 == that.intersectionIndex2) ||
                (intersectionIndex1 == that.intersectionIndex2 && intersectionIndex2 == that.intersectionIndex1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(intersectionIndex1, intersectionIndex2), Math.max(intersectionIndex1, intersectionIndex2));
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "index1=" + intersectionIndex1 +
                ", index2=" + intersectionIndex2 +
                '}';
    }
}