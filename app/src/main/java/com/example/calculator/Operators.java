package com.example.calculator;

import java.util.function.BinaryOperator;

public enum Operators {

    division((a, b) -> a / b),
    multiplication((a, b) -> a * b),
    subtraction((a, b) -> a - b),
    addition((a, b) -> a + b);

    private final BinaryOperator<Double> operation;

    private Operators(BinaryOperator<Double> operation) {
        this.operation = operation;
    }

    public BinaryOperator<Double> getOperator() {
        return this.operation;
    }
}
