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
public final class Tangent extends Trigonometric {

    public Tangent(JavmosGUI gui, String function) {
        super(gui, function, "tan");
    }

    public HashSet<Point> getCriticalPoints() {
        return new HashSet<Point>();
    }

    public HashSet<Point> getInflectionPoints() {
        return new HashSet<Point>();
    }

    @Override
    public String getSecondDerivative() {
        // deriv of sec^2(x) = 2tan(x)sec^2(x) 
        String firstPart = (2 * a * Math.pow(k, 2) == 1.0) ? "" : ((2 * a * Math.pow(k, 2) == -1.0) ? "-" : 2 * a * Math.pow(k, 2) + "");
        String tempK = (k == 1.0) ? "" : ((k == -1.0) ? "-" : k + "");
        return "f''(x)=" + firstPart + "tan(" + tempK + "x)" + "sec^2(" + tempK + "x)";
    }

    @Override
    public String getFirstDerivative() {
        // deriv of tanx = sec^2(x)
        String firstPart = (a * k == 1.0) ? "" : ((a * k == -1.0) ? "-" : a * k + "");
        String tempK = (k == 1.0) ? "" : ((k == -1.0) ? "-" : k + "");
        return "f'(x)=" + firstPart + "sec^2(" + tempK + "x)";
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        if (functionType == FunctionType.FIRST_DERIVATIVE) {
            return a * k * Math.pow((1 / Math.cos(k * x)), 2);
        } else if (functionType == FunctionType.SECOND_DERIVATIVE) {
            return 2 * a * Math.pow(k, 2) * Math.tan(k * x) * Math.pow((1 / Math.cos(k * x)), 2);
        } else if (functionType == FunctionType.THIRD_DERIVATIVE) {
            // 3rd deriv --> 2 * sec^2(kx)(2 * tan^2(kx) * sec^2(kx))
            return a * Math.pow(k, 3) * Math.pow((1 / Math.cos(k * x)), 2) * (3 * Math.pow((Math.pow((1 / Math.cos(k * x)), x)), 2));
        } else {
            return a * Math.tan(k * x);
        }
    }

}
