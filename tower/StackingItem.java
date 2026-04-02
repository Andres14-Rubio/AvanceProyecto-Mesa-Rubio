package tower;

public interface StackingItem {
    int getNumber();
    String getType();
    String getSubtype();
    int getHeightCm();
    void moveTo(int x, int baseY);
    void makeVisible();
    void makeInvisible();
    boolean canEnter(Tower tower);
    boolean canBeRemoved(Tower tower);
}
