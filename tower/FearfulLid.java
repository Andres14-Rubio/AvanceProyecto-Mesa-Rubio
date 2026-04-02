package tower;

public class FearfulLid extends Lid {
    public FearfulLid(int number, String color) {
        super(number, color);
        lid.changeColor("black");
    }

    public String getSubtype() { return "fearful"; }

    @Override
    public boolean canEnter(Tower tower) {
        return tower.containsCup(number);
    }

    @Override
    public boolean canBeRemoved(Tower tower) {
        return !tower.isCoveringOwnCup(this);
    }
}
