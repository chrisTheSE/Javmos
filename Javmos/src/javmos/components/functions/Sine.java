/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import javmos.JavmosGUI;
import javmos.enums.FunctionType;

/**
 *
 * @author Aaron
 */
public final class Sine extends Trigonometric {

    public Sine(JavmosGUI gui, String function) {
        super(gui, function, "sin");
    }

    @Override
    public String getSecondDerivative() {
        // deriv of ak * cos(kx) = -ak * sin(kx) 
        String firstPart = (-a * Math.pow(k, 2) == 1.0) ? "" : ((-a * Math.pow(k, 2) == -1.0) ? "-" : -a * Math.pow(k, 2) + "");
        String tempK = (k == 1.0) ? "" : ((k == -1.0) ? "-" : k + "");
        return "f''(x)=" + firstPart + "sin(" + tempK + "x)";
    }

    @Override
    public String getFirstDerivative() {
        // deriv of ak * sin(kx) = ak * cos(kx)
        String firstPart = (a * k == 1.0) ? "" : ((a * k == -1.0) ? "-" : a * k + "");
        String tempK = (k == 1.0) ? "" : ((k == -1.0) ? "-" : k + "");
        return "f''(x)=" + firstPart + "cos(" + tempK + "x)";
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        if (functionType == FunctionType.FIRST_DERIVATIVE) {
            return a * k * Math.cos(k * x);
        } else if (functionType == FunctionType.SECOND_DERIVATIVE) {
            return -a * Math.pow(k, 2) * Math.sin(k * x);
        } else if (functionType == FunctionType.THIRD_DERIVATIVE) {
            return -a * Math.pow(k, 3) * Math.cos(k * x);
        } else {
            return a * Math.sin(k * x);
        }
    }

}
