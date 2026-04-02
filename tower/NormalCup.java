package tower;

public class NormalCup extends Cup {
    public NormalCup(int number, int heightCm, String color) {
        super(number, heightCm, color);
    }

    public String getSubtype() { return "normal"; }
}
