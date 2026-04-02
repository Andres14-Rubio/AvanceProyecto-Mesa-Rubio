package shapes;

import java.awt.geom.*;

/** Circulo grafico reutilizable. */
public class Circle extends Shape {
    public static final double PI = 3.1416;
    private int diameter;

    public Circle() {
        super(20, 15, "blue");
        diameter = 30;
    }

    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }

    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
