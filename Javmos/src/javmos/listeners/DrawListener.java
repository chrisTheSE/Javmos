package javmos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javmos.JavmosGUI;
import javmos.components.JavmosPanel;
import javmos.components.functions.Cosine;
import javmos.components.functions.Function;
import javmos.components.functions.Logarithmic;
import javmos.components.functions.Polynomial;
import javmos.components.functions.Sine;
import javmos.components.functions.Tangent;

public class DrawListener implements ActionListener {

    private final JavmosGUI gui;
    private final JavmosPanel panel;

    public DrawListener(JavmosGUI gui, JavmosPanel panel) {
        this.gui = gui;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            String equation = gui.getEquationField();
            Function functionInput;
            if (equation.contains("sin")) {
                functionInput = new Sine(gui, equation);
            } else if (equation.contains("tan")) {
                functionInput = new Tangent(gui, equation);
            } else if (equation.contains("ln") || equation.contains("log")) {
                functionInput = new Logarithmic(gui, equation);
            } else if (equation.contains("cos")) {
                functionInput = new Cosine(gui, equation);
            } else {
                functionInput = new Polynomial(gui, equation);
            }
            panel.components.clear();
            panel.setFunction(functionInput);
            gui.setFirstDerivativeLabel(panel.getFunction().getFirstDerivative());
            gui.setSecondDerivativeLabel(panel.getFunction().getSecondDerivative());
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "FunctionInput Error", JOptionPane.ERROR_MESSAGE);
        }
        panel.repaint();
    }
}
