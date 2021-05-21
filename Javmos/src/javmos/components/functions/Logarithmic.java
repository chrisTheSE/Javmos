/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import java.util.HashSet;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.enums.FunctionType;

/**
 *
 * @author Aaron
 */
public class Logarithmic extends Function {

    // Initialize
    double a, base, k, valueToReturn;

    public Logarithmic(JavmosGUI gui, String function) {
        super(gui);
        function = (function + " ").substring(function.indexOf("=") + 1);
        if ((function.substring(0, function.indexOf("l"))).equals("-")) {
            a = -1;
        } else if (!(function.charAt(0) + "").equals("l")) {
            a = Double.parseDouble(function.substring(0, function.indexOf("l")));
        } else {
            a = 1;
        }
        String tempEquation = function.substring(function.indexOf("(") + 1, function.indexOf("x"));
        if (function.substring(function.indexOf("g") + 1, function.indexOf("(")).equals("")) {
            base = 10;
        } else {
            base = function.contains("ln") ? Math.E : Double.parseDouble(function.substring(function.indexOf("g") + 1, function.indexOf("(")));
        }
        if (tempEquation.equals("-")) {
            k = -1;
        } else if (!tempEquation.equals("")) {
            k = Double.parseDouble(tempEquation);
        } else {
            k = 1;
        }
    }

    public HashSet<Point> getCriticalPoints() {
        // API says that there's no crit points; return empty set
        return new HashSet<Point>();
    }

    public HashSet<Point> getInflectionPoints() {
        // API says no inflection points either; return empty set
        return new HashSet<Point>();
    }

    @Override
    public String getSecondDerivative() {
        // Check if ln or log function, return appropiate equation
        String equation = (base == Math.E) ? (-a + "/x^2") : (-a + "/x^2ln" + base);
        return "f''(x) = " + equation;
    }

    @Override
    public String getFirstDerivative() {
        // Check if ln or log function, returb appropiate equation for 1st deriv.
        String equation = (base == Math.E) ? (a + "/x") : (a + "/xln" + base);
        return "f'(x) = " + equation;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        // Check functionType and return respective value 
        if (functionType == FunctionType.FIRST_DERIVATIVE) {
            valueToReturn = a / (Math.log(base) * x);
        } else if (functionType == FunctionType.SECOND_DERIVATIVE) {
            valueToReturn = -a / (Math.pow(x, 2) * Math.log(base));
        } else if (functionType == FunctionType.THIRD_DERIVATIVE) {
            valueToReturn = 2 * a / (Math.pow(x, 3) * Math.log(base));
        } else {
            valueToReturn = a * Math.log(k * x) / Math.log(base);
        }
        return valueToReturn;
    }

}
