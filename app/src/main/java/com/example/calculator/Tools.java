package com.example.calculator;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Tools {
    public static final int[] BTN_NUMBERS_IDS = {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
    };

    public static final int[] BTN_OPERATORS_IDS = {
            R.id.button_division, R.id.button_multiplication, R.id.button_subtraction,
            R.id.button_addition, R.id.button_squareRoot
    };

    public static final int[] BTN_QUICKOPS_IDS = {
            R.id.button_squareRoot, R.id.button_percentage,
            R.id.button_divideby, R.id.button_minus
    };

    public static final int[] BTN_OTHERS_IDS = {
            R.id.button_backspace, R.id.button_ce, R.id.button_c,
            R.id.button_dot, R.id.button_equals
    };

    public static final Pattern OPERATOR_SYMBOLS = Pattern.compile("[/*+-]");

    public static ColorStateList getColor(int color) {
        return ColorStateList.valueOf(color);
    }

    public static boolean isAnInteger(double number) {
        return number == 0 || number % 1 == 0;
    }

    public static String formatNumber(double number) {
        return isAnInteger(number) ? String.valueOf((int) number) : String.valueOf(number);
    }
    public static String getText(Button button) {
        return button.getText().toString();
    }

    public static String getText(Context context, int id) {
        return ((Button)((Activity)context).findViewById(id)).getText().toString();
    }

    public static String getText(TextView textview) {
        return textview.getText().toString();
    }

    public static String backspace(TextView textview) {
        String str = textview.getText().toString();

        if (str.length() > 1)
            return str.substring(0, str.length() - 1);
        else
            return String.valueOf(MainActivity.DEFAULT_VALUE);
    }

    public static String concat(String... str) {
        return Arrays.stream(str).reduce("", (s1, s2) -> s1 + s2);
    }

    public static void setColor(Button button, ColorStateList color) {
        button.setBackgroundTintList(color);
    }
}
