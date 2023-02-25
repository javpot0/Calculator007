public class Calculator {

    public static double operate(double a, double b, String operation) {
        return Operators.valueOf(operation).getOperator().apply(a, b);
    }

    // another rootsquare option. See in Operators class.
    public static double root(double a) {
        return Math.sqrt(a);
    }
}
