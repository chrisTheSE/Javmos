package javmos.enums;

import javmos.components.Point;
import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import javmos.components.functions.Function;
import javmos.JavmosGUI;

public enum RootType {

    // Declaring enumerations
    X_INTERCEPT(Color.GREEN, "X_INTERCEPT", FunctionType.ORIGINAL, FunctionType.FIRST_DERIVATIVE),
    CRITICAL_POINT(Color.RED, "CRITICAL_POINT", FunctionType.FIRST_DERIVATIVE, FunctionType.SECOND_DERIVATIVE),
    INFLECTION_POINT(Color.BLUE, "INFLECTION_POINT", FunctionType.SECOND_DERIVATIVE, FunctionType.THIRD_DERIVATIVE);

    public final Color rootColor;
    public final String rootName;
    public final int ATTEMPTS = 16;
    public String polynomial;
    public FunctionType functionOne;
    public FunctionType functionTwo;

    RootType(Color rootColor, String rootName, FunctionType functionOne, FunctionType functionTwo) {
        this.rootColor = rootColor;
        this.rootName = rootName;
        this.functionOne = functionOne;
        this.functionTwo = functionTwo;
    }

    public Color getRootColor() {
        return rootColor;
    }

    public String getRootName() {
        return rootName;
    }

    public Double newtonsMethod(Function function, double guess, int attempts) {
        // DO NOT CHANGE TOLERENCE 
        final double TOLERENCE = 0.00000001;
        // Check whether to continue recursion or not
        //  if (attempts == 0 || degrees[0] <= 0) { (OLD VERSION MAY NEED TO CHECK DEGREE = 0
        if (attempts == 0) {
            // Finished, no need to continue
            return Double.NaN;
        } else {
            // Recursively run Newton's Method until tolerence is met
            return (Math.abs(guess - (guess + (function.getValueAt(guess, functionOne)
                    / function.getValueAt(guess, functionTwo)))) <= TOLERENCE) ? guess
                    : newtonsMethod(function, guess - (function.getValueAt(guess, functionOne) / function.getValueAt(guess, functionTwo)), attempts - 1);
        }

    }

    public HashSet<Point> getRoots(JavmosGUI gui, Function function, double minDomain, double maxDomain) {
        // Initialize
        HashSet<Point> points = new HashSet<>();
        DecimalFormat df = new DecimalFormat("0.000");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        double tempRoot = 0.0, a = minDomain;
        double step = (gui.getDomainStep() < 0) ? -gui.getDomainStep() : gui.getDomainStep(); //Handles negative domain steps
        while (a < maxDomain) {
            tempRoot = newtonsMethod(function, a, ATTEMPTS); // 30 attempts should be enough?
            if (!Double.isNaN(tempRoot)) {
                Point newPoint = new Point(gui, this, Double.parseDouble(df.format(tempRoot)), Double.parseDouble(df.format(function.getValueAt(tempRoot, FunctionType.ORIGINAL))));
                points.add(newPoint);
            }
            a = a + 0.01 * step;
        }
        return points;
    }
}
