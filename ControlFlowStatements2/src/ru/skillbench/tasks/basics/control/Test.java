package ru.skillbench.tasks.basics.control;

public class Test {
    public static void main(String[] args){
        ControlFlowStatements2 object = new ControlFlowStatements2Impl();
        System.out.println(object.getFunctionValue(3));
        System.out.println(object.decodeMark(5));
        double[][] arr = object.initArray();
        System.out.println(object.getMaxValue(arr));
        System.out.println(object.calculateSportsman(12.5f));
        //12 p = 10,11,12,13
    }
}
