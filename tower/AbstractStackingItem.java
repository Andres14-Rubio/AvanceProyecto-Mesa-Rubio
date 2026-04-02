package tower;

public abstract class AbstractStackingItem implements StackingItem {
    protected int number;
    protected int currentX;
    protected int currentY;
    protected String displayColor;

    protected AbstractStackingItem(int number, String displayColor) {
        this.number = number;
        this.displayColor = displayColor;
        this.currentX = 0;
        this.currentY = 0;
    }

    public int getNumber() { return number; }
    public boolean canEnter(Tower tower) { return true; }
    public boolean canBeRemoved(Tower tower) { return true; }
}
