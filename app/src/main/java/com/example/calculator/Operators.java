package com.example.calculator;

import java.util.function.BinaryOperator;

public enum Operators {

    divide((a, b) -> a / b),
    multiply((a, b) -> a * b),
    subtract((a, b) -> a - b),
    addition((a, b) -> a + b);

    private final BinaryOperator<Double> operation;

    private Operators(BinaryOperator<Double> operation) {
        this.operation = operation;
    }

    public BinaryOperator<Double> getOperator() {
        return this.operation;
    }
}
