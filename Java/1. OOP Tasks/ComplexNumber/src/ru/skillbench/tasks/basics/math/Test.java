package ru.skillbench.tasks.basics.math;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        ComplexNumber comp = new ComplexNumberImpl(1.0, 2.0);

//Getters and setters
        System.out.println("getRe getIm isReal");
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp.isReal());
        System.out.println();

        System.out.println("set set String");
        comp.set(3.0, 4.0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println();
        comp.set("i");
        System.out.println(comp);
        System.out.println();

//Methods of java.lang.Object and similar methods
        System.out.println("copy");
        ComplexNumber newComp = comp.copy();
        System.out.println(newComp.getRe());
        System.out.println(newComp.getIm());
        System.out.println();

        System.out.println("clone");
        ComplexNumber superNewComp = comp.clone();
        System.out.println(superNewComp.getRe());
        System.out.println(superNewComp.getIm());
        System.out.println();

        System.out.println("toString");
        comp.set(12.5, 1.0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        comp.set(12.5, 0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        comp.set(0, 1.0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        comp.set(-12.5, -1.0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        comp.set(-12.5, 0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        comp.set(0, -1.0);
        System.out.println(comp.getRe());
        System.out.println(comp.getIm());
        System.out.println(comp);
        System.out.println();

        System.out.println("equals");
        System.out.println(newComp.equals(superNewComp));
        System.out.println();

//        Ordering methods
        System.out.println("compareTo");
        System.out.println(newComp.compareTo(superNewComp));
        System.out.println();

//        Mathematical operations
        comp.set("12-i");
        System.out.println("negate");
        System.out.println(comp.negate());
        System.out.println();

        newComp.set("-1+i");
        System.out.println("add");
        System.out.println(comp.add(newComp));
        System.out.println();

        comp.set("1+2i");
        newComp.set("3+4i");
        System.out.println("multiply");
        System.out.println(comp.multiply(newComp));

    }

}
