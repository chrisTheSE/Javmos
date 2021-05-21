/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javmos.components.functions;

import javmos.JavmosGUI;

/**
 *
 * @author Aaron
 */
public abstract class Trigonometric extends Function {

    protected double a, k;

    public Trigonometric(JavmosGUI gui, String function, String name) {
        super(gui);
        String newEquation = (function + " ").substring(function.indexOf("=") + 1);
        // Repair "a" value in equation [y=sin(x) --> y=1sin(x)]
        if ((newEquation.charAt(0) + "").contains("-") && ((newEquation.charAt(1) + "").contains(name.charAt(0) + ""))) {
            a = -1;
        } else if (!(newEquation.charAt(0) + "").equals(name.charAt(0) + "")) {
            a = Double.parseDouble(newEquation.substring(0, newEquation.indexOf(name.charAt(0) + "")));
        } else {
            a = 1;
        }
        // Repair "k" value in equation [y=sin(x) --> y=sin(1x)]
        String form = newEquation.contains("(") ? "(" : name.charAt(name.length() - 1) + "";
        newEquation = newEquation.substring(newEquation.indexOf(form) + 1, newEquation.indexOf("x"));
        if (newEquation.equals("-")) {
            k = -1;
        } else if (newEquation.equals("")) {
            k = 1;
        } else {
            k = Double.parseDouble(newEquation);
        }
    }
}
