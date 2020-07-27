package ru.skillbench.tasks.basics.control;

public class Test {
    public static void main(String[] args){
        ControlFlowStatements1 object = new ControlFlowStatements1Impl();
        System.out.println(object.decodeWeekday(3));
        System.out.println(object.getFunctionValue(-4));
        int[][] arr = object.initArray();
        System.out.println(object.getMinValue(arr));
        System.out.println(object.calculateBankDeposit(4));
    }
}
