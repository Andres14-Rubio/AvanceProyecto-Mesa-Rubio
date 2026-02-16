/**
 * Representa una taza dentro de la torre.
 */
public class Cup extends StackableItem {

    private int diameter;
    private String color;
    private Lid lid;
    private int id;

    /**
     * Constructor de la taza.
     */
    public Cup(int diameter, String color, int id) {

    }

    /**
     * Retorna el id de la taza.
     */
    public int getId() {

        return 0;
    }

    /**
     * Asigna una tapa a la taza.
     */
    public void setLid(Lid lid) {

    }

    /**
     * Retorna la tapa asociada.
     */
    public Lid getLid() {

        return null;
    }

    /**
     * Indica si la taza tiene tapa.
     */
    public boolean hasLid() {

        return false;
    }

    @Override
    public void makeVisible() {

    }

    @Override
    public void makeInvisible() {

    }
}
