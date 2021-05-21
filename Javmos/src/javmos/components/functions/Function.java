package javmos.components.functions;

import java.awt.BasicStroke;
import java.awt.Color;
import javmos.enums.FunctionType;
import javmos.enums.RootType;
import javmos.components.Point;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.*;
import javmos.components.JavmosComponent;
import javmos.JavmosGUI;

/**
 *
 * @author Chris Suh
 */
public abstract class Function extends JavmosComponent {

    protected Function(JavmosGUI gui) {
        super(gui);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(153, 50, 204));
        graphics2D.setStroke(new BasicStroke(3));
        // Window size calculations
        double scaleX = gui.getZoom() / gui.getDomainStep();
        double scaleY = gui.getZoom() / gui.getRangeStep();
        //The width (or height) divided by two to give midpoint
        double middle = gui.getPlaneWidth() / 2;
        // Change to 0.01 if you like speed; leave at 0.001 for range step to work with greater accuracy
        final double INTERVAL = 0.001;
        //a >= minDomaint && a <= maxDomaint && 
        for (double a = gui.getMinDomain(); a < gui.getMaxDomain(); a += INTERVAL) {
            if (middle + scaleX * a < 800 && middle + scaleX * a > 0 && getValueAt(a, FunctionType.ORIGINAL) >= gui.getMinRange() && getValueAt(a, FunctionType.ORIGINAL) <= gui.getMaxRange()) {
                graphics2D.draw(new Line2D.Double(middle + scaleX * a, middle - scaleY * getValueAt(a, FunctionType.ORIGINAL),
                        middle + scaleX * (INTERVAL + a), middle - scaleY * getValueAt(INTERVAL + a, FunctionType.ORIGINAL)));
            }
        }
    }

    public HashSet<Point> getCriticalPoints() {
        return RootType.CRITICAL_POINT.getRoots(gui, this, -400 / gui.getZoom() * gui.getDomainStep(), 400 / gui.getZoom() * gui.getDomainStep());
    }

    public HashSet<Point> getInflectionPoints() {
        return RootType.INFLECTION_POINT.getRoots(gui, this, -400 / gui.getZoom() * gui.getDomainStep(), 400 / gui.getZoom() * gui.getDomainStep());
    }

    public abstract String getSecondDerivative();

    public abstract String getFirstDerivative();

    public abstract double getValueAt(double x, FunctionType functionType);

    public HashSet<Point> getXIntercepts() {
        return RootType.X_INTERCEPT.getRoots(gui, this, -400 / gui.getZoom() * gui.getDomainStep(), 400 / gui.getZoom() * gui.getDomainStep());
    }

}
