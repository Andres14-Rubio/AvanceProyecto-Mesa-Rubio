package tower;

public class HierarchicalCup extends Cup {
    public HierarchicalCup(int number, int heightCm, String color) {
        super(number, heightCm, color);
        tint("magenta");
    }

    public String getSubtype() { return "hierarchical"; }

    @Override
    public void onEnter(Tower tower) {
        tower.insertHierarchicalCup(this);
    }

    @Override
    public boolean canBeRemoved(Tower tower) {
        return !tower.isAtBottom(this);
    }
}
