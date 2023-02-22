import java.util.function.BinaryOperator;

public enum Operators {

    divide("/"),
    multiply("*"),
    subtract("-"),
    addition("+");

    private String symbol;

    private Operators(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public BinaryOperator<Double> getOperator() {
        switch (this.symbol) {
            case "/": return (a, b) -> a / b;
            case "*": return (a, b) -> a * b;
            case "-": return (a, b) -> a - b;
            default: return (a, b) -> a + b;
        }
    }
}
