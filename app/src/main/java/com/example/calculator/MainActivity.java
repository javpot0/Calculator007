package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // attributs de classe
    String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Button buttonCall0 = null, buttonCall1 = null, buttonCall2 = null, buttonCall3 = null, buttonCall4 = null,
                buttonCall5 = null, buttonCall6 = null, buttonCall7 = null, buttonCall8 = null, buttonCall9 = null;

        for(int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    buttonCall0 = findViewById(R.id.button_0);
                    buttonCall0.setTag("0");
                    break;
                case 1:
                    buttonCall1 = findViewById(R.id.button_1);
                    buttonCall1.setTag("1");
                    break;
                case 2:
                    buttonCall2 = findViewById(R.id.button_2);
                    buttonCall2.setTag("2");
                    break;
                case 3:
                    buttonCall3 = findViewById(R.id.button_3);
                    buttonCall3.setTag("3");
                    break;
                case 4:
                    buttonCall4 = findViewById(R.id.button_4);
                    buttonCall4.setTag("4");
                    break;
                case 5:
                    buttonCall5 = findViewById(R.id.button_5);
                    buttonCall5.setTag("5");
                    break;
                case 6:
                    buttonCall6 = findViewById(R.id.button_6);
                    buttonCall6.setTag("6");
                    break;
                case 7:
                    buttonCall7 = findViewById(R.id.button_7);
                    buttonCall7.setTag("7");
                    break;
                case 8:
                    buttonCall8 = findViewById(R.id.button_8);
                    buttonCall8.setTag("8");
                    break;
                case 9:
                    buttonCall9 = findViewById(R.id.button_9);
                    buttonCall9.setTag("9");
                    break;
            }
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = ((Button)v).getText().toString();
                String tag = v.getTag().toString();
                // do something with buttonText and tag
            }
        };

        buttonCall0.setOnClickListener(clickListener);
        buttonCall1.setOnClickListener(clickListener);
        buttonCall2.setOnClickListener(clickListener);
        buttonCall3.setOnClickListener(clickListener);
        buttonCall4.setOnClickListener(clickListener);
        buttonCall5.setOnClickListener(clickListener);
        buttonCall6.setOnClickListener(clickListener);
        buttonCall7.setOnClickListener(clickListener);
        buttonCall8.setOnClickListener(clickListener);
        buttonCall9.setOnClickListener(clickListener);
    }
}