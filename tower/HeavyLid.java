package tower;

/** Nuevo tipo propuesto: una tapa pesada que no permite sacar la taza que cubre directamente. */
public class HeavyLid extends Lid {
    public HeavyLid(int number, String color) {
        super(number, color);
        lid.changeColor("orange");
    }

    public String getSubtype() { return "heavy"; }
}
