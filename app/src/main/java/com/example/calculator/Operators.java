package com.example.calculator;

import java.util.function.BinaryOperator;

public enum Operators {

    division(((a, b) -> a / b), "/"),
    multiplication(((a, b) -> a * b), "*"),
    subtraction(((a, b) -> a - b), "-"),    // TO FIX LATER
    addition(((a, b) -> a + b), "+"),
    rootsquare(((a, b) -> Math.sqrt(a + b)), "√");

    private final BinaryOperator<Double> operation;
    private final String symbol;

    private Operators(BinaryOperator<Double> operation, String symbol) {
        this.operation = operation;
        this.symbol = symbol;
    }

    public BinaryOperator<Double> getOperator() {
        return this.operation;
    }

    public String getSymbol() {
        return this.symbol;
    }

    // Returns operator based on the symbol. If no matches, addition is returned by default.
    public static Operators getOperatorBySymbol(String symbol) {
        for (Operators op : Operators.values())
            if (op.getSymbol().equals(symbol))
                return op;

        return Operators.addition;
    }
}
