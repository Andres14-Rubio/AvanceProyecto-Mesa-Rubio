package tower;

public class CrazyLid extends Lid {
    public CrazyLid(int number, String color) {
        super(number, color);
        lid.changeColor("green");
    }

    public String getSubtype() { return "crazy"; }

    @Override
    public void onEnter(Tower tower) {
        tower.addItemAtBottom(this);
    }
}
