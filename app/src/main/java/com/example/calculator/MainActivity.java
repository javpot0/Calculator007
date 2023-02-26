package com.example.calculator;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_SELECTION = -1;
    public static final double DEFAULT_VALUE = 0;
    public static final String DEFAULT_INPUT_STATE = "0";

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
            button.setOnClickListener(onclick -> {
                switch(button.getId()) {
                    case R.id.button_squareRoot :
                        // MORE TO DO *****
                }
            });
    }

    // NOTE FOR BACKSPACE: IF PRESSED, DELETE | IF EMPTY, SET TEXT TO 0
    // NOTE FOR NUMBERS: CREATE 2 VARIABLES HERE FOR NUMBERS AND ONE FOR OPERATOR IN USE

    // Behavior for number buttons, backspace, ce, c(to be implemented)
    private void setInputText(boolean concatenating, String text) {
        String current = this.input.getText().toString();

        if (!concatenating || current.equals(DEFAULT_INPUT_STATE))
            this.input.setText(text);
        else
            this.input.setText(current + text);

        this.parseInputs();
    }

    // Behavior for output results
    private void setOutputText(boolean concatenating, String text) {

    }

    // Behavior for operator buttons
    private void setSelectedOperator(Button operator) {
        ColorStateList selected = Tools.getColor(getColor(R.color.custom_graySelected)),
                deselected = Tools.getColor(getColor(R.color.custom_gray));
        int currentSelection = this.buttons_operators.indexOf(operator);

        if (this.isUnaryOperable()) {
            if (this.selectedOperator == currentSelection) {
                operator.setBackgroundTintList(deselected);
                this.selectedOperator = DEFAULT_SELECTION;
            }
            else {
                if (this.selectedOperator != DEFAULT_SELECTION)
                    this.buttons_operators.get(this.selectedOperator).setBackgroundTintList(deselected);

                operator.setBackgroundTintList(selected);
                this.selectedOperator = currentSelection;
            }

            this.setOperatorInView(operator);
        }
    }

    private void setOperatorInView(Button operator) {
        String result = "";

        this.parseInputs();
        result += this.firstNumber != DEFAULT_VALUE ? String.valueOf(this.firstNumber) : "";
        result += !result.isEmpty() ? operator.getText() : "";
        result += this.secondNumber != DEFAULT_VALUE ? String.valueOf(this.secondNumber) : "";

        if (!result.isEmpty())
            this.setInputText(false, result);
    }

    // Behavior for quick operation buttons
    private void setQuickOps(Button quickOp) {

        switch(quickOp.getId()) {
            case R.id.button_squareRoot:
        // MORE TO DO *****
        }
    }

    // Behavior for misc & other buttons
    private void setOthers(Button other) {
        // TO DO ******
    }

    // Registering inputs
    private void parseInputs() {
        String[] input = this.input.getText().toString().split(Tools.OPERATOR_SYMBOLS.pattern());

        this.firstNumber = input.length > 0 ? Double.parseDouble(input[0]) : DEFAULT_VALUE;
        this.secondNumber = input.length > 1 ? Double.parseDouble(input[1]) : DEFAULT_VALUE;

        for (Button button : this.buttons_operators)
            if (this.input.getText().toString().indexOf(button.getText().toString()) != DEFAULT_SELECTION)
                this.selectedOperator = this.buttons_operators.indexOf(button);
    }

    private String backspace() {
        String inputStr = this.input.toString();

        return inputStr.substring(0, inputStr.length() - 1);
    }

    private boolean isUnaryOperable() {
        return this.firstNumber != DEFAULT_VALUE;
    }

    private boolean isBinaryOperable() {
        return this.isUnaryOperable() && this.secondNumber != DEFAULT_VALUE;
    }

}