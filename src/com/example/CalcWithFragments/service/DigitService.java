package com.example.CalcWithFragments.service;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DigitService {
    private static Pattern pattern;
    private static Matcher matcher;
    private static String mistake;

    static {
        mistake = "☜ там ошибка";
    }

    private static StringBuffer tmpFormula = new StringBuffer();
    private static StringBuilder formula;
    private static BigInteger bigInteger;
    private static long l;
    private static double dou;
    private static String tmp;

    /**
     * Get BigInteger value to convert into specific number system
     *
     * @param string main screen value
     * @return BigInteger value
     */
    public static StringBuilder getBigInteger(StringBuilder string) {
        pattern = Pattern.compile("-?[\\dA-F]+");
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            return new StringBuilder(matcher.group());
        } else {
            return new StringBuilder("");
        }
    }

    public static double resolveFormula(StringBuilder screenData) {
        Calculable calc;
        double result1 = 0;
        screenData = adaptFormula(screenData);
        try {
            calc = new ExpressionBuilder(screenData.toString()).build();
            result1 = calc.calculate();
        } catch (Exception e) {
            screenData.append(mistake);
        }
        return result1;
    }

    /**
     * Replace digit near bracket to multiply expression<br>
     * on example: from <b>2+3(5-1)2</b> to <b>2+3*(5-1)*2</b>
     *
     * @param screenData main screen formula
     * @return resolve
     */
    private static StringBuilder adaptFormula(StringBuilder screenData) {
        pattern = Pattern.compile("[\\dA-F]+\\(+");
        matcher = pattern.matcher(screenData);
        while (matcher.find()) {
            screenData.insert(matcher.end() - 1, "*");
        }
        pattern = Pattern.compile("\\)+[\\dA-F]+");
        matcher = pattern.matcher(screenData);
        while (matcher.find()) {
            screenData.insert(matcher.end() - 1, "*");
        }
        pattern = Pattern.compile("\\)+\\(+");
        matcher = pattern.matcher(screenData);
        while (matcher.find()) {
            screenData.insert(matcher.end() - 1, "*");
        }
        return screenData;
    }

    public static boolean isFormula(final StringBuilder screenData) {
        String s = screenData.toString();
        pattern = Pattern.compile("[\\dA-F]+[-+^*/]+");
        matcher = pattern.matcher(screenData);
        return matcher.find();
    }

    /**
     * Convert non decimal formula to decimal view and call  DigitService resolveFormula method
     * @param screenData main screen non decimal formula
     * @param notation specific number system
     * @return resolve in <i>double</i> type
     */
    public static double resolveNonDecimalFormula(StringBuilder screenData,final int notation) {
        pattern = Pattern.compile("[\\dA-F]+");
        matcher = pattern.matcher(screenData);
        while (matcher.find()) {
            bigInteger = new BigInteger(matcher.group(), notation);
            l = bigInteger.longValue();
            tmp = String.valueOf(l);
            matcher.appendReplacement(tmpFormula, tmp);
        }
        matcher.appendTail(tmpFormula);
        //get formula in dec and get resolve
        formula = new StringBuilder(tmpFormula);
        tmpFormula.setLength(0);
        dou = resolveFormula(formula);
        if (formula.toString().contains(mistake)) {
            screenData.append(mistake);
        }
        return dou;
    }

    public static StringBuilder getResolve(StringBuilder screenData) {
        pattern = Pattern.compile("= -?[\\dA-F]+");
        matcher = pattern.matcher(screenData);
        if (matcher.find()) {
            tmp = screenData.substring(matcher.start() + 2);
            screenData.setLength(0);
            screenData.append(tmp);
        }
        return screenData;
    }
}











