package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ArrayVectorImpl implements ArrayVector {

    public ArrayVectorImpl() {
    }

    private double[] coordinates = null;

    public void set(double... elements) {
        coordinates = elements;
    }

    public double[] get() {
        return coordinates;
    }

    public ArrayVector clone() {
        double[] cloneCoordinates = new double[coordinates.length];
        System.arraycopy(coordinates, 0, cloneCoordinates, 0, coordinates.length);
        ArrayVector cloneArrayVector = new ArrayVectorImpl();
        cloneArrayVector.set(cloneCoordinates);
        return cloneArrayVector;
    }

    public int getSize() {
        return coordinates.length;
    }


    public void set(int index, double value) {
        if (index >= 0) {
            if (index >= getSize()) {
                double[] newCoordinates = new double[index + 1];
                System.arraycopy(coordinates, 0, newCoordinates, 0, coordinates.length);
                newCoordinates[index] = value;
                set(newCoordinates);
            } else {
                coordinates[index] = value;
            }
        }
    }

    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 | index > coordinates.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return coordinates[index];
    }


    public double getMax() {
        double max = coordinates[0];
        for (double e : coordinates) {
            if (e > max) {
                max = e;
            }
        }
        return max;
    }

    public double getMin() {
        double min = coordinates[0];
        for (double e : coordinates) {
            if (e < min) {
                min = e;
            }
        }
        return min;
    }

    public void sortAscending() {
        Arrays.sort(coordinates);
    }

    public void mult(double factor) {
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] *= factor;
        }
    }

    public ArrayVector sum(ArrayVector anotherVector) {
        int minLength = Math.min(getSize(), anotherVector.getSize());
        for (int i = 0; i < minLength; i++) {
            coordinates[i] += anotherVector.get(i);
        }
        return this;
    }

    public double scalarMult(ArrayVector anotherVector) {
        int minLength = Math.min(getSize(), anotherVector.getSize());
        double mult = 0.0;
        for (int i = 0; i < minLength; i++) {
            mult += coordinates[i] * anotherVector.get(i);
        }
        return mult;
    }

    public double getNorm() {
        return Math.sqrt(scalarMult(this));
    }

}
