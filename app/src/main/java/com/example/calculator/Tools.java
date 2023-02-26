package com.example.calculator;

import android.content.res.ColorStateList;

import java.util.regex.Pattern;

public class Tools {
    public static final int[] BTN_NUMBERS_IDS = {
            R.id.button_0, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
    };

    public static final int[] BTN_OPERATORS_IDS = {
            R.id.button_division, R.id.button_multiplication, R.id.button_subtraction,
            R.id.button_addition, R.id.button_squareRoot, R.id.button_percentage, R.id.button_divideby
    };

    public static final int[] BTN_QUICKOPS_IDS = {
            R.id.button_squareRoot, R.id.button_percentage,
            R.id.button_divideby, R.id.button_minus
    };

    public static final int[] BTN_OTHERS_IDS = {
            R.id.button_backspace, R.id.button_ce, R.id.button_c, R.id.button_minus,
            R.id.button_dot, R.id.button_equals
    };

    public static final Pattern OPERATOR_SYMBOLS = Pattern.compile("[/*+-]");

    public static ColorStateList getColor(int color) {
        return ColorStateList.valueOf(color);
    }

    public static Operators getOperatorFromSymbol(String input) {
        switch (OPERATOR_SYMBOLS.matcher(input).group()) {
            case "/" : return Operators.division;
            case "*" : return Operators.multiplication;
            case "+" : return Operators.addition;
            default : return Operators.subtraction;
        }
    }
}
