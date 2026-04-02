package tower;

import shapes.Rectangle;

public abstract class Cup extends AbstractStackingItem {
    public static final int PX_PER_CM = 10;
    protected static final int CUP_WIDTH = 80;
    protected static final int WALL_THICKNESS = 10;

    protected final int heightCm;
    protected final Rectangle leftWall;
    protected final Rectangle rightWall;
    protected final Rectangle base;

    protected Cup(int number, int heightCm, String color) {
        super(number, color);
        this.heightCm = heightCm;
        leftWall = new Rectangle();
        rightWall = new Rectangle();
        base = new Rectangle();

        leftWall.changeColor(color);
        rightWall.changeColor(color);
        base.changeColor(color);

        int cupHeightPx = heightCm * PX_PER_CM;
        leftWall.changeSize(cupHeightPx, WALL_THICKNESS);
        rightWall.changeSize(cupHeightPx, WALL_THICKNESS);
        base.changeSize(WALL_THICKNESS, CUP_WIDTH);

        leftWall.moveHorizontal(-70);
        leftWall.moveVertical(-15);
        rightWall.moveHorizontal(-70 + (CUP_WIDTH - WALL_THICKNESS));
        rightWall.moveVertical(-15);
        base.moveHorizontal(-70);
        base.moveVertical(-15 + (cupHeightPx - WALL_THICKNESS));
    }

    public String getType() { return "cup"; }
    public int getHeightCm() { return heightCm; }

    public void moveTo(int x, int baseY) {
        int topY = baseY - (heightCm * PX_PER_CM);
        int dx = x - currentX;
        int dy = topY - currentY;
        leftWall.moveHorizontal(dx);
        leftWall.moveVertical(dy);
        rightWall.moveHorizontal(dx);
        rightWall.moveVertical(dy);
        base.moveHorizontal(dx);
        base.moveVertical(dy);
        currentX = x;
        currentY = topY;
    }

    public void makeVisible() {
        leftWall.makeVisible();
        rightWall.makeVisible();
        base.makeVisible();
    }

    public void makeInvisible() {
        leftWall.makeInvisible();
        rightWall.makeInvisible();
        base.makeInvisible();
    }

    public void tint(String color) {
        leftWall.changeColor(color);
        rightWall.changeColor(color);
        base.changeColor(color);
    }

    public void onEnter(Tower tower) {
        tower.addItem(this);
    }
}
