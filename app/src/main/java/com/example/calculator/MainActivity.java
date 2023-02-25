package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView input,output;
    Button buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    Button buttonCE, buttonC, buttonBackspace, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);

        assignerId(buttonDivide,R.id.button_division);
        assignerId(buttonMultiply,R.id.button_multiplication);
        assignerId(buttonPlus,R.id.button_addition);
        assignerId(buttonMinus,R.id.button_minus);
        assignerId(buttonEquals,R.id.button_equals);
        assignerId(button0,R.id.button_0);
        assignerId(button1,R.id.button_1);
        assignerId(button2,R.id.button_2);
        assignerId(button3,R.id.button_3);
        assignerId(button4,R.id.button_4);
        assignerId(button5,R.id.button_5);
        assignerId(button6,R.id.button_6);
        assignerId(button7,R.id.button_7);
        assignerId(button8,R.id.button_8);
        assignerId(button9,R.id.button_9);
        assignerId(buttonCE,R.id.button_ce);
        assignerId(buttonC,R.id.button_c);
        assignerId(buttonBackspace,R.id.button_backspace);
        assignerId(buttonDot,R.id.button_dot);

    }

    private void assignerId(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String btnTag = btn.getTag().toString();
        String equation = input.getText().toString();

        if(btnText.equals("CE")){
            output.setText("");
            output.setText("0");
            return;
        }
        if(btnText.equals("=")){
            output.setText(output.getText());
            return;
        }
        if(btnTag.equals("backspace")){
            equation = equation.substring(0,equation.length()-1);
        }else{
            equation += btnText;
        }
        output.setText(equation);


    }
}