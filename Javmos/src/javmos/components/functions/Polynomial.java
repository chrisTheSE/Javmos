package javmos.components.functions;

import javmos.enums.FunctionType;
import java.util.*;
import javmos.JavmosGUI;

public class Polynomial extends Function {

    public String polynomial;
    public final double coefficients[];
    public final int degrees[];

    public Polynomial(JavmosGUI gui, String function) {
        super(gui);
        ArrayList<String> tempTerms = new ArrayList<>();
        String terms[];
        int position = 0;
        String term = "";
        // Preventing error from empty input
        if (function.trim().isEmpty()) {
            function = "0";
        }
        // Remove anything before =
        String newEquation = (function + " ").substring(function.indexOf("=") + 1);
        // Repair individual neg terms for parsing
        newEquation = newEquation.replaceAll("-x", "-1x");
        // Separate terms
        while (true) {
            if (position == newEquation.length() - 1) {
                terms = new String[tempTerms.size()];
                terms = tempTerms.toArray(terms);
                coefficients = new double[terms.length];
                degrees = new int[terms.length];
                break;
            } else {
                term += newEquation.charAt(position);
                position++;
                if ((newEquation.charAt(position) + "").equals("-") | (newEquation.charAt(position) + "").equals("+") | position + 1 == newEquation.length()) {
                    tempTerms.add(term);
                    term = "";
                }
            }
        }
        for (int a = 0; a < terms.length; a++) {
            terms[a] = terms[a].replace("+", "");
            // Moving degrees to it's own array
            if (terms[a].contains("^")) {
                degrees[a] = Integer.parseInt(terms[a].substring(terms[a].indexOf("^") + 1));
            } else {
                if (terms[a].contains("x") && !terms[a].substring(terms[a].indexOf("x") + 1).isEmpty()) {
                    // throw new PolynomialException(function + " is not a valid polynomial!");
                } else {
                    degrees[a] = terms[a].contains("x") ? 1 : 0;
                }
            }
            // Moving coefficients to dedicated array
            coefficients[a] = ((terms[a].indexOf("x") == 0) ? 1 : ((terms[a].indexOf("x") > 0) ? Double.parseDouble(terms[a].substring(0, terms[a].indexOf("x"))) : Double.parseDouble(terms[a])));
        }
        // Sort the array lists from highest to lowest
        tempTerms = new ArrayList<>();
        for (int i = 0; i < terms.length; i++) {
            tempTerms.add(degrees[i] + "/" + coefficients[i]);
        }
        // Sort and reverse from largest to smallest deg
        Collections.sort(tempTerms);
        Collections.reverse(tempTerms);
        for (int b = 0; b < terms.length; b++) {
            degrees[b] = Integer.parseInt((tempTerms.get(b).substring(0, tempTerms.get(b).indexOf("/"))));
            coefficients[b] = Double.parseDouble(tempTerms.get(b).substring(tempTerms.get(b).indexOf("/") + 1));
        }
    }

    public Polynomial(JavmosGUI gui, double coefficients[], int degrees[]) {
        super(gui);
        this.coefficients = coefficients;
        this.degrees = degrees;
    }

    @Override
    public String getFirstDerivative() {
        // Initialize new arrays to store coefficients & deg
        String tempEquation = "f'(x)=";
        int[] tempDegrees = new int[degrees.length];
        double[] tempCoefficients = new double[coefficients.length];
        // Loop through and multiply coefficients by degrees
        for (int i = 0; i < coefficients.length; i++) {
            tempDegrees[i] = degrees[i] - 1;
            tempCoefficients[i] = degrees[i] * coefficients[i];
        }
        if (degrees[0] != 0) {
            for (int a = 0; a < tempCoefficients.length; a++) {
                if (tempCoefficients[a] != 0) {
                    // If lead is neg, add - sign; otherwise add +
                    tempEquation += (!((tempCoefficients[a] + "").charAt(0) + "").equals("-") && a != 0) ? "+" : "";
                    // If deg = 0, show coefficient only; if deg = 1, hide the exponent after x; if deg > 1, show x^degree[a]
                    tempEquation += (tempDegrees[a] > 0) ? (tempDegrees[a] == 1) ? tempCoefficients[a] + "x" : tempCoefficients[a] + "x^" + tempDegrees[a] : tempCoefficients[a];
                }
            }
            return tempEquation;
        } else {
            return "f'(x)=0.0";
        }
    }

    @Override
    public String getSecondDerivative() {
        Polynomial deriv = new Polynomial(gui, getFirstDerivative());
        if (deriv.degrees[0] != 0) {
            return "f'" + new Polynomial(gui, getFirstDerivative()).getFirstDerivative().substring(1);
        } else {
            return "f''(x)=0.0";
        }
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        Polynomial poly = null;
        if (functionType != null) {
            switch (functionType) {
                case THIRD_DERIVATIVE:
                    poly = new Polynomial(gui, new Polynomial(gui, getFirstDerivative()).getSecondDerivative());
                    break;
                case SECOND_DERIVATIVE:
                    poly = new Polynomial(gui, getSecondDerivative());
                    break;
                case FIRST_DERIVATIVE:
                    poly = new Polynomial(gui, getFirstDerivative());
                    break;
                default:
                    poly = this;
                    break;
            }
        }
        int[] tempDegrees = poly.degrees;
        double[] tempCoefficients = poly.coefficients;
        double tempVal = 0.0;
        for (int a = 0; a < tempCoefficients.length; a++) {
            tempVal += (tempDegrees[a] > 0) ? tempCoefficients[a] * Math.pow(x, tempDegrees[a]) : tempCoefficients[a];
        }
        return tempVal;
    }
}
