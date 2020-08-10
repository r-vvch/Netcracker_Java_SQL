package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements2Impl implements ControlFlowStatements2 {

    public ControlFlowStatements2Impl() {
    }

    public int getFunctionValue(int x) {
        if (x < -2 | x > 2) {
            return 2 * x;
        } else {
            return -3 * x;
        }
    }

    public String decodeMark(int mark) {
        switch (mark) {
            case 1:
                return "Fail";
            case 2:
                return "Poor";
            case 3:
                return "Satisfactory";
            case 4:
                return "Good";
            case 5:
                return "Excellent";
            default:
                return "Error";
        }
    }

    public double[][] initArray() {
        double[][] newArray = new double[5][8];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                newArray[i][j] = Math.pow(i, 4) - Math.sqrt(j);
            }
        }
        return newArray;
    }

    public double getMaxValue(double[][] array) {
        double max = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] > max) {
                    max = array[i][j];
                }
            }
        }
        return max;
    }

    public Sportsman calculateSportsman(float P) {
        Sportsman runner = new Sportsman();
        int dayDistance = 10;
        while (runner.getTotalDistance() < 200) {
            dayDistance = (int) (dayDistance * (1 + 0.01 * P));
            runner.addDay(dayDistance);
        }
        return runner;
    }
}
