package com.example.calculator;

public class Calculator {

    public static double operate(double a, double b, String operation) {
        return operation.equals("rootsquare") ? Math.sqrt(a) :
                Operators.valueOf(operation).getOperator().apply(a, b);
    }

    // another rootsquare option. See in com.example.calculator.Operators class.
    public static double root(double a) {
        return Math.sqrt(a);
    }
}
