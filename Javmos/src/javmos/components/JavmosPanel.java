package javmos.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JPanel;
import javmos.components.functions.Function;
import javmos.JavmosGUI;
import javmos.listeners.PointClickListener;

public class JavmosPanel extends JPanel {

    public final LinkedList<JavmosComponent> components;
    private final JavmosGUI gui;

    public JavmosPanel(JavmosGUI gui) {
        this.gui = gui;
        this.components = new LinkedList<>();
    }

    public Function getFunction() {
        for (JavmosComponent component : components) {
            if (component instanceof Function) {
                return (Function) component;
            }
        }
        return null;
    }

    public void setPlane(CartesianPlane plane) {
        components.addFirst(plane);
    }

    public void setFunction(Function function) {
        LinkedList<Point> points = new LinkedList<>();
        points.addAll(function.getCriticalPoints());
        points.addAll(function.getXIntercepts());
        points.addAll(function.getInflectionPoints());
        components.add(function);
        components.addAll(points);
        PointClickListener click = (PointClickListener) this.getMouseListeners()[0];
        click.setPoints(points);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setPlane(new CartesianPlane(gui));
        components.forEach((componentz) -> {
            componentz.draw((Graphics2D) graphics);
        });
    }
}
