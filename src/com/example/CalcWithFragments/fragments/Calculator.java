package com.example.CalcWithFragments.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import com.example.CalcWithFragments.R;
import com.example.CalcWithFragments.service.DigitService;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Calculator extends Fragment {
    private static TextView mainScreen;
    private static StringBuilder screenData;
    private static StringBuilder notationResult;
    private static BigInteger resolveResultInBigInt;
    public static final String TAG;
    private static double resolve = 0;

    private static long l;

    static {
        screenData = new StringBuilder("");
        notationResult = new StringBuilder("");
        TAG = "fragment_number";
        notationSystem = 10;//decimal notation system set on default
    }

    private static int notationSystem;
    private Button buttonDot;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

    private Button button9;
    private Button openBracket;

    private Button closeBracket;
    private Button hexA;
    private Button hexB;
    private Button hexC;
    private Button hexD;
    private Button hexE;

    private Button hexF;
    private Button backspace;

    private Button delete;
    private Button plus;
    private Button minus;
    private Button multiplication;
    private Button dividing;
    private Button pow;
    private Button equity;

    private View inflate;
    Menu menu;

    public Calculator() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.calculator, container, false);
//        int i = getArguments().getInt(TAG);
//        String fragment = getResources().getStringArray(R.array.fragments)[i];
        getSupportActivity().setTitle(getResources().getStringArray(R.array.fragments)[0]);
        setRetainInstance(true);
        //main screen
        mainScreen = (TextView) inflate.findViewById(R.id.main_screen);

        //digits buttons
        buttonDot = (Button) inflate.findViewById(R.id.dot);
        button0 = (Button) inflate.findViewById(R.id.zero);
        button1 = (Button) inflate.findViewById(R.id.one);
        button2 = (Button) inflate.findViewById(R.id.two);
        button3 = (Button) inflate.findViewById(R.id.three);
        button4 = (Button) inflate.findViewById(R.id.four);
        button5 = (Button) inflate.findViewById(R.id.five);
        button6 = (Button) inflate.findViewById(R.id.six);
        button7 = (Button) inflate.findViewById(R.id.seven);
        button8 = (Button) inflate.findViewById(R.id.eight);
        button9 = (Button) inflate.findViewById(R.id.nine);
        buttonDot.setOnClickListener(onDigitButtonClick);
        button0.setOnClickListener(onDigitButtonClick);
        button1.setOnClickListener(onDigitButtonClick);
        button2.setOnClickListener(onDigitButtonClick);
        button3.setOnClickListener(onDigitButtonClick);
        button4.setOnClickListener(onDigitButtonClick);
        button5.setOnClickListener(onDigitButtonClick);
        button6.setOnClickListener(onDigitButtonClick);
        button7.setOnClickListener(onDigitButtonClick);
        button8.setOnClickListener(onDigitButtonClick);
        button9.setOnClickListener(onDigitButtonClick);
        //hex buttons
        hexA = (Button) inflate.findViewById(R.id.A);
        hexB = (Button) inflate.findViewById(R.id.B);
        hexC = (Button) inflate.findViewById(R.id.C);
        hexD = (Button) inflate.findViewById(R.id.D);
        hexE = (Button) inflate.findViewById(R.id.E);
        hexF = (Button) inflate.findViewById(R.id.F);
        hexA.setOnClickListener(onDigitButtonClick);
        hexB.setOnClickListener(onDigitButtonClick);
        hexC.setOnClickListener(onDigitButtonClick);
        hexD.setOnClickListener(onDigitButtonClick);
        hexE.setOnClickListener(onDigitButtonClick);
        hexF.setOnClickListener(onDigitButtonClick);
        //brackets
        openBracket = (Button) inflate.findViewById(R.id.open_bracket);
        closeBracket = (Button) inflate.findViewById(R.id.close_bracket);
        openBracket.setOnClickListener(onDigitButtonClick);
        closeBracket.setOnClickListener(onDigitButtonClick);
        //delete buttons
        backspace = (Button) inflate.findViewById(R.id.backspace);
        delete = (Button) inflate.findViewById(R.id.delete);
        backspace.setOnClickListener(onDeleteButtonClick);
        delete.setOnClickListener(onDeleteButtonClick);
        //arithmetic operator buttons
        plus = (Button) inflate.findViewById(R.id.plus);
        minus = (Button) inflate.findViewById(R.id.minus);
        multiplication = (Button) inflate.findViewById(R.id.multiplication);
        dividing = (Button) inflate.findViewById(R.id.dividing);
        pow = (Button) inflate.findViewById(R.id.pow);
        equity = (Button) inflate.findViewById(R.id.equity);
        plus.setOnClickListener(onOperatorButtonClick);
        minus.setOnClickListener(onOperatorButtonClick);
        multiplication.setOnClickListener(onOperatorButtonClick);
        dividing.setOnClickListener(onOperatorButtonClick);
        pow.setOnClickListener(onOperatorButtonClick);
        equity.setOnClickListener(onOperatorButtonClick);
        return inflate;
    }

    View.OnClickListener onDeleteButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backspace:
                    try {
                        screenData.setLength(0);
                        screenData.append(mainScreen.getText());
                        screenData.deleteCharAt(screenData.length() - 1);
                        mainScreen.setText(screenData);
                    } catch (NullPointerException ex) {
                    } finally {
                        break;
                    }
                case R.id.delete:
                    screenData.setLength(0);
                    mainScreen.setText(screenData);
                    break;
            }
        }
    };
    View.OnClickListener onDigitButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if we have resolve, conveniently to operate with him
            screenData = DigitService.getResolve(screenData);
            switch (v.getId()) {
                case R.id.dot:
//                    if (DigitService.dotRepeat(screenData)) {
                    screenData.append(".");
//                    mainScreen.setText(screenData);
                    mainScreen = (TextView) inflate.findViewById(R.id.main_screen);
                    mainScreen.setText(screenData);
//                    }
                    break;
                case R.id.zero:
                    screenData.append("0");
                    mainScreen.setText(screenData);
                    break;
                case R.id.one:
                    screenData.append("1");
                    mainScreen.setText(screenData);
                    break;
                case R.id.two:
                    screenData.append("2");
                    mainScreen.setText(screenData);
                    break;
                case R.id.three:
                    screenData.append("3");
                    mainScreen.setText(screenData);
                    break;
                case R.id.four:
                    screenData.append("4");
                    mainScreen.setText(screenData);
                    break;
                case R.id.five:
                    screenData.append("5");
                    mainScreen.setText(screenData);
                    break;
                case R.id.six:
                    screenData.append("6");
                    mainScreen.setText(screenData);
                    break;
                case R.id.seven:
                    screenData.append("7");
                    mainScreen.setText(screenData);
                    break;
                case R.id.eight:
                    screenData.append("8");
                    mainScreen.setText(screenData);
                    break;
                case R.id.nine:
                    screenData.append("9");
                    mainScreen.setText(screenData);
                    break;
                case R.id.A:
                    screenData.append("A");
                    mainScreen.setText(screenData);
                    break;
                case R.id.B:
                    screenData.append("B");
                    mainScreen.setText(screenData);
                    break;
                case R.id.C:
                    screenData.append("C");
                    mainScreen.setText(screenData);
                    break;
                case R.id.D:
                    screenData.append("D");
                    mainScreen.setText(screenData);
                    break;
                case R.id.E:
                    screenData.append("E");
                    mainScreen.setText(screenData);
                    break;
                case R.id.F:
                    screenData.append("F");
                    mainScreen.setText(screenData);
                    break;
                case R.id.open_bracket:
                    screenData.append("(");
                    mainScreen.setText(screenData);
                    break;
                case R.id.close_bracket:
                    screenData.append(")");
                    mainScreen.setText(screenData);
                    break;
            }
        }
    };
    View.OnClickListener onOperatorButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if we have resolve, conveniently to operate with him
            screenData = DigitService.getResolve(screenData);
            switch (v.getId()) {
                case R.id.plus:
                    screenData.append("+");
                    mainScreen.setText(screenData);
                    break;
                case R.id.minus:
                    screenData.append("-");
                    mainScreen.setText(screenData);
                    break;
                case R.id.multiplication:
                    screenData.append("*");
                    mainScreen.setText(screenData);
                    break;
                case R.id.dividing:
                    screenData.append("/");
                    mainScreen.setText(screenData);
                    break;
                case R.id.pow:
                    screenData.append("^");
                    mainScreen.setText(screenData);
                    break;
                case R.id.equity:
                    //now we have to find in wat system notation write formula
                    switch (notationSystem) {
                        case 16:
                            resolve = DigitService.resolveNonDecimalFormula(screenData, notationSystem);
                            try {
                                resolveResultInBigInt = new BigDecimal(resolve).toBigInteger();
                                l = resolveResultInBigInt.longValue();
                            } catch (Exception e) {
                                screenData.append(" = ").append("∞");
                                break;
                            }
                            screenData.append(" = ").append(Long.toHexString(l).toUpperCase());
                            break;
                        case 10:
                            resolve = DigitService.resolveFormula(screenData);
                            try {
                                resolveResultInBigInt = new BigDecimal(resolve).toBigInteger();
                            } catch (Exception e) {
                                screenData.append(" = ").append("∞");
                                break;
                            }
                            screenData.append(" = ").append(new BigDecimal(resolve).round(MathContext.DECIMAL64));
                            break;
                        case 8:
                            resolve = DigitService.resolveNonDecimalFormula(screenData, notationSystem);
                            try {
                                resolveResultInBigInt = new BigDecimal(resolve).toBigInteger();
                                l = resolveResultInBigInt.longValue();
                            } catch (Exception e) {
                                screenData.append(" = ").append("∞");
                                break;
                            }
                            screenData.append(" = ").append(Long.toOctalString(l));
                            break;
                        case 2:
                            resolve = DigitService.resolveNonDecimalFormula(screenData, notationSystem);
                            try {
                                resolveResultInBigInt = new BigDecimal(resolve).toBigInteger();
                                l = resolveResultInBigInt.longValue();
                            } catch (Exception e) {
                                screenData.append(" = ").append("∞");
                                break;
                            }
                            screenData.append(" = ").append(Long.toBinaryString(l));
                            break;
                    }
                    mainScreen.setText(screenData);
                    break;
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void checkResolve() {
        //if we have resolve, conveniently to operate with him
        screenData = DigitService.getResolve(screenData);
        //we can`t convert all formula
        if (DigitService.isFormula(screenData)) {
            screenData.setLength(0);
            mainScreen.setText(screenData);
        }
        //we can`t convert decimal
        screenData = DigitService.getBigInteger(screenData);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        Log.i("Fragment", "onPrepareOptionsMenu " + notationSystem);
        switch (notationSystem) {
            case 2:
                menu.findItem(R.id.bin).setChecked(true);
                getSupportActivity().getSupportActionBar().setSubtitle(R.string.binary_);
                binLayout();
                break;
            case 8:
                menu.findItem(R.id.oct).setChecked(true);
                getSupportActivity().getSupportActionBar().setSubtitle(R.string.octal);
                octLayout();
                break;
            case 10:
                menu.findItem(R.id.dec).setChecked(true);
                getSupportActivity().getSupportActionBar().setSubtitle(R.string.decimal);
                decLayout();
                break;
            case 16:
                menu.findItem(R.id.hex).setChecked(true);
                getSupportActivity().getSupportActionBar().setSubtitle(R.string.hexadecimal);
                hexLayout();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bin:
                getSupportActionBar().setSubtitle(R.string.binary_);
                checkResolve();
                item.setChecked(true);
                binLayout();
                if (screenData.length() != 0) {
                    switch (notationSystem) {
                        case 16:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 16);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toBinaryString(l).toUpperCase());
                            break;
                        case 10:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 10);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toBinaryString(l).toUpperCase());
                            break;
                        case 8:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 8);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toBinaryString(l).toUpperCase());
                            break;
                    }
                }
                notationSystem = 2;
                screenData = notationResult;
                mainScreen.setText(screenData);
                break;
            case R.id.oct:
                getSupportActionBar().setSubtitle(R.string.octal);
                checkResolve();
                item.setChecked(true);
                octLayout();
                if (screenData.length() != 0) {
                    switch (notationSystem) {
                        case 16:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 16);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toOctalString(l).toUpperCase());
                            break;
                        case 10:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 10);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toOctalString(l).toUpperCase());
                            break;
                        case 2:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 2);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toOctalString(l).toUpperCase());
                            break;
                    }
                }
                notationSystem = 8;
                screenData = notationResult;
                mainScreen.setText(screenData);
                break;
            case R.id.dec:
                getSupportActionBar().setSubtitle(R.string.decimal);
                checkResolve();
                item.setChecked(true);
                decLayout();
                if (screenData.length() != 0) {
                    switch (notationSystem) {
                        case 16:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 16);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(String.valueOf(l));
                            break;
                        case 8:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 8);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(String.valueOf(l));
                            break;
                        case 2:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 2);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(String.valueOf(l));
                            break;
                    }
                }
                notationSystem = 10;
                screenData = notationResult;
                mainScreen.setText(screenData);
                break;
            case R.id.hex:
                getSupportActionBar().setSubtitle(R.string.hexadecimal);
                checkResolve();
                item.setChecked(true);
                hexLayout();
                if (screenData.length() != 0) {
                    switch (notationSystem) {
                        case 10:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 10);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toHexString(l).toUpperCase());
                            break;
                        case 8:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 8);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toHexString(l).toUpperCase());
                            break;
                        case 2:
                            resolveResultInBigInt = new BigInteger(screenData.toString(), 2);
                            l = resolveResultInBigInt.longValue();
                            notationResult = new StringBuilder(Long.toHexString(l).toUpperCase());
                            break;
                    }
                }
                notationSystem = 16;
                screenData = notationResult;
                mainScreen.setText(screenData);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hexLayout() {
        buttonDot.setEnabled(false);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        hexA.setEnabled(true);
        hexB.setEnabled(true);
        hexC.setEnabled(true);
        hexD.setEnabled(true);
        hexE.setEnabled(true);
        hexF.setEnabled(true);
    }

    private void decLayout() {
        buttonDot.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        hexA.setEnabled(false);
        hexB.setEnabled(false);
        hexC.setEnabled(false);
        hexD.setEnabled(false);
        hexE.setEnabled(false);
        hexF.setEnabled(false);
    }

    private void octLayout() {
        buttonDot.setEnabled(false);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(false);
        button9.setEnabled(false);
        hexA.setEnabled(false);
        hexB.setEnabled(false);
        hexC.setEnabled(false);
        hexD.setEnabled(false);
        hexE.setEnabled(false);
        hexF.setEnabled(false);
    }

    private void binLayout() {
        buttonDot.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        hexA.setEnabled(false);
        hexB.setEnabled(false);
        hexC.setEnabled(false);
        hexD.setEnabled(false);
        hexE.setEnabled(false);
        hexF.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainScreen.setText(screenData);
        Log.i("Fragment", "onResume");
    }
}
