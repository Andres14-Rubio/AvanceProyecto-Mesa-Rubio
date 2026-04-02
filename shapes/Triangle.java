package shapes;

import java.awt.*;

/** Triangulo grafico reutilizable. */
public class Triangle extends Shape {
    public static int VERTICES = 3;
    private int height;
    private int width;

    public Triangle() {
        super(140, 15, "green");
        height = 30;
        width = 40;
    }

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = {xPosition, xPosition + (width / 2), xPosition - (width / 2)};
            int[] ypoints = {yPosition, yPosition + height, yPosition + height};
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
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
