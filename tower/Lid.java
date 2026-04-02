package tower;

import shapes.Rectangle;

public abstract class Lid extends AbstractStackingItem {
    protected final Rectangle lid;

    protected Lid(int number, String color) {
        super(number, color);
        lid = new Rectangle();
        lid.changeColor(color);
        lid.changeSize(1 * Cup.PX_PER_CM, 80);
        lid.moveHorizontal(-70);
        lid.moveVertical(-15);
    }

    public String getType() { return "lid"; }
    public int getHeightCm() { return 1; }

    public void moveTo(int x, int baseY) {
        int topY = baseY - Cup.PX_PER_CM;
        int dx = x - currentX;
        int dy = topY - currentY;
        lid.moveHorizontal(dx);
        lid.moveVertical(dy);
        currentX = x;
        currentY = topY;
    }

    public void makeVisible() { lid.makeVisible(); }
    public void makeInvisible() { lid.makeInvisible(); }

    public void onEnter(Tower tower) {
        tower.addLidDefault(this);
    }
}
