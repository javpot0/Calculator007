package com.example.calculator;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_SELECTION = -1;
    public static final double DEFAULT_VALUE = 0;
    public static final double DIVIDEBY = 1;
    public static final double PERCENTAGE = 100;

    // Button references
    private ArrayList<Button> buttons_numbers;
    private ArrayList<Button> buttons_operators;
    private ArrayList<Button> buttons_quickOps;
    private ArrayList<Button> buttons_others;

    // TextView for input/output
    private TextView input;
    private TextView output;

    // Internal variables keeping track of current inputs
    private double firstNumber;
    private double secondNumber;
    private int selectedOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);

        // Instantiating lists containing buttons
        this.buttons_numbers = new ArrayList<Button>();
        this.addButtonsToList(Tools.BTN_NUMBERS_IDS, this.buttons_numbers);

        this.buttons_operators = new ArrayList<Button>();
        this.addButtonsToList(Tools.BTN_OPERATORS_IDS, this.buttons_operators);

        this.buttons_quickOps = new ArrayList<Button>();
        this.addButtonsToList(Tools.BTN_QUICKOPS_IDS, this.buttons_quickOps);

        this.buttons_others = new ArrayList<Button>();
        this.addButtonsToList(Tools.BTN_OTHERS_IDS, this.buttons_others);

        this.firstNumber = this.secondNumber = DEFAULT_VALUE;
        this.selectedOperator = DEFAULT_SELECTION;

        // Affecting a behavior to all buttons
        this.setListeners();
    }

    // Adding button references to the specified ArrayList by Id
    private void addButtonsToList(int[] ids, ArrayList<Button> list) {
        Button button = null;

        for (int id : ids) {
            button = findViewById(id);
            list.add(findViewById(id));
        }
    }

    // Adding the proper behavior to specific buttons
    private void setListeners() {
        for (Button button : this.buttons_numbers)
            button.setOnClickListener(onclick -> { this.setInputText(true, button.getText().toString()); });

        for (Button button : this.buttons_operators)
            button.setOnClickListener(onclick -> { this.setSelectedOperator(button); });

        for (Button button : this.buttons_quickOps)
            button.setOnClickListener(onclick -> { this.setQuickOps(button); });
    }

    // INPUT/OUTPUT

    // Registering inputs
    private void parseInputs() {
        String[] input = Tools.OPERATOR_SYMBOLS.split(this.input.getText());

        this.firstNumber = input.length > 0 ? Double.parseDouble(input[0]) : DEFAULT_VALUE;
        this.secondNumber = input.length > 1 ? Double.parseDouble(input[1]) : DEFAULT_VALUE;

        for (Button button : this.buttons_operators)
            if (button.getBackgroundTintList().equals(Tools.getColor(getColor(R.color.custom_graySelected))))
                this.selectedOperator = this.buttons_operators.indexOf(button);
    }

    // Behavior for number buttons, backspace, ce, c(to be implemented)
    private void setInputText(boolean concatenating, String text) {
        String current = Tools.getText(this.input);

        if (!concatenating || current.equals(String.valueOf(DEFAULT_VALUE)))
            this.input.setText(text);
        else
            this.input.setText(Tools.concat(current, text));

        this.updateInputDisplay();
    }

    // Updates input textview after parsing numbers and operator
    private void updateInputDisplay() {
        String result = "";

        this.parseInputs();
        if (this.isUnaryOperable())
            result += Tools.formatNumber(this.firstNumber);
        if (this.selectedOperator != DEFAULT_SELECTION)
            result += this.buttons_operators.get(this.selectedOperator).getText();
        if (this.isBinaryOperable())
            result += Tools.formatNumber(this.secondNumber);

        this.input.setText(!result.isEmpty() ? result : String.valueOf(DEFAULT_VALUE));
    }

    // Behavior for output results
    private void setOutputDisplay(String text) {
        this.output.setText(this.input.getText());

    }

    // BUTTONS

    // Behavior for operator buttons
    private void setSelectedOperator(Button operator) {
        ColorStateList selected = Tools.getColor(getColor(R.color.custom_graySelected)),
                deselected = Tools.getColor(getColor(R.color.custom_gray));
        int currentSelection = this.buttons_operators.indexOf(operator);

        if (this.isUnaryOperable()) {
            if (this.selectedOperator == currentSelection) {
                Tools.setColor(operator, deselected);
                this.selectedOperator = DEFAULT_SELECTION;
            }
            else {
                if (this.selectedOperator != DEFAULT_SELECTION)
                    Tools.setColor(this.buttons_operators.get(this.selectedOperator), deselected);

                Tools.setColor(operator, selected);
                this.selectedOperator = currentSelection;
            }

            this.updateInputDisplay();
        }
    }

    // Behavior for quick operation buttons - **Consider optimizing code readability/atomize into methods
    private void setQuickOps(Button quickOp) {
        int id = quickOp.getId();
        String operation = "";
        double a, b, result;

        a = b = result = DEFAULT_VALUE;
        if (this.isUnaryOperable()) {
            switch (id) {
            case R.id.button_squareRoot:
                if (this.isBinaryOperable()) {
                    a = Calculator.operate(this.firstNumber, this.secondNumber,
                            Tools.getText(this.buttons_operators.get(this.selectedOperator)));
                    this.reset();
                    this.firstNumber = a;
                }

                a = this.firstNumber;
                operation = Tools.getText(quickOp);
                this.output.setText(Tools.concat(Tools.getText(quickOp), Tools.formatNumber(this.firstNumber)));
            break;
            default:
                if (id != R.id.button_minus) {
                    a = id == R.id.button_percentage ? this.firstNumber : DIVIDEBY;
                    b = id == R.id.button_percentage ? PERCENTAGE : this.firstNumber;
                    operation = Tools.getText(findViewById(R.id.button_division));
                    this.output.setText(Tools.concat(
                            Tools.formatNumber(this.firstNumber), operation, Tools.formatNumber(this.secondNumber)));
                }
                else
                    this.firstNumber = -this.firstNumber;
            }

            if (id != R.id.button_minus) {
                this.reset();
                this.firstNumber = Calculator.operate(a, b, operation);
            }
        }

        if (this.isUnaryOperable()) {
            if (id == R.id.button_squareRoot) {
                this.squareroot();
                this.output.setText(Tools.concat(Tools.getText(quickOp), Tools.formatNumber(this.firstNumber)));
                a = this.firstNumber;
                operation = Tools.getText(quickOp);
            } else {
                if (id == R.id.button_minus && !this.isBinaryOperable())
                    this.firstNumber = -this.firstNumber;
                else
                    this.quickDivides(id == R.id.button_percentage);
            }


        }
    }

    private void squareroot() {
        double temp = DEFAULT_VALUE;
        String nb1 = Tools.formatNumber(this.firstNumber), nb2 = Tools.formatNumber(this.secondNumber),
            root = Operators.rootsquare.getSymbol();

        if (this.isBinaryOperable()) {
            this.output.setText(Tools.concat(root, "(", nb1,
                    Operators.valueOf(this.getCurrentOperator()).getSymbol(), nb2, ")"));
            temp = Calculator.operate(this.firstNumber, this.secondNumber, this.getCurrentOperator());
            this.reset();
            this.firstNumber = temp;
        } else
            this.output.setText(Tools.concat(root, nb1));

        temp = Calculator.operate(this.firstNumber, this.secondNumber, findViewById(R.id.button_squareRoot));
        this.setInputText(false, Tools.formatNumber(this.firstNumber));

    }

    // TODO
    private void quickDivides(boolean percentageSelected) {
        double a, b;
        Button divide = findViewById(R.id.button_division);

        a = b = DEFAULT_VALUE;
        a = percentageSelected ? this.firstNumber : DIVIDEBY;
        b = percentageSelected ? PERCENTAGE : this.firstNumber;

        this.selectedOperator = this.buttons_operators.indexOf(divide);
        this.output.setText(Tools.concat(Tools.formatNumber(a), this.getCurrentOperator(), Tools.formatNumber(b)));
        this.reset();
        this.firstNumber = Calculator.operate(a, b, divide);
    }

    // MISC.

    // Behavior for misc & other buttons
    private void setOthers(Button other) {
        // TO DO ******
    }

    // Sets the current numbers and operator back to default values.
    private void reset() {
        this.buttons_operators.get(this.selectedOperator).setBackgroundTintList(
                Tools.getColor(getColor(R.color.custom_gray)));
        this.firstNumber = this.secondNumber = DEFAULT_VALUE;
        this.selectedOperator = DEFAULT_SELECTION;
    }

    private String getCurrentOperator() {
        if (this.selectedOperator != DEFAULT_SELECTION)
            return Tools.getText(this.buttons_operators.get(this.selectedOperator));

        return "";
    }

    // Returns the name of the element with the id in parameter
    private String getNameById(int id) {
        return getResources().getResourceEntryName(id);
    }

    // Returns true if the state of current input is valid for an unary operation
    private boolean isUnaryOperable() {
        return this.firstNumber != DEFAULT_VALUE;
    }

    // Returns true if the state of current inputs are valid for a binary operation
    private boolean isBinaryOperable() {
        return this.isUnaryOperable() && this.secondNumber != DEFAULT_VALUE;
    }

}