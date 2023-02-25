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
        double a = Double.parseDouble(equation.substring(0));
        double b = Double.parseDouble(equation.substring(String.valueOf(a).length()+1));

        switch (btnText) {
            case "CE" :
            case "C" :
                output.setText("");
                input.setText("0");
                break;
            case "/" :
                Calculator.operate(a,b,"division");
                break;
                case "*" :
                    Calculator.operate(a,b,"multiplication");
                    break;
                case "+" :
                    Calculator.operate(a,b,"subtraction");
                    break;
                case "-" :
                    Calculator.operate(a,b,"addition");
                    break;
            case "=" :
                output.setText(output.getText());
                break;
            case "√" :
                Calculator.root(a);
                equation += a;
                break;
            case "%" :
                Calculator.operate(a,100,"division");
                break;
            case "+/-" :
                if (a < 0) {
                    Math.abs(a);
                    equation += a;
                }else if (a > 0) {
                    a = 0 - a;
                    equation += a;
                }
                break;
            case "1/X" :
                Calculator.operate(1,a,"division");
                equation += a;
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
}