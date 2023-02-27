package com.example.calculator;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// ZI HENG LIU, ZAFER ACAR, JAVEN VERNET

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_SELECTION = -1; // Operator selection
    public static final double DEFAULT_VALUE = 0;   // Default input display
    public static final double DIVIDEBY = 1;    // The 1/X button
    public static final double PERCENTAGE = 100; // The % button
    public static final int MAX_INPUTS = 10; // The max amount of characters in the input

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
            button.setOnClickListener(onclick -> { this.registerInput(button); });

        for (Button button : this.buttons_operators)
            button.setOnClickListener(onclick -> { this.setSelectedOperator(button); });

        for (Button button : this.buttons_quickOps)
            button.setOnClickListener(onclick -> { this.setQuickOps(button); });

        for (Button button : this.buttons_others)
            button.setOnClickListener(onclick -> { this.setOthers(button); });
    }

    // INPUT/OUTPUT

    private void registerInput(Button button) {
        String result = Tools.getText(button),
            def = String.valueOf(DEFAULT_VALUE);


        if (this.firstNumber != DEFAULT_SELECTION) {
            result = Tools.getText(this.input) + result;

            if (result.length() <= MAX_INPUTS &&
                    (!Tools.getText(this.input).equals(def) || !Tools.getText(button).equals(def)))
                this.input.setText(result);

            this.parseInputs();
        }
    }

    // Registering inputs
    private void parseInputs() {
        String[] input = Tools.OPERATOR_SYMBOLS.split(this.input.getText());


        this.firstNumber = Double.parseDouble(input[0]);
        this.secondNumber = input.length > 1 ? Double.parseDouble(input[1]) : DEFAULT_VALUE;


        this.updateInputDisplay();
    }

    private void parseInParam(String text) {
        String[] input = Tools.OPERATOR_SYMBOLS.split(text);


        this.firstNumber = Double.parseDouble(input[0]);
        this.secondNumber = input.length > 1 ? Double.parseDouble(input[input.length - 1]) : DEFAULT_VALUE;


        this.updateInputDisplay();
    }

    // Updates input textview after parsing numbers and operator
    private void updateInputDisplay() {
        String result = "";

        if (this.isUnaryOperable())
            result += this.firstNumber == 0 ? "0" : Tools.formatNumber(this.firstNumber);
        if (this.selectedOperator != DEFAULT_SELECTION)
            result += this.buttons_operators.get(this.selectedOperator).getText();
        if (this.isBinaryOperable())
            result += this.secondNumber == 0 ? "" : Tools.formatNumber(this.secondNumber);

        this.input.setText(!result.isEmpty() ? result : String.valueOf((int) DEFAULT_VALUE));
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

        if (this.isUnaryOperable() && !this.isBinaryOperable()) {
            if (id == R.id.button_minus)
                this.minus();
            else if (id == R.id.button_squareRoot)
                this.squareroot();
            else
                this.quickDivides(quickOp);

            this.updateInputDisplay();
        }
    }

 /*   private void minus() {
        double temp = DEFAULT_VALUE;

        if (this.isBinaryOperable()) {
            this.output.setText(Tools.concat("-(", Tools.formatNumber(this.firstNumber),
                    Tools.getText(this.buttons_operators.get(this.selectedOperator)), Tools.formatNumber(this.secondNumber), ")"));
            temp = Calculator.operate(this.firstNumber, this.secondNumber, this.buttons_operators.get(this.selectedOperator));
            this.reset();
            this.firstNumber = -temp;
        } else {
            this.firstNumber = -this.firstNumber;
            this.output.setText(Tools.formatNumber(this.firstNumber));
        }

        this.updateInputDisplay();
    }
*/
    private void minus() {
        if(this.isUnaryOperable() /* && check selected operator */) {
            this.firstNumber = -this.firstNumber;
        } else if (this.isBinaryOperable()) {
            this.secondNumber = -this.secondNumber;
        }
        this.updateInputDisplay();
    }

    /*
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
        this.input.setText(Tools.formatNumber(this.firstNumber));

    }
    */

    private void squareroot() {
            this.firstNumber = Math.sqrt(this.firstNumber);
            this.updateInputDisplay();
    }

 /*   private void quickDivides(boolean percentageSelected) {
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
*/
    private void quickDivides(Button btn) {
      if(btn.getId() == R.id.button_percentage)
          this.firstNumber = this.firstNumber/100;
      else if (btn.getId() == R.id.button_divideby) {
          this.firstNumber = 1/this.firstNumber;
      }
        this.updateInputDisplay();
    }

    // MISC.

    // Behavior for misc & other buttons
    @SuppressLint("NonConstantResourceId")
    private void setOthers(Button other) {
        switch (other.getId()) {
            case R.id.button_ce:
                this.clearEverything();
            break;
            case R.id.button_c:
                this.clear();
            break;
            case R.id.button_backspace:
                this.parseInParam(Tools.backspace(this.input));
            break;
            case R.id.button_dot:
                this.dot();
            break;
            case R.id.button_equals:
                this.equals();
                break;
        }

        this.updateInputDisplay();
    }

    private void equals() {
        Button btn = null;
        String answer = "";
        String a = String.valueOf(this.firstNumber);
        String b = String.valueOf(this.secondNumber);
        String remove = "";

    if(this.isBinaryOperable()) {
        btn = this.buttons_operators.get(selectedOperator);
         answer = String.valueOf(Calculator.operate(this.firstNumber,this.secondNumber,btn));
         output.setText(Tools.concat(a,btn.getText().toString(),b));
        this.reset();
        this.firstNumber = Double.parseDouble(answer);
        input.setText(answer);
    }
    }

    private void dot() {

    }

    private void clearEverything() {
        this.reset();
        this.updateInputDisplay();
        this.output.setText("");
    }

    private void clear() {
        if (this.isBinaryOperable())
            this.secondNumber = DEFAULT_VALUE;
        else if (this.isUnaryOperable() && this.selectedOperator != DEFAULT_SELECTION)
            this.selectedOperator = DEFAULT_SELECTION;
        else
            this.firstNumber = DEFAULT_VALUE;
    }

    // Sets the current numbers and operator back to default values.
    private void reset() {
        if (this.selectedOperator != DEFAULT_SELECTION)
            this.buttons_operators.get(this.selectedOperator).setBackgroundTintList(
                Tools.getColor(getColor(R.color.custom_gray)));

        this.firstNumber = this.secondNumber = DEFAULT_VALUE;
        this.selectedOperator = DEFAULT_SELECTION;
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