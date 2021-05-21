package javmos.components;

import java.awt.*;
import javmos.JavmosGUI;

public class CartesianPlane extends JavmosComponent {

    public CartesianPlane(JavmosGUI gui) {
        super(gui);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // numOfSquares is the number of squares on one side of the axis
        int numOfSquares = (int) Math.floor((800 / gui.getZoom()) / 2);
        // Initial Pixels that is not a full square on each side
        int iniPixels = (int) Math.floor((((800 / gui.getZoom()) / 2) - numOfSquares) * gui.getZoom());
        double middle = gui.getPlaneWidth() / 2;

        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setFont(new Font("default", Font.BOLD, gui.getZoom() < 50 ? (int) Math.floor(0.28 * (gui.getZoom() + 16)) : (int) Math.floor(0.28 * gui.getZoom())));
        graphics2D.drawLine((int) middle, 0, (int) middle, 800);//Major Y-axis
        graphics2D.drawLine(0, (int) middle, 800, (int) middle);//Major X-axis
        graphics2D.setStroke(new BasicStroke(1));

        // The for loop below draws the minor lines of the axis and the numbers. We make use of a ternary operator here to ensure that when the domain/range step are integers the 1.0 doesn't appear I.e: 1 vs 1.0.
        for (int i = 0; i <= numOfSquares * 2; i++) {
            graphics2D.drawLine((int) Math.floor(iniPixels + i * gui.getZoom()), 0, (int) Math.floor(iniPixels + i * gui.getZoom()), 800);
            graphics2D.drawLine(0, (int) Math.floor(iniPixels + i * gui.getZoom()), 800, (int) Math.floor(iniPixels + i * gui.getZoom()));
            // We cast to (Object) because ternary operators require both cases to be of the same type.
            if (i != numOfSquares) {
                graphics2D.drawString("" + ((gui.getDomainStep() != Math.floor(gui.getDomainStep())) ? ((double) Math.round((-numOfSquares + i) * gui.getDomainStep() * 1000) / 1000)
                        : ((Object) (int) ((-numOfSquares + i) * gui.getDomainStep()))), (int) Math.floor(iniPixels + i * gui.getZoom()), (int) middle);
                graphics2D.drawString("" + (gui.getRangeStep() != Math.floor(gui.getRangeStep()) ? ((double) Math.round((numOfSquares - i) * gui.getRangeStep() * 1000) / 1000)
                        : (Object) (int) ((numOfSquares - i) * gui.getRangeStep())), (int) middle + 2, (int) Math.floor(iniPixels + i * gui.getZoom()));
            }
        }
    }
}
