package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements1Impl implements ControlFlowStatements1 {

    public ControlFlowStatements1Impl() {
    }

    public float getFunctionValue(float x) {
        if (x > 0) {
            return (float) (2 * Math.sin(x));
        } else {
            return 6 - x;
        }
    }

    public String decodeWeekday(int weekday) {
        switch (weekday) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Wrong input";
        }
    }

    public int[][] initArray() {
        int[][] newArray = new int [8][5];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                newArray[i][j] = i * j;
            }
        }
        return newArray;
    }

    public int getMinValue(int[][] array) {
        int min = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] < min) {
                    min = array[i][j];
                }
            }
        }
        return min;
    }

    public BankDeposit calculateBankDeposit(double P) {
        BankDeposit dep = new BankDeposit();
        dep.years = 0;
        dep.amount = 1000;
        while (dep.amount < 5000) {
            dep.years += 1;
            dep.amount = dep.amount * (1 + P * 0.01);
        }
        return dep;
    }

}

