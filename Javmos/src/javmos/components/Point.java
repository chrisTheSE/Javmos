package javmos.components;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Objects;
import javmos.JavmosGUI;
import javmos.enums.RootType;

public class Point extends JavmosComponent {

    public final RootType rootType;
    public final int RADIUS = 5;
    public Ellipse2D.Double point;
    public double x;
    public double y;

    public Point(JavmosGUI gui, RootType type, double x, double y) {
        super(gui);
        this.rootType = type;
        this.x = x;
        this.y = y;
    }

    public RootType getRootType() {
        return rootType;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return Math.round(y * 1000) / 1000.0;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // Check if point is within domain/range
        if (x > gui.getMinDomain() && x < gui.getMaxDomain() && y > gui.getMinRange() && y < gui.getMaxRange()) {
            Ellipse2D.Double oval = getPoint();
            graphics2D.setColor(rootType.getRootColor());
            graphics2D.fill(oval);
            graphics2D.draw(oval);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Point point = (Point) object;
        return x == point.x;
    }

    @Override
    public String toString() {
        if (this.getRootType() == RootType.X_INTERCEPT) {
            return ("" + rootType.getRootName() + ": (" + x + ", 0.0)");
        } else {
            x = (x == -0.0) ? 0.0 : x;
            y = (y == -0.0) ? 0.0 : y;
            return ("" + rootType.getRootName() + ": (" + x + ", " + y + ")");
        }
    }

    public Ellipse2D.Double getPoint() {
        double scaleX = gui.getZoom() / gui.getDomainStep();
        double scaleY = gui.getZoom() / gui.getRangeStep();
        double middle = gui.getPlaneWidth() / 2;
        point = new Ellipse2D.Double(middle + scaleX * getX() - 5, middle - scaleY * getY() - 5, 10, 10);
        return point;
    }

}
