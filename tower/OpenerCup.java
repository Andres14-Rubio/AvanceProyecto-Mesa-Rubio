package tower;

public class OpenerCup extends Cup {
    public OpenerCup(int number, int heightCm, String color) {
        super(number, heightCm, color);
        tint("yellow");
    }

    public String getSubtype() { return "opener"; }

    @Override
    public void onEnter(Tower tower) {
        tower.removeBlockingLids();
        tower.addItem(this);
    }
}
