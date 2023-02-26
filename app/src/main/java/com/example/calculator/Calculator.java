package com.example.calculator;

import android.widget.Button;

public class Calculator {

    public static double operate(double a, double b, String operation) {
        return Operators.valueOf(operation).getOperator().apply(a, b);
    }

    public static double operate(double a, double b, Button operator) {
        return Operators.valueOf(Tools.getText(operator)).getOperator().apply(a, b);
    }

    // another rootsquare option. See in com.example.calculator.Operators class.
    public static double root(double a) {
        return Math.sqrt(a);
    }
}
