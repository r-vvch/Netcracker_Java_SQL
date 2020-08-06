package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        System.out.println("set-get test");
        ArrayVector array = new ArrayVectorImpl();
        array.set(1.0, 3.0, 2.0);
        System.out.println(Arrays.toString(array.get()));

        System.out.println();
        System.out.println("clone test");
        ArrayVector newArray = new ArrayVectorImpl();
        newArray = array.clone();
        System.out.println(Arrays.toString(newArray.get()));

        System.out.println();
        System.out.println("getSize test");
        System.out.println(newArray.getSize());

        System.out.println();
        System.out.println("set test");
        System.out.print("src newArray: ");
        System.out.println(Arrays.toString(newArray.get()));
        newArray.set(2, 4.0);
        System.out.print("setted newArray: ");
        System.out.println(Arrays.toString(newArray.get()));

        System.out.println();
        System.out.println("get index test");
        System.out.println(Arrays.toString(newArray.get()));
        System.out.println(array.get(0));
//        System.out.println(array.get(4));

        System.out.println();
        System.out.println("getMax-getMin test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        System.out.println(array.getMax());
        System.out.println(array.getMin());

        System.out.println();
        System.out.println("sortAscending test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        System.out.print("sorted array: ");
        array.sortAscending();
        System.out.println(Arrays.toString(array.get()));

        System.out.println();
        System.out.println("mult test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        array.mult(2);
        System.out.print("mult array: ");
        System.out.println(Arrays.toString(array.get()));

        System.out.println();
        System.out.println("sum test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        System.out.print("src newArray: ");
        System.out.println(Arrays.toString(newArray.get()));
        array.sum(newArray);
        System.out.print("sum array: ");
        System.out.println(Arrays.toString(array.get()));

        System.out.println();
        System.out.println("scalarMult test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        System.out.print("src newArray: ");
        System.out.println(Arrays.toString(newArray.get()));
        System.out.print("scalarMult: ");
        System.out.println(array.scalarMult(newArray));

        System.out.println();
        System.out.println("getNorm test");
        System.out.print("src array: ");
        System.out.println(Arrays.toString(array.get()));
        System.out.print("getNorm: ");
        System.out.println(array.getNorm());
    }
}
