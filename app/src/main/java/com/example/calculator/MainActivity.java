package com.example.calculator;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
TextView input,output;

    private final int[] btn_numbers_ids = {
            R.id.button_0, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
    };

    private final int[] btn_operators_ids = {
            R.id.button_division, R.id.button_multiplication, R.id.button_subtraction,
            R.id.button_addition, R.id.button_squareRoot, R.id.button_percentage, R.id.button_divideby
    };

    private final int[] btn_others_ids = {
            R.id.button_backspace, R.id.button_ce, R.id.button_c, R.id.button_minus,
            R.id.button_dot, R.id.button_equals
    };

    private ArrayList<Button> buttons_numbers;
    private ArrayList<Button> buttons_operators;
    private ArrayList<Button> buttons_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);

        // Assigning stuff to numbers
        this.buttons_numbers = new ArrayList<Button>();
        this.addButtonsToList(this.btn_numbers_ids, this.buttons_numbers);

        // Assigning stuff to operators
        this.buttons_operators = new ArrayList<Button>();
        this.addButtonsToList(this.btn_operators_ids, this.buttons_operators);

        // Assigning stuff to the rest
        this.buttons_others = new ArrayList<Button>();
        this.addButtonsToList(this.btn_others_ids, this.buttons_others);

        this.setListeners();
    }

    // GETS EACH ID IN THE LIST OF INT AND ADDS THE BUTTON OBJECT TO LIST
    private void addButtonsToList(int[] ids, ArrayList<Button> list) {
        Button button = null;

        for (int id : ids) {
            button = findViewById(id);
            list.add(findViewById(id));
        }
    }

    // ADDS ONCLICK LISTENERS TO ALL BUTTONS
    private void setListeners() {
        for (Button button : this.buttons_numbers)
            button.setOnClickListener(onclick -> {
                input.setText(input.getText().toString() + button.getText());
            });

        for (Button button : this.buttons_operators) {
            if (this.buttons_operators.indexOf(button) <= this.btn_operators_ids[3]) {
                button.setOnClickListener(onclick -> { this.setSelected(button); });
            }
        }
    }

    private void setSelected(Button operator) {
        int lastOperator = this.buttons_operators.indexOf(findViewById(R.id.button_addition)),
                selected = ContextCompat.getColor(this, R.color.custom_graySelected),
                normal = ContextCompat.getColor(this, R.color.custom_gray);

        for (int i = 0; i < lastOperator; i++) {
            operator.setBackgroundColor(operator.equals(this.buttons_operators.get(i)) ? selected : normal);
        }
    }

    // ZAFER

    /*
    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String btnTag = btn.getTag().toString();
        String equation = input.getText().toString();

        switch (btnText) {
            case "CE" :
                output.setText("");
                input.setText("0");
                break;
            case "/" :
                break;
                case "*" :
                break;
                case "+" :
                break;
                case "-" :
                break;
                case "=" :
                    output.setText(output.getText());
                    break;
            default:
                break;
        }

        if(btnTag.equals("backspace")){
            equation = equation.substring(0,equation.length()-1);
        }
        equation += btnText;
        output.setText(equation);


    }

     */
}