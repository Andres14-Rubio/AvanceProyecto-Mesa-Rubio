package tower;

public class NormalLid extends Lid {
    public NormalLid(int number, String color) {
        super(number, color);
    }

    public String getSubtype() { return "normal"; }
}
